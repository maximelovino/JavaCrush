package ch.hepia.it.JavaCrush.gui;

import ch.hepia.it.JavaCrush.game.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrushView extends JPanel{

	private final String[] assets;
	private final int size;
	private JButton[] buttons;
	private Board game;

	public CrushView (String[] assets, int size, Board game) {
		super(new GridLayout(size,size));
		this.assets = assets;
		this.size = size;
		this.game = game;
		this.buttons = new JButton[this.size * this.size];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].setName(String.valueOf(i));
			buttons[i].addActionListener(e -> {
				JButton src = (JButton) e.getSource();
				System.out.println(src.getName());
			});
			this.add(buttons[i]);
		}
		syncButtonsWithGame();

	}

	public void syncButtonsWithGame(){
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setIcon(new ImageIcon(new ImageIcon(assets[this.game.getCase(i)]).getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT)));
		}
		update(this.getGraphics());
	}
}
