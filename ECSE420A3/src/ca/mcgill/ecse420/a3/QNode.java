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
	
	public QNode(QNode existingNode){
		this.fruit = existingNode.fruit;
		this.key = existingNode.key;
		this.nodeLock = existingNode.nodeLock;
		this.next = existingNode.next;
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
	
	public boolean contains(String fruit){
		QNode curr = this, pred = this;
		int key = fruit.hashCode();
		
		curr.nodeLock.lock();
		pred.nodeLock.lock();
		
		try{
			while(curr.key<=key){
				if(fruit==curr.fruit){
					return true;
				}

				pred.nodeLock.unlock();
				pred = curr;

				curr = curr.next;
				try{
					curr.nodeLock.lock();
				}catch(NullPointerException ex){
					return false;
				}
				
			}
			return false;
		}finally{
			pred.nodeLock.unlock();
			if(curr!=null) curr.nodeLock.unlock();
		}
	}
	
	public boolean remove(String fruit){
		if(!this.contains(fruit)){
			return false;
		}
		
		QNode curr = this, pred = this;
		int key = fruit.hashCode();
		
		curr.nodeLock.lock();
		pred.nodeLock.lock();
		
		try{
			while(curr.key<=key || curr!=null){
				if(fruit.equals(curr.fruit)){
					pred.next = curr.next;
					curr.next = null;
					return true;
				}
				pred.nodeLock.unlock();
				pred = curr;

				curr = curr.next;
				try{
					curr.nodeLock.lock();
				}catch(NullPointerException ex){
					return false;
				}
			}
			return false;
		}finally{
			pred.nodeLock.unlock();
			if(curr!=null) curr.nodeLock.unlock();
		}
		
	}
	
	public boolean add(String fruit){
		//Can't add node with same fruit value.
		if(this.contains(fruit)){
			return false;
		}
		
		QNode curr = this, pred = this, newNode;
		int key = fruit.hashCode();
		
		curr.nodeLock.lock();
		pred.nodeLock.lock();
		
		try{
			while(pred.key<=key){
				if(pred.key<key && curr.key>key){
					newNode = new QNode.QNodeBuilder(fruit).setNext(curr).build();
					pred.next = newNode;
					return true;
				}
				pred.nodeLock.unlock();
				pred = curr;

				curr = curr.next;
				
				try{
					curr.nodeLock.lock();
				//Reached end of list
				}catch(NullPointerException ex){
					newNode = new QNode.QNodeBuilder(fruit).build();
					pred.next = newNode;
					return true;
				}
			}
			return false;
		}finally{
			pred.nodeLock.unlock();
			if(curr!=null) curr.nodeLock.unlock();
		}
	}
	
	public String toString(){
		String toString = "";
		QNode head = this;
		while(head.next!=null){
			toString = toString + head.fruit;
			head = head.next;
		}
		return toString;
	}
}
