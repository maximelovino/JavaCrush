package ch.hepia.it.JavaCrush.game;

import ch.hepia.it.JavaCrush.gui.CrushView;

public class Mover extends Thread{
	private Board b;
	private int col;
	private CrushView view;

	public Mover (Board b, int col, CrushView view) {
		this.b = b;
		this.col = col;
		this.view = view;
	}

	@Override
	public void run () {
		while(true){
		int emptyCnt = 0;
		for (int i = this.b.getSize() - 1; i >= 0 ; i--) {
			if (this.b.getCase(i,col) == -1){
				emptyCnt ++;
			}else{
				if (emptyCnt > 0){
					this.b.swap(i,col,i+emptyCnt,col);
					view.syncButtonsWithGame();
				}
			}
		}
		}


	}
}
