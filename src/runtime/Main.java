package runtime;

import java.util.function.Predicate;

import lib.Monitor;
import lib.Testable;

public class Main extends Monitor{

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	public void init(){
		
	}
	
	
	
	@Override
	public void doWork(){

		Predicate<Object> i  = (s)-> s.length() > 5;
		waitUntil();
	}

}
