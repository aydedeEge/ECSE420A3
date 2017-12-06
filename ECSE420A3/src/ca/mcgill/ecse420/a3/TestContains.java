package ca.mcgill.ecse420.a3;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class TestContains {
	private static QNode strawberry = new QNode.QNodeBuilder("strawberry").build();
	private static QNode apple = new QNode.QNodeBuilder("apple").setNext(strawberry).build();
	private static QNode kiwi = new QNode.QNodeBuilder("kiwi").setNext(apple).build();
	private static QNode banana = new QNode.QNodeBuilder("banana").setNext(kiwi).build();
	private static QNode head = banana;
	
	private TestContains testcontains = new TestContains();

	public static void main(String[] args){
		ExecutorService executor = Executors.newCachedThreadPool();
		
		executor.execute(new RunContains(head));
		executor.execute(new RunContains(head));
		executor.execute(new RunContains(head));
		executor.execute(new RunRemove(head));
		executor.execute(new RunContains(head));
		executor.execute(new RunContains(head));
		executor.execute(new RunContains(head));
		
		executor.shutdown();
	}
	
//	public static class RunContains implements Runnable{
//		private double startTime, endTime;
//		private boolean result;
//		public void run(){
//			
//			startTime = System.nanoTime();
//			result = banana.contains("apple");
//			endTime = System.nanoTime();
//			System.out.printf("CONTAINS Start time: %.0f End time: %.0f Result: %s\n", startTime, endTime, result);
//		}
//	}
	
//	public static class RunRemove implements Runnable{
//		private double startTime, endTime;
//		private boolean result;
//		public void run(){
//			startTime = System.nanoTime();
//			result = banana.remove("apple");
//			endTime = System.nanoTime();
//			System.out.printf("REMOVE Start time: %.0f End time: %.0f Result: %s\n", startTime, endTime, result);
//		}
//	}
}
