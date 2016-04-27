package lib;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;


public class Monitor {

	public Monitor(){
		Thread notifier = new Thread(new ThreadNotifier());
		notifier.start();
	}
	final ArrayList<VersionObject> thingsAlreadyWaitingOn = new ArrayList<>(); 
	final ArrayList<VersionObject> localCopyOfObjects = new ArrayList<>();
	final HashMap<Integer, ArrayList<Condition>> conditionVariables = new HashMap<>(); //will group threads into different conditions
	boolean readyToAwake = false;
	final private Lock aLock = new ReentrantLock();
	final protected Lock mutex = new ReentrantLock();

	public void customWait(ArrayList<VersionObject> listOfToWaitOn) throws CloneNotSupportedException, InterruptedException
	{
	 	mutex.lock();
		aLock.lock();
		Condition newCond = null;
		//Only checks the ID, not the version
		for(VersionObject toWaitOn: listOfToWaitOn)
		{
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
				ArrayList<Condition> listToAddTo = conditionVariables.get(toWaitOn.getId());
				newCond = aLock.newCondition();
				listToAddTo.add(newCond);
			}
			
			if(!readyToAwake){
				readyToAwake = true;
			}
		}
		mutex.unlock();
		newCond.await();
		aLock.unlock();
	}
	
	class ThreadNotifier implements Runnable 
	{
		public void run()
		{
			while(true){
				mutex.lock();
				aLock.lock();
				if(readyToAwake){
					for(Integer id : conditionVariables.keySet())
					{
						VersionObject dummy = new VersionObject(-1, id);
						VersionObject localCopy = localCopyOfObjects.get(localCopyOfObjects.indexOf(dummy));
						VersionObject refCopy = thingsAlreadyWaitingOn.get(thingsAlreadyWaitingOn.indexOf(dummy));
						//Checks the version and the id
						if(!localCopy.equalsVersion(refCopy)) //if local copy is different to actual object
						{
							//if object changed, (not equal to local copy), notify all waiting threads on that variable
							ArrayList<Condition> conditionList = conditionVariables.get(id); //get conditions in hash table
							for(Condition c : conditionList){
								c.signal();
							}
							//signaled all conditions, now clear arraylist so they can add themselves if necessary
							conditionVariables.put(id, new ArrayList<Condition>());
							//update local copy
							int index = localCopyOfObjects.indexOf(new VersionObject(-1, id));
							localCopyOfObjects.set(index, refCopy.clone());
						}
					}
				}
				aLock.unlock();
				mutex.unlock();
			}
		}
	}
}
