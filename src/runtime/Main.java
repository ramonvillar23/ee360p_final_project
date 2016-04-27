package runtime;

import java.util.ArrayList;
import lib.Reader;
import lib.Timer;
import lib.Writer;

public class Main{
	public Main()
	{
		super();
	}
	
	public static void main(String[] args) {		
		//IntThread thread1 = new IntThread(newInt, mon, 1);
		//IntThread thread2 = new IntThread(newInt, mon, 2);
		ArrayList<Long> results = new ArrayList<Long>();
		for(int j = 0; j < 10; j++){
			ProducerConsumerImplicit pc = new ProducerConsumerImplicit();
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
			results.add(time.getTime());
			//System.out.println("Completed in "+time.getTime()/1000000.0);
		}
		long sum = 0;
		for(Long d : results){
			sum += d;
		}
		System.out.println("Trial Average: "+(sum/results.size())/1000000.0);
		
		
	}
}
