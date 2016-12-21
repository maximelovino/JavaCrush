package ch.hepia.it.JavaCrush.gui;

import ch.hepia.it.JavaCrush.game.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class CrushView extends JPanel {

	private Assets assets;
	private final int size;
	private JButton[] buttons;
	private Board game;
	private int firstCase = -1;
	private Lock lock;


	public CrushView (Assets assets, int size, Board game, Lock lock) {
		super(new GridLayout(size, size));
		this.assets = assets;
		this.size = size;
		this.game = game;
		this.lock = lock;
		this.buttons = new JButton[this.size * this.size];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].setName(String.valueOf(i));
			buttons[i].addActionListener(e -> {
				JButton src = (JButton) e.getSource();

				if (firstCase == -1) {
					firstCase = Integer.valueOf(src.getName());
				} else {
					int temp = Integer.valueOf(src.getName());
					if ((temp == firstCase - 1 && firstCase % size != 0) || (temp == firstCase + 1 && firstCase % size != size - 1) || temp == firstCase - 10 || temp == firstCase + 10) {
						this.lock.lock();
						this.game.swap(firstCase, temp);
						System.out.println("swapped " + temp + " with " + firstCase);
						syncButtonsWithGame(firstCase,temp);
						this.lock.unlock();
					}
					firstCase = -1;
				}
			});
			this.add(buttons[i]);
		}
		syncButtonsWithGame();

	}

	public void syncButtonsWithGame () {
		for (int i = 0; i < buttons.length; i++) {
			int val = this.game.getCase(i);
			ImageIcon icn = (ImageIcon) assets.get(val);
			buttons[i].setIcon(icn);
		}
	}

	public void syncButtonsWithGame (int... ids){
		for (int b : ids) {
			int val = this.game.getCase(b);
			ImageIcon icn = (ImageIcon) assets.get(val);
			buttons[b].setIcon(icn);
		}
	}
	public void syncButtonsWithGame (ArrayList<Integer> ids){
		for (int b : ids) {
			syncButtonsWithGame(b);
		}
	}

	public void setAssets(Assets a){
		this.assets = a;
		syncButtonsWithGame();
	}
}
