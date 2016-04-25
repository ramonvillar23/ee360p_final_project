package lib;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public abstract class Monitor {
	ReentrantLock mutex = new ReentrantLock();
	Condition threadChecker = mutex.newCondition();
	Condition waitingThreads = mutex.newCondition();	
	/**
	 * Wrapper class for what the monitor will do.
	 * This allows to notify other threads when monitor
	 * returns normally.
	 */
	public void runMonitor()
	{
		mutex.lock();
		this.mainMethod();
		mutex.unlock();
	}
	

	/**
	 * User code goes here
	 */
	public abstract void mainMethod();

	public class ThreadChecker implements Runnable {
		/**
		 * Will check whenever lock is free to see
		 * if a thread can try to get the monitor
		 */
		@Override
		public void run() {
			mutex.lock();
			if(mutex.hasQueuedThreads())
			{
				mutex.notifyAll();
			}
			mutex.unlock();
		}
		
	}
}
