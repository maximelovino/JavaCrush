package ch.hepia.it.JavaCrush.gui;

import ch.hepia.it.JavaCrush.game.Board;

import javax.swing.*;
import java.awt.*;

public class CrushView extends JPanel {

	private final String[] assets;
	private final int size;
	private JButton[] buttons;
	private Board game;
	int firstCase = -1;

	public CrushView (String[] assets, int size, Board game) {
		super(new GridLayout(size, size));
		this.assets = assets;
		this.size = size;
		this.game = game;
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
						this.game.swap(firstCase, temp);
						System.out.println("swapped " + temp + " with " + firstCase);
						syncButtonsWithGame();
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
			ImageIcon icn = val == -1 ? new ImageIcon() : new ImageIcon(new ImageIcon(assets[this.game.getCase(i)]).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
			buttons[i].setIcon(icn);
		}
		update(this.getGraphics());
	}
}
