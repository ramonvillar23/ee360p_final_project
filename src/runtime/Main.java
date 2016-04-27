package runtime;

import java.util.ArrayList;
import java.util.function.Predicate;

import lib.IntThread;
import lib.Monitor;
import lib.Reader;
import lib.Timer;
import lib.VersionInteger;
import lib.Writer;

public class Main extends Monitor{
	public Main()
	{
		super();
	}
	
	public static void main(String[] args) {		
		//IntThread thread1 = new IntThread(newInt, mon, 1);
		//IntThread thread2 = new IntThread(newInt, mon, 2);
		ReaderWriterImplicit rw = new ReaderWriterImplicit();
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for(int i = 0; i < 1; i++){
			threads.add(new Thread(new Reader(rw)));
			threads.add(new Thread(new Writer(rw)));
		}
		
		for(Thread t : threads){
			t.start();
		}
		
		Timer time = new Timer();
		time.start();
		
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		time.stop();
		System.out.println("Completed in "+time.getTime()/1000000.0);
	}
}
