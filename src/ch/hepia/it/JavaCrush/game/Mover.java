package ch.hepia.it.JavaCrush.game;

import ch.hepia.it.JavaCrush.gui.CrushView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

/**
 * Thread that will cascade the cells to cover the empty ones and generate new ones on top of the columns
 */
public class Mover extends Thread {
	private Board b;
	private CrushView view;
	private Lock lock;
	private AtomicBoolean running;

	/**
	 * Main constructor
	 * @param b			The game board
	 * @param view		The view of the game
	 * @param lock		The lock to synchronize with other threads
	 * @param running	A boolean stating if the application is running or it has finished (exit condition)
	 */
	public Mover (Board b, CrushView view, Lock lock, AtomicBoolean running) {
		this.b = b;
		this.view = view;
		this.lock = lock;
		this.running = running;
	}

	/**
	 * Override of the run method
	 * We take the lock, call our move function and then release the lock
	 * Sleep of 10 ms is added to ease the CPU
	 */
	@Override
	public void run () {
		while (running.get()) {
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.lock.lock();
			move();
			this.lock.unlock();
		}


	}

	/**
	 * Function that will take each column and put the empty cells on top (cascading the others) and replace them with new ones
	 */
	public void move () {
		int emptyCnt;
		ArrayList<Integer> toSync = new ArrayList<>();
		for (int col = 0; col < this.b.getSize(); col++) {
			emptyCnt = 0;
			for (int line = this.b.getSize() - 1; line >= 0; line--) {
				if (this.b.isEmpty(line,col)) {
					emptyCnt++;
					this.b.setRandomCase(line,col);
					toSync.add(line * this.b.getSize() + col);
				} else {
					if (emptyCnt > 0) {
						this.b.swap(line, col, line + emptyCnt, col);
						toSync.add(line * this.b.getSize() + col);
						toSync.add((line + emptyCnt) * this.b.getSize() + col);
					}
				}
			}
		}
		view.syncButtonsWithGame(toSync);
	}
}
