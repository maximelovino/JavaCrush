package ch.hepia.it.JavaCrush;

import ch.hepia.it.JavaCrush.game.Board;
import ch.hepia.it.JavaCrush.game.Checker;
import ch.hepia.it.JavaCrush.game.Mover;
import ch.hepia.it.JavaCrush.gui.CrushView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JavaCrush {
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
		int seed = 42;
		if (args.length > 0){
			seed = Integer.valueOf(args[0]);
		}
		Board b = Board.generateRandomBoard(size,max, seed);
		System.out.println(b);
		JFrame frame = new JFrame();
		Boolean checkingH = false;
		Boolean checkingV = false;
		Boolean effect = false;
		CrushView view = new CrushView(assets,size,b, checkingH, checkingV, effect);
		Lock lock = new ReentrantLock();
		Mover mover = new Mover(b,view, lock);
		Integer score = 0;

		Checker lineChecker = new Checker(true,b,view, score, lock, checkingH, checkingV, effect);
		Checker colChecker = new Checker(false,b,view, score, lock, checkingH, checkingV, effect);
		lineChecker.start();
		colChecker.start();
		mover.start();

		frame.add(view);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(800,800));
		frame.setVisible(true);
	}
}
