package lib;

import runtime.ProducerConsumerExplicit;

public class Consumer implements Runnable {
	/**
	 * 
	 */
	private ProducerConsumerExplicit pc;

	/**
	 * @param main
	 */
	public Consumer(ProducerConsumerExplicit pcSent) {
		this.pc = pcSent;
	}

	public void run(){
		for(int i = 0; i < 50; i++)
		{
			try
			{
				pc.take();
			}
			catch(InterruptedException e)
			{
				
			}
			
		}
	}
	
}