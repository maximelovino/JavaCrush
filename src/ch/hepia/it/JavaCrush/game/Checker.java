package ch.hepia.it.JavaCrush.game;

import ch.hepia.it.JavaCrush.gui.CrushView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class Checker extends Thread {
	private boolean line;
	private Board b;
	private CrushView view;
	private AtomicInteger score;
	private Lock lock;
	private AtomicBoolean checkingH, checkingV;
	private AtomicBoolean effect;
	private AtomicBoolean running;

	public Checker (boolean line, Board b, CrushView view, AtomicInteger score, Lock lock, AtomicBoolean checkingH, AtomicBoolean checkingV, AtomicBoolean effect, AtomicBoolean running) {
		this.line = line;
		this.b = b;
		this.view = view;
		this.score = score;
		this.lock = lock;
		this.checkingH = checkingH;
		this.checkingV = checkingV;
		this.effect = effect;
		this.running = running;
	}

	@Override
	public void run () {
		//TODO replace with other variables from timer, or main, so we can then join cleanly
		//TODO find a way to get the score cleanly permanently, or at least at the end (with the join)
		while (running.get()) {
			this.lock.lock();
			boardCheck();
			if (line) checkingH.set(false);
			else checkingV.set(false);
			this.lock.unlock();
		}
	}

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
							effect.set(true);
							int start = line ? toCheck * this.b.getSize() + (i - cnt + 1) : (i - cnt + 1) * this.b.getSize() + toCheck;
							int finish = line ? toCheck * this.b.getSize() + i : i * this.b.getSize() + toCheck;
							toSync.addAll(this.b.destroyCases(start, finish, line));
						}
					}
				} else {
					if (cnt >= 3) {
						System.out.println("found something at " + i);
						effect.set(true);
						//50 pour 3 cases identiques alignées, 150 pour 4 et 400 pour 5
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

	public AtomicInteger getScore () {
		return score;
	}
}
