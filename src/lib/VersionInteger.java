package lib;

public class VersionInteger extends VersionObject
{
	private int value;
	
	public VersionInteger()
	{
		value = 0;
	}
	
	public int getAndInc()
	{
		value++;
		setVersion();
		return value;
	}
}
