package lib;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class Monitor {
	ReentrantLock threads = new ReentrantLock();
	public void customWait()
	{
		try {
			threads.wait();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public class ThreadChecker implements Runnable {
		/**
		 * Will notify all threads
		 */
		public void run() {
			threads.notifyAll();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
}
