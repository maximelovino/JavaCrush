package ch.hepia.it.JavaCrush.game;

import ch.hepia.it.JavaCrush.gui.CrushView;

public class Checker extends Thread {
	private int toCheck;
	private boolean line;
	private Board b;
	private CrushView view;

	public Checker (int toCheck, boolean line, Board b, CrushView view) {
		this.toCheck = toCheck;
		this.line = line;
		this.b = b;
		this.view = view;
	}

	@Override
	public void run () {
		//TODO replace with other variables from timer, or main, so we can then join cleanly
		//TODO find a way to get the score cleanly permanently, or at least at the end (with the join)
		while (true) boardCheck();
	}

	public synchronized void boardCheck(){
		//TODO add scoring
		//TODO launch replacement threads at end of each check??
		int cnt = 0;
		int lastVal = -1;

		for (int i = 0; i < this.b.getSize(); i++) {
			int val = line ? this.b.getCase(toCheck,i) : this.b.getCase(i, toCheck);
			if (val == lastVal && val != -1){
				cnt++;
				if (i == this.b.getSize()-1){
					if (cnt >= 3){
						System.out.println("found something at "+i);
						for (int j = 0; j < cnt; j++) {
							int idx = i - j;
							System.out.println("deleting "+idx);
							if (line){
								this.b.destroyCase(toCheck,idx);
							}else{
								this.b.destroyCase(idx,toCheck);
							}
							view.syncButtonsWithGame();
						}
					}
				}
			}else{
				if (cnt >= 3){
					System.out.println("found something at "+i);
					for (int j = 1; j <= cnt; j++) {
						int idx = i - j;
						System.out.println("deleting "+idx);
						if (line){
							this.b.destroyCase(toCheck,idx);
						}else{
							this.b.destroyCase(idx,toCheck);
						}
						view.syncButtonsWithGame();
					}
				}
				lastVal = val;
				cnt = 1;
			}
		}
	}
}
