package com.meritamerica.assignment5.models;

public class DepositTransaction extends Transaction {

	DepositTransaction(BankAccount targetAccount, double amount) {
		super(targetAccount , amount);
	}
	
	DepositTransaction(BankAccount targetAccount, double amount , java.util.Date dateToBeAdded) {
		super(targetAccount , amount , dateToBeAdded);
	}
	
}
