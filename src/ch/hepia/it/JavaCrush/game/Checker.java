package ch.hepia.it.JavaCrush.game;

import ch.hepia.it.JavaCrush.gui.CrushView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/**
 * Thread that checks the board and destroy matches
 */
public class Checker extends Thread {
	private boolean line;
	private Board b;
	private CrushView view;
	private AtomicInteger score;
	private Lock lock;
	private AtomicBoolean running;

	/**
	 * Constructor for the checker thread
	 * @param line		If the checker is a line checker (otherwise column checker)
	 * @param b			The board
	 * @param view		The view
	 * @param score		The score, to update
	 * @param lock		A lock, for synchronization
	 * @param running	A flag to tell if the program is running
	 */
	public Checker (boolean line, Board b, CrushView view, AtomicInteger score, Lock lock, AtomicBoolean running) {
		this.line = line;
		this.b = b;
		this.view = view;
		this.score = score;
		this.lock = lock;
		this.running = running;
	}

	/**
	 * Run method for this thread
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
			boardCheck();
			this.lock.unlock();
		}
	}

	/**
	 * Method that will check the board and set empty cases for the matching cases
	 */
	public void boardCheck () {
		int cnt, lastVal;
		ArrayList<Integer> toSync = new ArrayList<>();
		//toCheck is the line or column we're checking now
		for (int toCheck = 0; toCheck < this.b.getSize(); toCheck++) {
			cnt = 0;
			lastVal = -1;
			//this is how we loop through the line/column
			for (int i = 0; i < this.b.getSize(); i++) {
				int val = line ? this.b.getCase(toCheck, i) : this.b.getCase(i, toCheck);
				if (val == lastVal && val != -1) {
					cnt++;
					//if we arrive at the end and we have 3 or more at the end
					if (i == this.b.getSize() - 1) {
						if (cnt >= 3) {
							int toAdd = cnt == 3 ? 50 : cnt == 4 ? 150 : 400;
							score.addAndGet(toAdd);
							System.out.println("found something at " + i);
							int start = line ? toCheck * this.b.getSize() + (i - cnt + 1) : (i - cnt + 1) * this.b.getSize() + toCheck;
							int finish = line ? toCheck * this.b.getSize() + i : i * this.b.getSize() + toCheck;
							toSync.addAll(this.b.destroyCases(start, finish, line));
						}
					}
				} else {
					if (cnt >= 3) {
						System.out.println("found something at " + i);
						int toAdd = cnt == 3 ? 50 : cnt == 4 ? 150 : 400;
						score.addAndGet(toAdd);
						int start = line ? toCheck * this.b.getSize() + (i-1 - cnt) : (i - cnt) * this.b.getSize() + toCheck;
						int finish = line ? toCheck * this.b.getSize() + (i-1) : (i-1) * this.b.getSize() + toCheck;
						toSync.addAll(this.b.destroyCases(start, finish, line));
					}
					lastVal = val;
					cnt = 1;
				}
			}
		}
		if (!toSync.isEmpty()) view.syncButtonsWithGame(toSync);
	}
}
