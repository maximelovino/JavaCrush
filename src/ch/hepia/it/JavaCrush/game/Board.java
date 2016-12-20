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

	public static Random getRnd () {
		return rnd;
	}

	public Board (int size, int range) {
		this.size = size;
		this.range = range;
		this.board = new int[size][size];
	}

	public Board (int[] board, int range) {
		this.range = range;
		this.size = (int) Math.sqrt(board.length);
		for (int i = 0; i < board.length; i++) {
			this.board[i / this.size][i % this.size] = board[i];
		}
	}

	public Board (Board b) {
		this.range = b.range;
		this.size = b.size;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.board[i][j] = b.board[i][j];
			}
		}
	}

	public static Board generateRandomBoard (int size, int range) {
		Board b = new Board(size, range);
		b.shuffle();
		return b;
	}

	public static Board generateRandomBoard (int size, int range, Integer seed) {
		if (seed != null){
			rnd.setSeed(seed);
		}
		return generateRandomBoard(size,range);
	}

	public void swap (int firstCase, int secondCase) {
		swap(firstCase / this.size, firstCase % this.size, secondCase / this.size, secondCase % this.size);
	}

	public void swap (int firstCaseLine, int firstCaseColumn, int secondCaseLine, int secondCaseColumn) {
		int temp = this.board[firstCaseLine][firstCaseColumn];
		this.board[firstCaseLine][firstCaseColumn] = this.board[secondCaseLine][secondCaseColumn];
		this.board[secondCaseLine][secondCaseColumn] = temp;
	}

	public void setCase (int index, int value) {
		setCase(index / this.size, index % this.size, value);
	}

	public void setCase (int line, int col, int value) {
		this.board[line][col] = value;
	}

	public void setRandomCase(int index){
		this.setCase(index,rnd.nextInt(this.range));
	}

	public void setRandomCase(int line,int col){
		this.setCase(line,col,rnd.nextInt(this.range));
	}

	public int getCase (int index) {
		return getCase(index / this.size, index % this.size);
	}

	public int getCase (int line, int col) {
		return board[line][col];
	}

	public int[][] getBoard () {
		return board;
	}

	public int getSize () {
		return size;
	}

	public boolean isEmpty (int index) {
		return isEmpty(index / this.size, index % this.size);
	}

	public boolean isEmpty (int line, int col) {
		return this.board[line][col] == -1;
	}

	/**
	 * Method to set a case as destroyed (-1)
	 *
	 * @param index	A 1D coordinate of the case
	 */
	public void destroyCase (int index) {
		this.setCase(index, -1);
	}

	/**
	 * Method to set a case as destroyed (-1)
	 *
	 * @param line		The line of the case to destroy
	 * @param column	The column of the case to destroy
	 */
	public void destroyCase (int line, int column) {
		this.setCase(line, column, -1);
	}

	ArrayList<Integer> destroyCases (int start, int finish, boolean line){
		ArrayList<Integer> toReturn = new ArrayList<>();
		for (int i = start; i <= finish ; i = line ? i+1 : i+this.size) {
			destroyCase(i);
			toReturn.add(i);
		}
		return toReturn;
	}

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

	public void shuffle() {
		for (int i = 0; i < size * size; i++) {
			this.setCase(i, rnd.nextInt(range));
		}
	}
}
