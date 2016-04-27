package runtime;

import java.util.function.Predicate;

import lib.IntThread;
import lib.Monitor;
import lib.VersionInteger;

public class Main extends Monitor{

	
	public static void main(String[] args) {
		VersionInteger newInt = new VersionInteger();
		Monitor mon = new Monitor();
		
		IntThread thread1 = new IntThread(newInt, mon, 1);
		IntThread thread2 = new IntThread(newInt, mon, 2);
		Thread t1 = new Thread(thread1);
		Thread t2 = new Thread(thread2);
		t1.start();
		t2.start();
		// TODO Auto-generated method stub
	}
	public void init(){
		
	}
	
	
	

}
