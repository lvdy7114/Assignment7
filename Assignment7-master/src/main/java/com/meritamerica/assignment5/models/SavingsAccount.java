package com.meritamerica.assignment5.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
public class SavingsAccount extends BankAccount {
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private double interestRate;
	
	public static final double savingsInterestRate = 0.01;
	
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "account_holder_id")
	//private AccountHolder accountHolder;
	private Integer account_holder_id;
	
	public SavingsAccount() {
		super();
		this.interestRate = savingsInterestRate;
	}
	
	public SavingsAccount(double startBalance){		
		super(startBalance);
		this.interestRate = savingsInterestRate;
	}
	
	public SavingsAccount(double startBalance , double interestRate , long accountNumber , java.util.Date startDate) {
		
		super(accountNumber , startBalance , startDate);
		this.interestRate = interestRate;
	}
	
	public static SavingsAccount readFromString(String accountData) {
		
		SavingsAccount toBeAdded = null;
		try{
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
			String[] toBeParsed = accountData.split(",");
			long accountNumberToBeAdd = Integer.parseInt(toBeParsed[0]);
			double curentBalanceToBeAdded = Double.parseDouble(toBeParsed[1]);
			double interestRateToBeAdded = Double.parseDouble(toBeParsed[2]);
			java.util.Date dateToBeAdded = dateFormatter.parse(toBeParsed[3]);
			
			
			toBeAdded = new SavingsAccount(curentBalanceToBeAdded , interestRateToBeAdded , accountNumberToBeAdd , dateToBeAdded);
		
			//NumberFormatException
							
		}catch(NumberFormatException exception) {
			throw exception;
			
		}catch(ParseException exception) { 
			
		}
		
		return toBeAdded;
		
	}
	
	
	
	
	public Integer getAccount_holder_id() {
		return account_holder_id;
	}

	public void setAccount_holder_id(Integer account_holder_id) {
		this.account_holder_id = account_holder_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public static double getSavingsinterestrate() {
		return savingsInterestRate;
	}
	
		
	
}

