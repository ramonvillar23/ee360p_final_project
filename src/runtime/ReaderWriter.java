package runtime;

import lib.Monitor;
import lib.VersionInteger;
import lib.VersionObject;

public class ReaderWriter extends Monitor{

	private VersionInteger readers  = new VersionInteger(0);
	private VersionInteger writers  = new VersionInteger(0);
	void startRead()
	{
		mutex.lock();
		System.out.println("About to request read");
		while(writers.getValue() !=  0) 
		{
			try {
				mutex.unlock();
				customWait(writers);
				mutex.lock();
			} catch (CloneNotSupportedException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Read Request succesful");
		readers.getAndInc();
		mutex.unlock();
	}
	
	void endRead()
	{
		mutex.lock();
		System.out.println("Exiting read");
		readers.getAndDec();
		mutex.unlock();
	}
	
	void startWrite()
	{
		mutex.lock();
		System.out.println("About to request write");
		while(writers.getValue() != 0 || readers.getValue() != 0)
		{
			try {
				mutex.unlock();
				customWait(readers);
				mutex.lock();
			} catch (CloneNotSupportedException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Write Request succesful");
		writers.getAndInc();
		mutex.unlock();
	}
	
	void endWrite()
	{
		mutex.lock();
		System.out.println("Exiting write");
		writers.setValue(0);
		mutex.unlock();
	}
	
}
