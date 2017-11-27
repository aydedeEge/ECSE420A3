package ca.mcgill.ecse420.a3;

public class FineGrainTesting {
	
	public static void main(String[] args){
		
		//These are in order of increasing key values
		QNode strawberry = new QNode.QNodeBuilder("strawberry").build();
		QNode apple = new QNode.QNodeBuilder("apple").setNext(strawberry).build();
		QNode kiwi = new QNode.QNodeBuilder("kiwi").setNext(apple).build();
		QNode banana = new QNode.QNodeBuilder("banana").setNext(kiwi).build();
		
		System.out.println(contains(banana, "adsf"));
	}
	
	public static boolean contains(QNode head, String fruit){
		QNode curr = head, pred = null;
		int key = fruit.hashCode();
		
		if(head.next==null){
			
		}else{
			curr.nodeLock.lock();
		}
		
		try{
			while(curr.key<=key){
				if(fruit==curr.fruit){
					return true;
				}else if(pred!=null){
					pred.nodeLock.unlock();
				}

				pred = curr;

				curr = curr.next;
				curr.nodeLock.lock();
			}
			return false;
		}finally{	
			pred.nodeLock.unlock();
			curr.nodeLock.unlock();
		}
	}
}