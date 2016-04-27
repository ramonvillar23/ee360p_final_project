package runtime;

import lib.Monitor;
import lib.VersionInteger;

public class ProducerConsumerImplicit extends Monitor{
   final int size = 10;

   final Object[] buffer = new Object[size];
   int inBuf=0, outBuf=0;
   VersionInteger count = new VersionInteger(0);

   public void put(Object x) throws InterruptedException {
     mutex.lock();
     try {
       while (count.getValue() == buffer.length){
    	   System.out.println(Thread.currentThread().getId()+":Puter is waiting");
    	   customWait(count);
    	   mutex.lock();
       }
       
       System.out.println(Thread.currentThread().getId()+":Puter put value");
       buffer[inBuf] = x;
       inBuf = (inBuf + 1) % size;
       count.getAndInc();
       
     } catch (CloneNotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		System.out.println(Thread.currentThread().getId()+":Puter exiting");
       mutex.unlock();
     }
   }

   public Object take() throws InterruptedException {
     mutex.lock();
     Object x = null;
     try {
       while (count.getValue() == 0){
    	   System.out.println(Thread.currentThread().getId()+":Taker is waiting");
    	   customWait(count);
    	   mutex.lock();
       }
      
       System.out.println(Thread.currentThread().getId()+":Taker took element");
       x = buffer[outBuf];
       outBuf = (outBuf + 1) % size;
       count.getAndDec();
       
       
     } catch (CloneNotSupportedException e) {
		// TODO Auto-generated catch block
    	 
		e.printStackTrace();
	} finally {
		System.out.println(Thread.currentThread().getId()+":Taker is exiting");
       mutex.unlock();
       
     }
     return x;
   }
 }
 
