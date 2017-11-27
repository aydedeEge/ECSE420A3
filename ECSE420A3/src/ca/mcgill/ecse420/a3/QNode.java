package ca.mcgill.ecse420.a3;

import java.util.concurrent.locks.ReentrantLock;

public class QNode {
	public String fruit;
	public int key;
	public ReentrantLock nodeLock;
	public volatile QNode next;
	
	private QNode(QNodeBuilder qbb){
		this.fruit = qbb.fruit;
		this.key = qbb.key;
		this.nodeLock = qbb.nodeLock;
		this.next = qbb.next;
	}
	
	public static class QNodeBuilder {
		private String fruit;
		private int key;
		private ReentrantLock nodeLock;
		private volatile QNode next;
		
		public QNodeBuilder(String fruit){
			this.fruit = fruit;
			this.key = this.fruit.hashCode();
			this.nodeLock = new ReentrantLock();
		}
		
		public QNodeBuilder setNext(QNode next){
			this.next = next;
			return this;
		}
		
		public QNode build(){
			return new QNode(this);
		}
	}
	
	
}