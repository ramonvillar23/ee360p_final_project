package lib;

public class VersionInteger extends VersionObject
{
	private int value;
	private String name = null;
	public VersionInteger()
	{
		super(0);
		value = 0;
	}
	public VersionInteger(int value)
	{
		super(0);
		this.value = value;
	}
	public VersionInteger(int value, String name)
	{
		super(0);
		this.value = value;
		this.name = name;
	}
	@Override
	public String toString()
	{
		String toPrint = "";
		if(this.name != null)
			toPrint+= this.name + " ";
		toPrint+= this.value;
		return toPrint;
	}
	public synchronized int getValue()
	{
		return this.value;
	}

	
	public synchronized int getAndInc()
	{
		value++;
		incrementVersion();
		return value;
	}
	
	public synchronized int getAndDec()
	{
		value--;
		incrementVersion();
		return value;
	}
	
	public synchronized int setValue(int value)
	{
		this.value = value;
		incrementVersion();
		return this.value;
	}
	
}
