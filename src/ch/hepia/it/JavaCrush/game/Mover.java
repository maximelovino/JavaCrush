package ch.hepia.it.JavaCrush.game;

import ch.hepia.it.JavaCrush.gui.CrushView;

import java.util.concurrent.locks.Lock;

public class Mover extends Thread{
	private Board b;
	private CrushView view;
	private Lock lock;

	public Mover (Board b, CrushView view, Lock lock) {
		this.b = b;
		this.view = view;
		this.lock = lock;
	}

	@Override
	public void run () {
		while(true) {
			this.lock.lock();
			move();
			this.lock.unlock();
		}


	}

	public void move(){
		int emptyCnt;
		for (int col = 0; col < this.b.getSize(); col++) {
			emptyCnt = 0;
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
			for (int j = 0; j < emptyCnt; j++) {
				this.b.setRandomCase(j,col);
				view.syncButtonsWithGame();
			}
		}
	}
}
