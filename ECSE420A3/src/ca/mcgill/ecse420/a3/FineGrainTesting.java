package ca.mcgill.ecse420.a3;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainTesting {
	private static Random rand = new Random();
	private static ReentrantLock countLock = new ReentrantLock();
	
	public static void main(String[] args){
		
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new testContains());
		executor.execute(new testContains());

		executor.shutdown();
		
	}
	
	public static class testContains implements Runnable{
		//These are in order of increasing key values for the sake of brevity
		private static String[] fruitList = new String[]{"banana", "apple", "kiwi", "strawberry", "pear", "dragonfruit", "cherry"};;
		private volatile int trueCount = 0;
		private volatile int falseCount = 0;
		private static QNode strawberry = new QNode.QNodeBuilder("strawberry").build();
		private static QNode apple = new QNode.QNodeBuilder("apple").setNext(strawberry).build();
		private static QNode kiwi = new QNode.QNodeBuilder("kiwi").setNext(apple).build();
		private static QNode banana = new QNode.QNodeBuilder("banana").setNext(kiwi).build();
		
		QNode head = banana;
		@Override
		public void run() {
			
			runContains();
			runAdd();
			runContains();
			runRemove();
			runContains();
			runAdd();
			runContains();
			runRemove();
			runContains();
			runAdd();
			runContains();
			runRemove();
			runContains();
			runAdd();
			runContains();
			runRemove();
			runContains();
			runAdd();
			runContains();
			runRemove();
			runContains();
			runAdd();
			runContains();
			runRemove();
			System.out.println("True Count: " + trueCount);
			System.out.println("False Count: " + falseCount);
		}
		
		public synchronized QNode createImage(QNode toCopy){
			return new QNode(toCopy);
		}
		
		public void runContains(){
			QNode image = this.createImage(this.head);
			String currentFruit = this.fruitList[rand.nextInt(fruitList.length)];
			if(image.contains(currentFruit) == this.head.contains(currentFruit)){
				countLock.lock();
				this.trueCount++;
				countLock.unlock();
			}else{
				System.out.println(image.toString());
				System.out.println(this.head.toString());
				countLock.lock();
				this.falseCount++;
				countLock.unlock();
			}
		}
		
		public boolean runAdd(){
			QNode image = new QNode(this.head);
			String currentFruit = this.fruitList[rand.nextInt(fruitList.length)];
			return this.head.add(currentFruit);
		}
		
		public boolean runRemove(){
			QNode image = new QNode(this.head);
			String currentFruit = this.fruitList[rand.nextInt(fruitList.length)];
			return this.head.remove(currentFruit);
		}
		
		
	}
}
