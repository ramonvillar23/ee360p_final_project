package lib;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

import java.util.ArrayList;
import java.util.function.Predicate;

<<<<<<< HEAD
public class Monitor {

=======
	public synchronized void customWait()
	{
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public class ThreadChecker implements Runnable {
		/**
		 * Will notify all threads
		 */
		public void run() {
			Monitor.this.notifyAll();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
>>>>>>> c81f1419edd65d1c2f185299581d0ced02fb9328
}
