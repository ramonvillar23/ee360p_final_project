package lib;

public class VersionInteger extends VersionObject
{
	private int value;
	
	public VersionInteger()
	{
		super(0);
		value = 0;
	}
	
	
	public int getValue()
	{
		return this.value;
	}
	public VersionInteger(int value)
	{
		super(0);
		this.value = value;
	}
	
	public int getAndInc()
	{
		value++;
		incrementVersion();
		return value;
	}
	
	public int getAndDec()
	{
		value--;
		incrementVersion();
		return value;
	}
	
	public int setValue(int value)
	{
		this.value = value;
		incrementVersion();
		return this.value;
	}
	
}
