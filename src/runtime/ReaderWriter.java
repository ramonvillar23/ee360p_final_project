package runtime;

import lib.Monitor;
import lib.VersionInteger;
import lib.VersionObject;

public class ReaderWriter extends Monitor{

	private VersionInteger readers  = new VersionInteger(0);
	private VersionInteger writers  = new VersionInteger(0);
	synchronized void startRead()
	{
		while(writers.getValue() !=  0) 
		{
			try {
				customWait(writers);
			} catch (CloneNotSupportedException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		readers.getAndInc();
	}
	
	synchronized void endRead()
	{
		readers.getAndDec();
	}
	
	synchronized void startWrite()
	{
		while(writers.getValue() != 0 || readers.getValue() != 0)
		{
			try {
				customWait(readers);
			} catch (CloneNotSupportedException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writers.getAndInc();
		
	}
	
	synchronized void endWrite()
	{
		writers.setValue(0);
	}
	
}
