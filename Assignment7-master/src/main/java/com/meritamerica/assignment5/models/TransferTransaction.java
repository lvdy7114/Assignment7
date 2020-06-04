package com.meritamerica.assignment5.models;

public class TransferTransaction extends Transaction {
	
	TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount) {
		super(sourceAccount , targetAccount , amount);		
	}

	
	TransferTransaction(BankAccount targetAccount, double amount , java.util.Date dateToBeAdded) {
		super(targetAccount , amount , dateToBeAdded);
	}

}
