package lib;

public class VersionObject
{
	private int version;
	private int id;
	public VersionObject(int version){
		this.version = version;
		this.id = (int) (Math.random()*Integer.MAX_VALUE);
	}
	public VersionObject(int version, int uniqueId){
		this.version = version;
		this.id = uniqueId;
	}
		
	public int getVersion()
	{
		return version;
	}
	
	public void incrementVersion()
	{
		version++;
	}
	
	public void setVersion(int version){
		this.version = version;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof VersionObject))
			return false;
		else if(this.id == ((VersionObject)obj).id)
			return true;
		return false;
	}
	
	public boolean equalsVersion(Object obj){
		if(!(obj instanceof VersionObject))
			return false;
		else if(this.version == ((VersionObject)obj).getVersion())
			return true;
		return false;
	}
	
	@Override
	public VersionObject clone(){
		VersionObject newObj = new VersionObject(getVersion(), getId());
		return newObj;
	}
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
}
