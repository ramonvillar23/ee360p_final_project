package lib;

public class IntThread implements Runnable
{
	private VersionInteger verInt;
	private Monitor mon;
	private int id;
	
	public IntThread(VersionInteger vi, Monitor m, int i)
	{
		verInt = vi;
		mon = m;
		id = i;
	}
	
	public void run()
	{
		for(int i = 0; i < 100; i++)
		{
			System.out.println("Thread " + id + ": " + verInt.getAndInc());
			System.out.flush();
			
			try {
				mon.customWait(verInt);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
