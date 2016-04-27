package runtime;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import lib.IntThread;
import lib.Monitor;
import lib.VersionInteger;

public class Main extends Monitor{
	public Main()
	{
		super();
	}
	ReaderWriterImplicit rw = new ReaderWriterImplicit();
	public static void main(String[] args) {		
		//IntThread thread1 = new IntThread(newInt, mon, 1);
		//IntThread thread2 = new IntThread(newInt, mon, 2);
		Main test = new Main();
		Thread t1 = new Thread(test.new Reader());
		Thread t2 = new Thread(test.new Writer());
		Thread t3 = new Thread(test.new Reader());
		Thread t4 = new Thread(test.new Writer());
		Thread t5 = new Thread(test.new Reader());
		Thread t6 = new Thread(test.new Writer());
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
	}
	
	public class Reader implements Runnable {
		public void run() {
			for(int i = 0; i < 50; i++)
			{
				rw.startRead();
				try {
					TimeUnit.SECONDS.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rw.endRead();
				try {
					TimeUnit.SECONDS.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public class Writer implements Runnable {

		public void run() {
			for(int i = 0; i < 50; i++)
			{
				rw.startWrite();
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rw.endWrite();
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
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
}
