package ch.hepia.it.JavaCrush.tests;

import ch.hepia.it.JavaCrush.game.Board;

public class BoardTest {
	public static void main (String[] args) {

		Board b = Board.generateRandomBoard(10,10);
		System.out.println(b);
		b.destroyCase(5,0);
		//b.destroyCase(6,0);
		b.destroyCase(7,0);
		System.out.println(b);

		int emptyCnt = 0;
		for (int i = b.getSize() - 1; i >= 0 ; i--) {
			if (b.getCase(i,0) == -1){
				emptyCnt ++;
			}else{
				b.swap(i,0,i+emptyCnt,0);
			}
		}
		System.out.println(b);
	}
}
