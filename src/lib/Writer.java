package lib;

import java.util.concurrent.TimeUnit;

import runtime.ReaderWriterImplicit;

public class Writer implements Runnable {

	/**
	 * 
	 */
	private ReaderWriterImplicit rw;

	/**
	 * @param main
	 */
	public Writer(ReaderWriterImplicit rwSent) {
		this.rw = rwSent;
	}

	public void run() {
		for(int i = 0; i < 50; i++)
		{
			rw.startWrite();
			rw.endWrite();
			
		}
	}
	
}