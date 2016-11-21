package ch.hepia.it.JavaCrush.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CrushView extends JPanel {
	private static final String[] images = {
			"bird.png",
			"cat.png",
			"cricket.png",
			"dolphin.png",
			"dragon_fly.png",
			"elephant.png",
			"gnome_panel_fish.png",
			"jelly_fish.png",
			"kbugbuster.png",
			"penguin.png",
			"pig.png"
	};

	private static final int SIZE = 10;
	private JButton[] buttons = new JButton[SIZE*SIZE];
	Random rnd;

	public CrushView (Random rnd) {
		super(new GridLayout(SIZE,SIZE));
		System.out.println(images.length);
		this.rnd = rnd;
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(new ImageIcon(new ImageIcon("assets/"+images[rnd.nextInt(images.length)]).getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT)));
			buttons[i].setName(String.valueOf(i));
			this.add(buttons[i]);
		}
	}
}
