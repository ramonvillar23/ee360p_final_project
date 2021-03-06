package runtime;

import java.util.ArrayList;

import lib.Monitor;
import lib.VersionInteger;
import lib.VersionObject;

public class ReaderWriterImplicit extends Monitor{

	private VersionInteger readers  = new VersionInteger(0, "readers");
	private VersionInteger writers  = new VersionInteger(0, "writers");
	public void startRead()
	{
		mutex.lock();
		//System.out.println("About to request read");
		while(writers.getValue() !=  0) 
		{
			try {
				ArrayList<VersionObject> toWait = new ArrayList<>();
				toWait.add(writers);
				System.out.println(Thread.currentThread().getId()+":Reader is waiting for "+writers.getValue()+" writers to exit.");
				customWait(toWait);
				//customWait releases mutex... need to reacquire
				mutex.lock();
			} catch (CloneNotSupportedException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getId()+":Read Request succesful");
		readers.getAndInc();
		mutex.unlock();
	}
	
	public void endRead()
	{
		mutex.lock();
		System.out.println(Thread.currentThread().getId()+":Exiting read");
		readers.getAndDec();
		mutex.unlock();
	}
	
	public void startWrite()
	{
		mutex.lock();
		//System.out.println("About to request write");
		while(writers.getValue() != 0 || readers.getValue() != 0)
		{
			try {
				ArrayList<VersionObject> toWait = new ArrayList<>();
				toWait.add(readers);
				toWait.add(writers);
				System.out.println(Thread.currentThread().getId()+":Writer is waiting for "+readers.getValue()+" reader and "+writers.getValue()+" writers");
				customWait(toWait);
				//customWait releases mutex... need to reacquire
				mutex.lock();
			} catch (CloneNotSupportedException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getId()+":Write Request succesful");
		writers.getAndInc();
		mutex.unlock();
	}
	
	public void endWrite()
	{
		mutex.lock();
		System.out.println(Thread.currentThread().getId()+":Exiting write");
		writers.setValue(0);
		mutex.unlock();
	}
	
}
