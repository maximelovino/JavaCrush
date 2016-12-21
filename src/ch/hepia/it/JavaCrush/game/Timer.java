package ch.hepia.it.JavaCrush.game;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread that will control the timer of the game
 */
public class Timer extends Thread {
	private int time;
	private AtomicBoolean running;
	private JLabel timerText;

	/**
	 * Main constructor for the timer thread
	 * @param time		The time we want the game to run for
	 * @param running	A boolean specifying if the game is running or not
	 * @param timerText	The label in our main window where the time is written (will be updated from this thread)
	 */
	public Timer (int time, AtomicBoolean running, JLabel timerText) {
		this.time = time;
		this.running = running;
		this.timerText = timerText;
	}

	/**
	 * Run method for this thread, we update the timer label every second
	 */
	@Override
	public void run () {
		while (time > 0){
			try {
				Thread.sleep(1000);
				time--;
				if (time <= 10){
					timerText.setForeground(Color.red);
				}
				timerText.setText(String.valueOf(time));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("timer done");
		running.set(false);
	}
}
