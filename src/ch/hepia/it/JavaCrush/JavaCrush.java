package ch.hepia.it.JavaCrush;

import ch.hepia.it.JavaCrush.game.*;
import ch.hepia.it.JavaCrush.game.Timer;
import ch.hepia.it.JavaCrush.gui.Assets;
import ch.hepia.it.JavaCrush.gui.CrushView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JavaCrush {
	public static void main (String[] args) {
		int size = 10;
		String assetsPath = "assets";
		Assets assets = new Assets(assetsPath);
		int max = assets.size();
		int seed = 42;
		if (args.length > 0){
			seed = Integer.valueOf(args[0]);
		}
		Board b = Board.generateRandomBoard(size,max, seed);
		System.out.println(b);
		JFrame frame = new JFrame("JavaCrush");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		AtomicBoolean checkingH = new AtomicBoolean(false);
		AtomicBoolean checkingV = new AtomicBoolean(false);
		AtomicBoolean effect = new AtomicBoolean(false);
		AtomicBoolean running = new AtomicBoolean(true);
		Lock lock = new ReentrantLock();
		CrushView view = new CrushView(assets,size,b, lock, checkingH, checkingV, effect);
		final int TIME = 40;
		JLabel timing = new JLabel(String.valueOf(TIME));
		Timer timer = new Timer(TIME,running, timing);
		Mover mover = new Mover(b,view, lock, running);
		AtomicInteger score = new AtomicInteger(0);

		Checker lineChecker = new Checker(true,b,view, score, lock, checkingH, checkingV, effect, running);
		Checker colChecker = new Checker(false,b,view, score, lock, checkingH, checkingV, effect, running);
		lineChecker.start();
		colChecker.start();
		mover.start();
		timer.start();

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(view,BorderLayout.CENTER);

		JPanel header = new JPanel(new FlowLayout());
		JButton shuffleButton = new JButton("Shuffle the board");
		header.add(shuffleButton);
		header.add(timing);

		

		frame.getContentPane().add(header,BorderLayout.PAGE_START);
		frame.pack();
		frame.setSize(new Dimension(800,800));
		frame.setVisible(true);

		try {
			timer.join();
			System.out.println("timer joined");
			mover.join();
			System.out.println("mover joined");
			colChecker.join();
			System.out.println("col checker joined");
			lineChecker.join();
			System.out.println("Joined everything");
			System.out.println("score "+score);
			JOptionPane.showMessageDialog(frame,"Your score is "+score.get()+" points");
//			ScoreView popup = new ScoreView(score.get());
//			popup.setSize(new Dimension(400,400));
//			popup.setVisible(true);
//			popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
