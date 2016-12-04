package ch.hepia.it.JavaCrush.game;

import ch.hepia.it.JavaCrush.gui.CrushView;

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

	public void boardCheck() {
		int cnt,lastVal;
		for (int toCheck = 0; toCheck < this.b.getSize(); toCheck++) {
			cnt = 0;
			lastVal = -1;
			for (int i = 0; i < this.b.getSize(); i++) {
				int val = line ? this.b.getCase(toCheck, i) : this.b.getCase(i, toCheck);
				if (val == lastVal && val != -1) {
					cnt++;
					if (i == this.b.getSize() - 1) {
						if (cnt >= 3) {
							int toAdd = cnt == 3 ? 50 : cnt == 4 ? 150 : 400;
							score.addAndGet(toAdd);
							System.out.println("found something at " + i);
							effect.set(true);
							for (int j = 0; j < cnt; j++) {
								int idx = i - j;
								int toSync;
								System.out.println("deleting " + idx);
								if (line) {
									this.b.destroyCase(toCheck, idx);
									toSync = toCheck * this.b.getSize() + idx;
								} else {
									this.b.destroyCase(idx, toCheck);
									toSync = idx * this.b.getSize() + toCheck;
								}
								view.syncButtonsWithGame(toSync);
							}
						}
					}
				} else {
					if (cnt >= 3) {
						System.out.println("found something at " + i);
						effect.set(true);
						//50 pour 3 cases identiques alignées, 150 pour 4 et 400 pour 5
						int toAdd = cnt == 3 ? 50 : cnt == 4 ? 150 : 400;
						score.addAndGet(toAdd);
						for (int j = 1; j <= cnt; j++) {
							int idx = i - j;
							int toSync;
							System.out.println("deleting " + idx);
							if (line) {
								this.b.destroyCase(toCheck, idx);
								toSync = toCheck * this.b.getSize() + idx;
							} else {
								this.b.destroyCase(idx, toCheck);
								toSync = idx * this.b.getSize() + toCheck;
							}
							view.syncButtonsWithGame(toSync);
						}
					}
					lastVal = val;
					cnt = 1;
				}
			}
		}
	}

	public AtomicInteger getScore () {
		return score;
	}
}
