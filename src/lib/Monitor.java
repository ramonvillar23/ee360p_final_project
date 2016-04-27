package lib;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;


public class Monitor {


	ArrayList<VersionObject> thingsAlreadyWaitingOn = new ArrayList<>(); 
	ArrayList<VersionObject> localCopyOfObjects = new ArrayList<>();
	HashMap<Integer, ArrayList<Condition>> conditionVariables = new HashMap<>(); //will group threads into different conditions
	boolean readyToAwake = false;
	public Monitor(){
		Thread notifier = new Thread(new ThreadNotifier());
		notifier.start();
	}
	
	final private Lock aLock = new ReentrantLock();
 public void customWait(VersionObject toWaitOn) throws CloneNotSupportedException, InterruptedException
	{
		aLock.lock();
		Condition newCond = null;
		//Only checks the ID, not the version
		if(!thingsAlreadyWaitingOn.contains(toWaitOn)) //not here so need to add it
		{
			thingsAlreadyWaitingOn.add(toWaitOn);
			localCopyOfObjects.add(toWaitOn.clone()); //local copy of object to know if it changes in future
			ArrayList<Condition> newConditionList = new ArrayList<Condition>();
			newCond = aLock.newCondition();
			newConditionList.add(newCond);
			conditionVariables.put(toWaitOn.getId(), newConditionList);	//each parameter will point to an arraylist of conditions (threads waiting for it)
		}
		else{
			ArrayList<Condition> listToAddTo = conditionVariables.get(thingsAlreadyWaitingOn.get(thingsAlreadyWaitingOn.indexOf(toWaitOn)).getId());
			newCond = aLock.newCondition();
			listToAddTo.add(newCond);
		}
		
		if(!readyToAwake){
			readyToAwake = true;
		}
		newCond.await();
	}
	
	class ThreadNotifier implements Runnable 
	{
		public void run()
		{
			
			while(true){
				if(readyToAwake){
					for(Integer id : conditionVariables.keySet())
					{
						VersionObject dummy = new VersionObject(-1, id);
						VersionObject localCopy = localCopyOfObjects.get(localCopyOfObjects.indexOf(dummy));
						VersionObject refCopy = thingsAlreadyWaitingOn.get(thingsAlreadyWaitingOn.indexOf(dummy));
						//Checks the version and the id
						if(!localCopy.equals(refCopy)) //if local copy is different to actual object
						{
							//if object changed, (not equal to local copy), notify all waiting threads on that variable
							ArrayList<Condition> conditionList = conditionVariables.get(id); //get conditions in hash table
							for(Condition c : conditionList){
								c.notify();
							}
							//update local copy
							int index = localCopyOfObjects.indexOf(id);
							localCopyOfObjects.set(index, refCopy.clone());
						}
					}
				}
			}
		}
	}
}
