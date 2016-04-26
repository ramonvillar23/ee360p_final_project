package lib;

public abstract class VersionObject
{
	private int version;
	
	public int getVersion()
	{
		return version;
	}
	
	public void setVersion()
	{
		version++;
	}
	
	public boolean equals(Object obj)
	{
		if(!(obj instanceof VersionObject))
			return false;
		else if(version == ((VersionObject)obj).getVersion())
			return true;
		return false;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
