package runtime;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderWriterExplicit
{
	
	private ReentrantLock monitorLock = new ReentrantLock();
	private Condition rw = monitorLock.newCondition();
	private int numReaders = 0;
	private int numWriters = 0;
	
	public void startRead() throws InterruptedException
	{
		monitorLock.lock();
		while(numWriters > 0)
		{
			System.out.println(Thread.currentThread().getId()+":Reader is waiting for "+numWriters+" writers to exit.");
			rw.await();
		}
		
		numReaders++;
		System.out.println(Thread.currentThread().getId()+":Read Request succesful");
		monitorLock.unlock();
	}
	
	public void endRead()
	{
		monitorLock.lock();
		System.out.println(Thread.currentThread().getId()+":Exiting read");
		numReaders--;
		rw.signalAll();
		monitorLock.unlock();
	}
	
	public void startWrite() throws InterruptedException
	{
		monitorLock.lock();
		
		while(numWriters > 0 || numReaders > 0)
		{
			System.out.println(Thread.currentThread().getId()+":Writer is waiting for "+numReaders+" reader and "+numWriters+" writers");
			rw.await();
		}
		numWriters++;
		System.out.println(Thread.currentThread().getId()+":Write Request succesful");
		monitorLock.unlock();
	}
	
	public void endWrite()
	{
		monitorLock.lock();
		numWriters--;
		System.out.println(Thread.currentThread().getId()+":Exiting write");
		rw.signalAll();
		monitorLock.unlock();
	}
}
