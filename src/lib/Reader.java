package lib;

import runtime.ReaderWriterImplicit;

public class Reader implements Runnable {
	/**
	 * 
	 */
	private ReaderWriterImplicit rw;

	/**
	 * @param main
	 */
	public Reader(ReaderWriterImplicit rwSent) {
		this.rw = rwSent;
	}

	public void run() {
		for(int i = 0; i < 50; i++)
		{
			rw.startRead();
			rw.endRead();
			
		}
	}
	
}