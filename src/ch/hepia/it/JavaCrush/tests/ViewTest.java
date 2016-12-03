package ch.hepia.it.JavaCrush.tests;

import ch.hepia.it.JavaCrush.game.Board;
import ch.hepia.it.JavaCrush.game.Checker;
import ch.hepia.it.JavaCrush.game.Mover;
import ch.hepia.it.JavaCrush.gui.CrushView;

import javax.swing.*;
import java.awt.*;

public class ViewTest {
	public static void main (String[] args) {
		int size = 10;
		String[] assets = {
				"assets/bird.png",
				"assets/cat.png",
				"assets/cricket.png",
				"assets/dolphin.png",
				"assets/dragon_fly.png",
				"assets/elephant.png",
				"assets/gnome_panel_fish.png",
				"assets/jelly_fish.png",
				"assets/kbugbuster.png",
				"assets/penguin.png",
				"assets/pig.png"
		};
		int max = assets.length;
		Board b = Board.generateRandomBoard(size,max);
		System.out.println(b);
		JFrame frame = new JFrame();
		CrushView view = new CrushView(assets,size,b);
		Mover[] movers = new Mover[size];


		Checker lineChecker = new Checker(true,b,view);
		Checker colChecker = new Checker(false,b,view);
		lineChecker.start();
		colChecker.start();

		for (int i = 0; i < size; i++) {
			movers[i] = new Mover(b,i,view);
			movers[i].start();
		}

		frame.add(view);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(800,800));
		frame.setVisible(true);
	}
}
