package ca.mcgill.ecse420.a3;

import java.util.Date;

public class RunRemove implements Runnable{
	private long startTime, endTime;
	private Date date = new Date();
	private boolean result;
	private QNode head;
	
	public RunRemove(QNode head){
		this.head = head;
	}
	
	public void run(){
		startTime = date.getTime();
		result = this.head.remove("apple");
		endTime = date.getTime();
		System.out.printf("REMOVE   Start time: %d End time: %d Result: %s\n", startTime, endTime, result);
	}
}
