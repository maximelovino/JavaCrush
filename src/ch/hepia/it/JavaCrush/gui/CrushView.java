package ch.hepia.it.JavaCrush.gui;

import ch.hepia.it.JavaCrush.game.Board;
import ch.hepia.it.JavaCrush.game.Checker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private Board game;
	int clickedFirst = -1;
	Random rnd;

	public CrushView (Random rnd) {
		super(new GridLayout(SIZE,SIZE));
		System.out.println(images.length);
		this.game = new Board(SIZE);
		this.rnd = rnd;
		for (int i = 0; i < buttons.length; i++) {
			int random = rnd.nextInt(images.length);
			this.game.setCase(random,i);
			buttons[i] = new JButton(new ImageIcon(new ImageIcon("assets/"+images[random]).getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT)));
			buttons[i].setName(String.valueOf(i));
			buttons[i].addActionListener(e -> {
				JButton source = (JButton) e.getSource();
				System.out.println(source.getName());
				int score = 0;
				Checker[] workerThreads = new Checker[SIZE *2];
				if (clickedFirst == -1){
					clickedFirst = Integer.valueOf(source.getName());
				}else{
					System.out.println("it's the second one");
					int secondClick = Integer.valueOf(source.getName());
					//TODO check with mods if we're on far left or far right
					//TODO perhaps do check in swap function
					if (secondClick == clickedFirst + 1 || secondClick == clickedFirst -1 || secondClick == clickedFirst + SIZE || secondClick == clickedFirst - SIZE){
						swapTwoButtons(clickedFirst, secondClick);
						for (int j = 0; j < SIZE; j++) {
							workerThreads[j] = new Checker(j,-1,this.game);
							workerThreads[j+SIZE] = new Checker(-1,j,this.game);
							workerThreads[j].start();
							workerThreads[j+SIZE].start();
						}
						for (int j = 0; j < SIZE; j++) {
							try {
								workerThreads[j].join();
								workerThreads[j+SIZE].join();
								score += workerThreads[j].getScore();
								score += workerThreads[j+SIZE].getScore();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
					System.out.println(score);
					clickedFirst = -1;
				}
			});
			this.add(buttons[i]);
		}
	}

	private void swapTwoButtons(int a, int b){
		System.out.println("swapping");
		ImageIcon iconA = (ImageIcon) buttons[a].getIcon();
		ImageIcon iconB = (ImageIcon) buttons[b].getIcon();
		buttons[b].setIcon(iconA);
		buttons[a].setIcon(iconB);
		this.game.swap(a,b);
		this.update(this.getGraphics());
	}
}
