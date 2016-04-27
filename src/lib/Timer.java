package lib;

public class Timer {
	private long startTime;
	private long endTime;
	
	public Timer() {
		startTime = 0;
		endTime = 0;
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	public void stop() { 
		endTime = System.nanoTime();
	}
	
	public long getTime() {
		return endTime - startTime;
	}
}