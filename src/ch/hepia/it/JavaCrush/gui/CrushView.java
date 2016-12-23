package ch.hepia.it.JavaCrush.gui;

import ch.hepia.it.JavaCrush.game.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

/**
 * View for our JavaCrush board
 */
public class CrushView extends JPanel {
	private Assets assets;
	private final int size;
	private JButton[] buttons;
	private Board game;
	private int firstCase = -1;
	private Lock lock;

	/**
	 * Constructor for our view
	 * @param assets	An assets object for the icon pack of the board
	 * @param size		The size of the side of the board
	 * @param game		The board of the game (the model)
	 * @param lock		The lock, for synchronization
	 */
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

	/**
	 * Method to update the view from the model (updates the whole board)
	 */
	public void syncButtonsWithGame () {
		for (int i = 0; i < buttons.length; i++) {
			int val = this.game.getCase(i);
			ImageIcon icn = (ImageIcon) assets.get(val);
			buttons[i].setIcon(icn);
		}
	}

	/**
	 * Method to update specific cells of the view from the model
	 * @param ids	The cells to update
	 */
	public void syncButtonsWithGame (int... ids){
		for (int b : ids) {
			int val = this.game.getCase(b);
			ImageIcon icn = (ImageIcon) assets.get(val);
			buttons[b].setIcon(icn);
		}
	}

	/**
	 * Method to update specific cells of the view from the model
	 * @param ids	A list of cells to update
	 */
	public void syncButtonsWithGame (ArrayList<Integer> ids){
		for (int b : ids) {
			syncButtonsWithGame(b);
		}
	}

	/**
	 * A method to change assets of the view
	 * @param a	The new assets
	 */
	public void setAssets(Assets a){
		this.assets = a;
		syncButtonsWithGame();
	}
}
