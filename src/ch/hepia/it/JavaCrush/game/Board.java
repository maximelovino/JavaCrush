package ch.hepia.it.JavaCrush.game;

public class Board {
	private int[] board;
	private int size;

	public Board (int size) {
		this.size = size;
		this.board = new int[size*size];
	}

	public Board (int[] board) {
		this.size = (int) Math.sqrt(board.length);
		this.board = board;
	}

	public void swap (int a, int b){
		int temp = board[a];
		board[a] = board[b];
		board[b] = temp;
	}

	public void setCase (int value, int index){
		board[index] = value;
	}

	public int getCase (int index){
		return board[index];
	}

	public int[] getBoard () {
		return board;
	}

	public int getSize () {
		return size;
	}
}
