package runtime;

import lib.Monitor;

public class ReaderWriter extends Monitor{

	private int readers  = 0;
	private int writers  = 0;
	synchronized void startRead()
	{
		while(writers != 0) this.customWait();
		readers++;
	}
	
	synchronized void endRead()
	{
		readers--;
	}
	
	synchronized void startWrite()
	{
		while(writers != 0 || readers != 0) this.customWait();
		writers++;
	}
	
	synchronized void endWrite()
	{
		writers = 0;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
