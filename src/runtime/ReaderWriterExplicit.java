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
		numReaders++;
		while(numWriters > 0)
		{
			rw.await();
		}
		monitorLock.unlock();
	}
	
	public void endRead()
	{
		monitorLock.lock();
		numReaders--;
		rw.signalAll();
		monitorLock.unlock();
	}
	
	public void startWrite() throws InterruptedException
	{
		monitorLock.lock();
		numWriters++;
		while(numWriters > 1 || numReaders > 0)
		{
			rw.await();
		}
		monitorLock.unlock();
	}
	
	public void endWrite()
	{
		monitorLock.unlock();
		numWriters--;
		rw.signalAll();
		monitorLock.unlock();
	}
}
