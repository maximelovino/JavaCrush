package ch.hepia.it.JavaCrush.game;

public class Timer extends Thread {
	private int time;
	private Boolean running;

	public Timer (int time, Boolean running) {
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
		running = false;
	}
}
