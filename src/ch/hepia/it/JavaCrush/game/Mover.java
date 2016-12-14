package ch.hepia.it.JavaCrush.game;

import ch.hepia.it.JavaCrush.gui.CrushView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class Mover extends Thread {
	private Board b;
	private CrushView view;
	private Lock lock;
	private AtomicBoolean running;

	public Mover (Board b, CrushView view, Lock lock, AtomicBoolean running) {
		this.b = b;
		this.view = view;
		this.lock = lock;
		this.running = running;
	}

	@Override
	public void run () {
		while (running.get()) {
			this.lock.lock();
			move();
			this.lock.unlock();
		}


	}

	public void move () {
		//TODO use a list of everything to sync and sync at the end
		int emptyCnt;
		ArrayList<Integer> toSync = new ArrayList<>();
		for (int col = 0; col < this.b.getSize(); col++) {
			emptyCnt = 0;
			for (int line = this.b.getSize() - 1; line >= 0; line--) {
				if (this.b.isEmpty(line,col)) {
					emptyCnt++;
				} else {
					if (emptyCnt > 0) {
						this.b.swap(line, col, line + emptyCnt, col);
						toSync.add(line * this.b.getSize() + col);
						toSync.add((line + emptyCnt) * this.b.getSize() + col);
					}
				}
			}
			for (int j = 0; j < emptyCnt; j++) {
				this.b.setRandomCase(j, col);
				toSync.add(j * this.b.getSize() + col);
			}
		}
		view.syncButtonsWithGame(toSync);
	}
}
