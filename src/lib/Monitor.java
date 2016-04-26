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
	HashMap<VersionObject, Condition> conditionVariables = new HashMap<>(); //will group threads into different conditions

	void customWait(VersionObject toWaitOn) throws CloneNotSupportedException, InterruptedException
	{
		
		
		if(!thingsAlreadyWaitingOn.contains(toWaitOn)) //not here so need to add it
		{
			thingsAlreadyWaitingOn.add(toWaitOn);
			localCopyOfObjects.add((VersionObject)toWaitOn.clone()); //local copy of object to know if it changes in future
			Lock aLock = new ReentrantLock();
			Condition uniqueCondition = aLock.newCondition();
			conditionVariables.put(toWaitOn, uniqueCondition);	//each parameter will point to an arraylist of conditions (threads waiting for it)
		}
		
		conditionVariables.get(toWaitOn).await();
	}
	
	class ThreadNotifier implements Runnable 
	{
		public void run()
		{
			for(VersionObject param : conditionVariables.keySet())
			{
				if(!param.equals(thingsAlreadyWaitingOn.get(localCopyOfObjects.indexOf(param)))) //if local copy is different to actual object
				{
					//if object changed, (not equal to local copy), notify all waiting threads on that variable
					conditionVariables.get(param).notifyAll(); //get conditions in hash table
					//update local copy
					int index = localCopyOfObjects.indexOf(param);
					try {
						localCopyOfObjects.set(index, (VersionObject) thingsAlreadyWaitingOn.get(index).clone());
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
