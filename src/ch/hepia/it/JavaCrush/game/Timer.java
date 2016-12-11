package ch.hepia.it.JavaCrush.game;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Timer extends Thread {
	private int time;
	private AtomicBoolean running;
	private JLabel timerText;

	public Timer (int time, AtomicBoolean running, JLabel timerText) {
		this.time = time;
		this.running = running;
		this.timerText = timerText;
	}

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
