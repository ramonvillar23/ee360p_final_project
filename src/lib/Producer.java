package lib;

import runtime.ProducerConsumerExplicit;

public class Producer implements Runnable {
	/**
	 * 
	 */
	private ProducerConsumerExplicit pc;

	/**
	 * @param main
	 */
	public Producer(ProducerConsumerExplicit pcSent) {
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