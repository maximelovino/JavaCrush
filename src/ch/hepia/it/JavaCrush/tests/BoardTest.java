package ch.hepia.it.JavaCrush.tests;

import ch.hepia.it.JavaCrush.game.Board;

public class BoardTest {
	public static void main (String[] args) {

		Board b = Board.generateRandomBoard(10,10);
		System.out.println(b);
		b.destroyCase(3);
		System.out.println(b.isEmpty(3));
		System.out.println(b);
	}
}
