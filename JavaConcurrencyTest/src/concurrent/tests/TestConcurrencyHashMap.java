package concurrent.tests;

import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.tamu.aser.exploration.JUnit4MCRRunner;

@RunWith(JUnit4MCRRunner.class)
public class TestConcurrencyHashMap {
      private final ConcurrentHashMap<Integer,String> conHashMap = new ConcurrentHashMap<Integer,String>();
	  public static void main(String[] args) {
		  ExecutorService  service = Executors.newFixedThreadPool(3);
		  TestConcurrencyHashMap ob = new TestConcurrencyHashMap();
		  service.execute(ob.new WriteThreasOne());
		  service.execute(ob.new WriteThreasTwo());
		  service.execute(ob.new ReadThread());
		  service.shutdownNow();
	  }
	  class WriteThreasOne implements Runnable {
		@Override
		public void run() {
			for(int i= 1; i<=10; i++) {
				conHashMap.putIfAbsent(i, "A"+ i);
			}			
		}
	  }
	  class WriteThreasTwo implements Runnable {
		@Override
		public void run() {
			for(int i= 1; i<=5; i++) {
				conHashMap.put(i, "A"+ i);
			}
		}
	  }  
	  class ReadThread implements Runnable {
		@Override
		public void run() {
		   Iterator<Integer> ite = conHashMap.keySet().iterator();
	  	   while(ite.hasNext()){
	  		   Integer key = ite.next();
	  		   System.out.println(key+" : " + conHashMap.get(key));
		  }
		}
	  }
		@Test
		public void test() throws InterruptedException {
			try {
				TestConcurrencyHashMap.main(null);
			} catch (Exception e) {
				System.out.println("here");
				fail();
			}
		}
} 
