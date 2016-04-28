package lib;

import runtime.ProducerConsumerImplicit;

public class Producer implements Runnable {
	/**
	 * 
	 */
	private ProducerConsumerImplicit pc;

	/**
	 * @param main
	 */
	public Producer(ProducerConsumerImplicit pcSent) {
		this.pc = pcSent;
	}

	public void run(){
		for(int i = 0; i < 50; i++)
		{
			try
			{
				pc.put(new Object());
			}
			catch(InterruptedException e)
			{
				
			}
			
		}
	}
	
}