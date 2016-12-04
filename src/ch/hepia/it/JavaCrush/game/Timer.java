package ch.hepia.it.JavaCrush.game;

import java.util.concurrent.atomic.AtomicBoolean;

public class Timer extends Thread {
	private int time;
	private AtomicBoolean running;

	public Timer (int time, AtomicBoolean running) {
		this.time = time;
		this.running = running;
	}

	@Override
	public void run () {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("timer done");
		running.set(false);
	}
}
