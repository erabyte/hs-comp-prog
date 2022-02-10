/**
 * A version of the 'piles' card game.
 * 
 * Created by Derek King / erabyte
 * 29 Oct, 2021
 */

import java.util.*;

class Pile { // Create class for our piles
	Random rand = new Random();
	private int cards;
	Pile() { // Constructor to assign amount of cards
		this.cards = rand.nextInt(7) + 1; // Assigns a random number of cards between 1 and 7 
	}
	void removeCards(int cardsToRemove) { // Removes cards from the pile
		this.cards -= cardsToRemove;
	}
	int getCards() { // Returns the current amount of cards in the pile
		return this.cards;
	}
}

class Player { // Create class for the players
	private String name;
	Player(String newName) { // Constructor to assing name of player
		this.name = newName;
	}
	String getName() { // Method to get the name of the player
		return this.name;
	}
}

public class LostOnes {
	public static void main(String[] args) {
		LostOnes game = new LostOnes();
		Scanner keyboard = new Scanner(System.in);
		
		// Create 3 new piles named A, B, and C
		Pile pileA = new Pile();
		Pile pileB = new Pile();
		Pile pileC = new Pile();
		Pile[] piles = {pileA, pileB, pileC}; // Put the piles into an array

		System.out.print("Player 1, enter your name: "); // Create player for player 1 using name from user
		Player plr1 = new Player(keyboard.nextLine());

		System.out.print("Player 2, enter your name: "); // Create player for player 2 using name from user
		Player plr2 = new Player(keyboard.nextLine());

		int turn = 1; // Variable to hold the number of turns since the beginning of the game 

		// Loop that keeps the game running until somebody wins
		boolean gameRunning = true;
		while (gameRunning) {
			
			// Ends the game if all piles are empty
			if (pileA.getCards() <= 0 && pileB.getCards() <= 0 && pileC.getCards() <= 0) {
				System.out.println(game.GetPlayerAtTurn(plr1, plr2, turn + 1) + ", you took the last remaining counter(s), so you lose. " + game.GetPlayerAtTurn(plr1, plr2, turn) + " wins!"); // End of game
				gameRunning = false;
			}
			
			// Keeps the game running as long as there is more than 1 card left
			else if (pileA.getCards() + pileB.getCards() + pileC.getCards() > 1) {
				System.out.println();
				
				// Displays the current piles and their card amounts in colums
				int rows = Math.max(pileA.getCards(), Math.max(pileB.getCards(), pileC.getCards())); // Finds the pile with the highest amount of cards
				for (int i = rows; i > 0; i--) {
					String row = ""; // Empty string to hold the row
					
					// Loop through each pile
					for (Pile pile : piles) {
						
						// Check if the amount of cards in the current pile is less than the row number being generated (a pile with 4 cards will not show *'s in the top 3 rows)
						if (pile.getCards() >= i) {
							row = row.concat("* "); // Add a * to the row
						}
						else {
							row = row.concat("  "); // Add a space to the end of the row
						}
					}
					System.out.println(row); // Display the generated row
				}
				System.out.println("A B C");

				//System.out.println("\nA: " + pileA.displayRow() +  "\nB: " + pileB.displayRow() + "\nC: " + pileC.displayRow()); // Display current piles and their card amounts
				System.out.print("\n" + game.GetPlayerAtTurn(plr1, plr2, turn) + ", choose a pile: "); // Prompt current player to pick a pile
				String pileName = keyboard.nextLine(); // Player enters a letter corresponding to pile name
				Pile pile = null;

				// Loop to validate pile selection from user
				boolean pileIsValid = false;
				while (!pileIsValid) {

					// Loop to check that the user selected a valid pile
					while (pile == null) {

						// Updates the value of the current pile based from player input
						if (pileName.equalsIgnoreCase("A")) {
							pile = pileA;
						}
						else if (pileName.equalsIgnoreCase("B")) {
							pile = pileB;
						}
						else if (pileName.equalsIgnoreCase("C")) {
							pile = pileC;
						}

						// If the player does not choose a valid pile
						else {
							System.out.print("Please choose from pile A, B, or C: "); // Tell the player to choose pile A, B, or C
							pileName = keyboard.nextLine(); // Allow the player to choose a pile to be checked again
						}
					}
					
					// If the amount of cards in a chosen pile empty
					if (pile.getCards() < 0) {
						System.out.println("Nice try, " + game.GetPlayerAtTurn(plr1, plr2, turn) + ". That pile is empty. Choose again: "); // Tell the player to choose another pile
					}
					// Will run when the player chooses a valid pile that is not empty
					else {
						pileIsValid = true; // Ends the loop
					}
				}
					
				// Prompt player to choose amount of cards to remove from previously selected pile
				System.out.print("How many cards do you want to remove from " + pileName.toUpperCase() + "? ");
				int cardsToRemove = keyboard.nextInt(); // Player chooses amount of cards to remove
				keyboard.nextLine(); // Removes the newline character

				// Loop to run until the player enters a valid amount of cards to remove from the chosen pile
				boolean success = false;
				while (!success) {

					// If the player enters a number that leaves the amount of cards < 0
					if (cardsToRemove > pile.getCards()) {
						System.out.print("Pile " + pileName + " doesn't have that many. Try again: "); // Tell the player to enter another number
						cardsToRemove = keyboard.nextInt(); // Allow them to enter another number that will result in the amount of cards being >= 0
						keyboard.nextLine();
					}

					// If the player enters a negative number or 0
					else if (cardsToRemove < 1) {
						System.out.print("You must choose at least 1. How many? "); // Tell the player to enter another number
						cardsToRemove = keyboard.nextInt(); // Allow them to enter a positive number
						keyboard.nextLine();
					}

					// When the player enters a number that is positive and leaves the amount of cards >= 0
					else {
						success = true; // Ends the loop
						pile.removeCards(cardsToRemove); // Removes the chosen amount of cards from the current pile
						turn += 1; // Moves to the next player's turn
					}
				}
			}
			
			// Ends the game when there is 1 card left
			else {
				System.out.println(game.GetPlayerAtTurn(plr1, plr2, turn) + ", you must take the last remaining counter, so you lose. " + game.GetPlayerAtTurn(plr1, plr2, turn + 1) + " wins!");
				gameRunning = false; // Stops the game loop from running
			}
		}
		keyboard.close();
	}

	// Method to return the name of the player on the given turn
	private String GetPlayerAtTurn(Player plr1, Player plr2, int turn) {
		return turn % 2 == 0 ? plr2.getName() : plr1.getName();
	}
}