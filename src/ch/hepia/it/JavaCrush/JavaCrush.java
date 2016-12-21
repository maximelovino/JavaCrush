package ch.hepia.it.JavaCrush;

import ch.hepia.it.JavaCrush.game.Board;
import ch.hepia.it.JavaCrush.game.Checker;
import ch.hepia.it.JavaCrush.game.Mover;
import ch.hepia.it.JavaCrush.game.Timer;
import ch.hepia.it.JavaCrush.gui.Assets;
import ch.hepia.it.JavaCrush.gui.CrushView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JavaCrush {
	public static void main (String[] args) {
		int size = 10;

		String assetsPath = "assets/basic";
		Assets basicAssets = new Assets(assetsPath, "Basic");
		ArrayList<Assets> assetsCollection = new ArrayList<>();
		Assets ccAssets = new Assets("assets/cc","Creative Cloud");
		Assets progAssets = new Assets("assets/prog", "Programming");
		Assets octoAssets = new Assets("assets/octo", "Octocats Github");
		assetsCollection.add(basicAssets);
		assetsCollection.add(ccAssets);
		assetsCollection.add(progAssets);
		assetsCollection.add(octoAssets	);

		int max = basicAssets.size();

		JFrame frame = new JFrame("JavaCrush");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Board b = Board.generateRandomBoard(size,max);
		System.out.println(b);
		final int TIME = Integer.valueOf(JOptionPane.showInputDialog(frame,"How long should the timer be in seconds?", 30));
		AtomicBoolean running = new AtomicBoolean(true);
		Lock lock = new ReentrantLock();
		CrushView view = new CrushView(basicAssets,size,b, lock);
		JLabel timing = new JLabel(String.valueOf(TIME));
		Timer timer = new Timer(TIME,running, timing);
		Mover mover = new Mover(b,view, lock, running);
		AtomicInteger score = new AtomicInteger(0);

		Checker lineChecker = new Checker(true,b,view, score, lock, running);
		Checker colChecker = new Checker(false,b,view, score, lock, running);
		lineChecker.start();
		colChecker.start();
		mover.start();
		timer.start();

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(view,BorderLayout.CENTER);

		JPanel header = new JPanel(new FlowLayout());
		JButton shuffleButton = new JButton("Shuffle the board");
		JComboBox<Assets> picker = new JComboBox<>(new Vector<>(assetsCollection));
		header.add(picker);
		header.add(shuffleButton);
		header.add(timing);

		shuffleButton.addActionListener(e -> {
			lock.lock();
			b.shuffle();
			view.syncButtonsWithGame();
			lock.unlock();
		});

		picker.addActionListener(e -> {
			view.setAssets((Assets)picker.getSelectedItem());
		});


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
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
