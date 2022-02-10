/**
 * Classic 3-in-a-row game.
 * 
 * Created by Derek King
 * 9 Feb, 2022
 */

import java.util.*;

public class TicTacToe {
	// class variables
	private char[][] board = new char[3][3];
	private boolean ended;
	private char currentTurn;

	public static void main(String[] args) {
		// variables
		TicTacToe game = new TicTacToe();
		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		game.generateBoard(); game.displayBoard();

		// random first turn
		game.currentTurn = rand.nextInt(2) == 0 ? 'X' : 'O';

		//loop until game is over
		while (!game.ended) {
			System.out.println(String.format("%c, choose a cell on the board (format is \"x,y\" (\"1,1\" is top left, \"3,3\" is bottom right).", game.currentTurn));
			System.out.print(String.format("<%c>: ", game.currentTurn));

			// get valid move from player
			String move = scan.nextLine();
			while (!game.isValidMove(move)) {
				System.out.print(String.format("<%c>: ", game.currentTurn));
				move = scan.nextLine();
			}

			// update cell on the board
			String[] cell = move.split(",");
			int x = Integer.parseInt(cell[0]), y = Integer.parseInt(cell[1]);
			game.board[y-1][x-1] = game.currentTurn;
			
			game.displayBoard(); // after each turn, display board

			// check state of the game for a winner or if the board is full
			int state = game.getState();
			switch (state) {
				case 0: // no winner yet
					game.currentTurn = game.currentTurn == 'X' ? 'O' : 'X';
					continue;
				case 1: // X won
					System.out.println("X wins!");
					break;
				case 2: // O won
					System.out.println("O wins!");
					break;
				case 3: // draw/board is full
					System.out.println("board is full- draw!");
					break;
			}
			game.ended = true;
			scan.close();
		}
	}

	// reset the board with blank spaces
	void generateBoard() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				board[row][col] = ' ';
			}
		}
	}

	// display the game board
	void displayBoard() {
		System.out.println();
		for (int row = 0; row < 3; row++) {
			System.out.println(String.format(" %c ║ %c ║ %c ", board[row][0], board[row][1], board[row][2]));
			System.out.println(row != 2 ? "═══╬═══╬═══" : "");
		}
	}

	// attempt to find the cell the player wants to mark
	boolean isValidMove(String move) {
		try {
			// get x and y cell coordinates from move
			String[] cell = move.split(",");
			int x = Integer.parseInt(cell[0]), y = Integer.parseInt(cell[1]);
			// if the cell is empty
			if (board[y-1][x-1] == ' ') { return true; }
			else { System.out.println("this cell is already occupied!"); }
		}
		// will run if they use non-integer values or do not type their move correctly
		catch (Exception e) { System.out.println("this is not a valid cell! the format is \"x,y\"."); }
		return false;
	}

	// get the state of the board represented by an integer between 0 and 3
	int getState() {
		boolean full = true;
		// generate single-line string from board cells
		String inline = "";
		for (int n = 0; n < 3; n ++) {
			inline = inline + String.valueOf(board[n][0]) + String.valueOf(board[n][1]) + String.valueOf(board[n][2]);
			// check for empty cells
			if (board[n][0] == ' ' || board[n][1] == ' ' || board[n][2] == ' ') {
				full = false;
			}
		}
		// check for vertical, horizontal, or diagonal lines of the current player
		if (inline.matches("CCC.{6}|...CCC...|.{6}CCC|(C..){3}|(.C.){3}|(..C){3}|(C...){2}C|..C.C.C..".replaceAll("C", String.valueOf(currentTurn)))) {
			return currentTurn == 'X' ? 1 : 2;
		}
		return full ? 3 : 0;
	} 
}