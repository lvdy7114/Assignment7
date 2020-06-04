package com.meritamerica.assignment5.models;

public class FraudQueue {
	
	private List<Transaction> fq = null;
	public static final double excessiveAmount = 1000;
	
	public FraudQueue() {
		
	}
	
	
	public void addTransaction(Transaction transaction) {
		if(fq == null) {
			fq = new List<Transaction>();
		}
		fq.enqueue(transaction);
	}
	
	
	public Transaction getTransaction() {
		return fq.dequeue();
		
	}
	
	public static double getExcessiveAmount() {
		return excessiveAmount;
	}


}
