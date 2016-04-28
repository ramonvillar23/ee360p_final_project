package lib;

import runtime.ProducerConsumerImplicit;

public class Consumer implements Runnable {
	/**
	 * 
	 */
	private ProducerConsumerImplicit pc;

	/**
	 * @param main
	 */
	public Consumer(ProducerConsumerImplicit pcSent) {
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