package ch.hepia.it.JavaCrush.gui;

import ch.hepia.it.JavaCrush.game.Board;

import javax.swing.*;
import java.awt.*;

public class CrushView extends JPanel {

	private final String[] assets;
	private final int size;
	private JButton[] buttons;
	private Board game;
	private int firstCase = -1;
	private Boolean checkingH, checkingV;
	private Boolean effect;


	public CrushView (String[] assets, int size, Board game, Boolean checkingH, Boolean checkingV, Boolean effect) {
		super(new GridLayout(size, size));
		this.assets = assets;
		this.size = size;
		this.game = game;
		this.checkingH = checkingH;
		this.checkingV = checkingV;
		this.effect = effect;
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
						this.checkingH = this.checkingV = true;
						this.effect = false;
						this.game.swap(firstCase, temp);
						System.out.println("swapped " + temp + " with " + firstCase);
						syncButtonsWithGame(firstCase,temp);
						while (checkingH && checkingV) {}
						//check why we enter anyway
						if (!effect){
							System.out.println("NO");
							this.game.swap(firstCase,temp);
							System.out.println("useless change was swapped");
							syncButtonsWithGame(firstCase,temp);
						}
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
			//TODO change this with collection of icons already made
			ImageIcon icn = val == -1 ? new ImageIcon() : new ImageIcon(new ImageIcon(assets[val]).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
			buttons[i].setIcon(icn);
		}
		update(this.getGraphics());
	}

	public void syncButtonsWithGame (int... ids){
		for (int b : ids) {
			int val = this.game.getCase(b);
			ImageIcon icn = val == -1 ? new ImageIcon() : new ImageIcon(new ImageIcon(assets[val]).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
			buttons[b].setIcon(icn);
		}
		update(this.getGraphics());
	}
}
