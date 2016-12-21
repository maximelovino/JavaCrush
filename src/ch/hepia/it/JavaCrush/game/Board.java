package ch.hepia.it.JavaCrush.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Model to represent the board of the game
 */
public class Board {
	private int[][] board;
	private int size;
	private static Random rnd = new Random();
	private int range;

	/**
	 * Main constructor for our board
	 * @param size	The size of the side of the board
	 * @param range	The range, meaning the number of different cells possible
	 */
	public Board (int size, int range) {
		this.size = size;
		this.range = range;
		this.board = new int[size][size];
	}

	/**
	 * Secondary constructor for our board
	 * @param board	A one dimensional array representation of a board
	 * @param range	The range, meaning the number of different cells possible
	 */
	public Board (int[] board, int range) {
		this.range = range;
		this.size = (int) Math.sqrt(board.length);
		for (int i = 0; i < board.length; i++) {
			this.board[i / this.size][i % this.size] = board[i];
		}
	}

	/**
	 * Copy constructor for our board
	 * @param b	The board to copy
	 */
	public Board (Board b) {
		this.range = b.range;
		this.size = b.size;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.board[i][j] = b.board[i][j];
			}
		}
	}

	/**
	 * Static method to generate a random board
	 * @param size		The size of the board we want to generate (its side)
	 * @param range		The range, meaning the number of different cells possible
	 * @return			The generated board
	 */
	public static Board generateRandomBoard (int size, int range) {
		Board b = new Board(size, range);
		b.shuffle();
		return b;
	}

	/**
	 * Static method to generate a random board
	 * @param size		The size of the board we want to generate (its side)
	 * @param range		The range, meaning the number of different cells possible
	 * @param seed		The seed we want to use for our generator
	 * @return			The generated board
	 */
	public static Board generateRandomBoard (int size, int range, Integer seed) {
		if (seed != null){
			rnd.setSeed(seed);
		}
		return generateRandomBoard(size,range);
	}

	/**
	 * Method to swap two cases
	 * @param firstCase		The 1D coordinate of the first case
	 * @param secondCase	The 1D coordinate of the second case
	 */
	public void swap (int firstCase, int secondCase) {
		swap(firstCase / this.size, firstCase % this.size, secondCase / this.size, secondCase % this.size);
	}

	/**
	 * Method to swap two cases
	 * @param firstCaseLine			The line coordinate of the first case
	 * @param firstCaseColumn		The column coordinate of the first case
	 * @param secondCaseLine		The line coordinate of the second case
	 * @param secondCaseColumn		The column coordinate of the second case
	 */
	public void swap (int firstCaseLine, int firstCaseColumn, int secondCaseLine, int secondCaseColumn) {
		int temp = this.board[firstCaseLine][firstCaseColumn];
		this.board[firstCaseLine][firstCaseColumn] = this.board[secondCaseLine][secondCaseColumn];
		this.board[secondCaseLine][secondCaseColumn] = temp;
	}

	/**
	 * Method to set a cell to a value
	 * @param index	The 1D coordinate of the cell we want to set
	 * @param value	The new value
	 */
	public void setCase (int index, int value) {
		setCase(index / this.size, index % this.size, value);
	}

	/**
	 * Method to set a cell to a value
	 * @param line	The line coordinate of the cell we want to set
	 * @param col	The column coordinate of the cell we want to set
	 * @param value	The new value
	 */
	public void setCase (int line, int col, int value) {
		this.board[line][col] = value;
	}

	/**
	 * Method to set a random value for a cell
	 * @param index	The 1D coordinate of the cell we want to set
	 */
	public void setRandomCase(int index){
		this.setCase(index,rnd.nextInt(this.range));
	}

	/**
	 * Method to set a random value for a cell
	 * @param line	The line coordinate of the cell we want to set
	 * @param col	The column coordinate of the cell we want to set
	 */
	public void setRandomCase(int line,int col){
		this.setCase(line,col,rnd.nextInt(this.range));
	}

	/**
	 * Method to get the value of a cell
	 * @param index	The 1D coordinate of the cell we want to get
	 * @return		The value of the cell
	 */
	public int getCase (int index) {
		return getCase(index / this.size, index % this.size);
	}

	/**
	 * Method to get the value of a cell
	 * @param line	The line coordinate of the cell we want to get
	 * @param col	The column coordinate of the cell we want to get
	 * @return		The value of the cell
	 */
	public int getCase (int line, int col) {
		return board[line][col];
	}

	/**
	 * @return	The size of the side of the board
	 */
	public int getSize () {
		return size;
	}

	/**
	 * Method to check if a cell is empty
	 * @param index	The 1D coordinate of the cell we want to check
	 * @return	If the cell is empty
	 */
	public boolean isEmpty (int index) {
		return isEmpty(index / this.size, index % this.size);
	}

	/**
	 * Method to check if a cell is empty
	 * @param line	The line coordinate of the cell we want to check
	 * @param col	The column coordinate of the cell we want to check
	 * @return	If the cell is empty
	 */

	public boolean isEmpty (int line, int col) {
		return this.board[line][col] == -1;
	}

	/**
	 * Method to set a case as destroyed (-1)
	 * @param index	The 1D coordinate of the cell we want to destroy
	 */
	public void destroyCase (int index) {
		this.setCase(index, -1);
	}

	/**
	 * Method to set a case as destroyed (-1)
	 * @param line		The line coordinate of the cell we want to destroy
	 * @param column	The column coordinate of the cell we want to destroy
	 */
	public void destroyCase (int line, int column) {
		this.setCase(line, column, -1);
	}

	/**
	 * Method to destroy a range of cases
	 * @param start		The start index where to destroy (1D coordinate)
	 * @param finish	The finish index until where to destroy (1D coordinate) (finish > start)
	 * @param line		If we proceed in a line (we will proceed in the column otherwise)
	 * @return			An ArrayList of the individual cell coordinates destroyed
	 */
	ArrayList<Integer> destroyCases (int start, int finish, boolean line){
		ArrayList<Integer> toReturn = new ArrayList<>();
		for (int i = start; i <= finish ; i = line ? i+1 : i+this.size) {
			destroyCase(i);
			toReturn.add(i);
		}
		return toReturn;
	}

	/**
	 * @return	String representation of the board
	 */
	@Override
	public String toString () {
		String st = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				st += board[i][j] + "\t";
			}
			st += "\n";
		}
		return st;
	}

	/**
	 * Method that shuffles the board (regenerates random values for each cell)
	 */
	public void shuffle() {
		for (int i = 0; i < size * size; i++) {
			this.setCase(i, rnd.nextInt(range));
		}
	}
}
