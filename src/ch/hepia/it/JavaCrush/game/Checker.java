package ch.hepia.it.JavaCrush.game;

import ch.hepia.it.JavaCrush.gui.CrushView;

import java.util.ArrayList;

public class Checker extends Thread {
	private int lineToCheck;
	private int colToCheck;
	private Board game;
	private CrushView view;
	private int score = 0;

	public Checker (int lineToCheck, int colToCheck, Board game, CrushView view) {
		super();
		this.lineToCheck = lineToCheck;
		this.colToCheck = colToCheck;
		this.game = game;
		this.view = view;
	}

	@Override
	public void run () {
		ArrayList<Integer> toDel = new ArrayList<>();
		int cnt = 0;
		int lastType = -1;
		int size = this.game.getSize();
		int currentScore = 0;
		int start = 0;
		int end = 0;
		int jump = 0;

		if (lineToCheck == -1){
			start = colToCheck;
			end = size *size;
			jump = size;
		}else if (colToCheck == -1){
			start = size * lineToCheck;
			end = size * (lineToCheck+1);
			jump = 1;
		}

		for (int i = start ; i < end; i += jump) {
			int currentType = this.game.getCase(i);
			if (lastType == -1 || lastType == currentType){
				cnt ++;
				//TODO try this, or add the score to the precedent, with +50 for 3, +100 for 4 and +250 for 5
				switch (cnt){
					case 3 :
						currentScore = 50;
						break;
					case 4 :
						currentScore = 150;
						break;
					case 5 :
						currentScore = 400;
						break;
					default:
						break;
				}
			} else{
				cnt = 1;
			}
			lastType = currentType;
		}
		score = currentScore;
	}

	public int getScore () {
		return score;
	}
}
