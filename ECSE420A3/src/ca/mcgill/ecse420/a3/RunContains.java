package ca.mcgill.ecse420.a3;

public class RunContains implements Runnable{
	private double startTime, endTime;
	private boolean result;
	private QNode head;
	
	public RunContains(QNode head){
		this.head = head;
	}
	
	public void run(){
		
		startTime = System.currentTimeMillis();
		result = this.head.contains("apple");
		endTime = System.currentTimeMillis();
		System.out.printf("CONTAINS Start time: %.0f End time: %.0f Result: %s\n", startTime, endTime, result);
	}
}
