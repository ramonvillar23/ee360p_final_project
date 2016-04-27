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
	ReaderWriter rw = new ReaderWriter();
	public static void main(String[] args) {
		VersionInteger newInt = new VersionInteger();
		
		//IntThread thread1 = new IntThread(newInt, mon, 1);
		//IntThread thread2 = new IntThread(newInt, mon, 2);
		Main test = new Main();
		Thread t1 = new Thread(test.new Reader());
		Thread t2 = new Thread(test.new Writer());
		t1.start();
		t2.start();
	}
	
	public class Reader implements Runnable {
		public void run() {
			while(true)
			{
				rw.startRead();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rw.endRead();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public class Writer implements Runnable {

		public void run() {
			while(true)
			{
				rw.startWrite();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rw.endWrite();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
