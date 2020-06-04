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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name ="cdAccount", catalog = "meritbank")
public class CDAccount extends BankAccount {
		
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Min(value  = 1 , message = "term size error too small") 
	private int term;
	
			
	//@DecimalMin(value = "0.0", inclusive = false , message = "interest rate size error too small")
	//@Max(value = (long) 0.9999999999 , message = "interest rate size error too big")
	private double interestRate;
	
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "account_holder_id")
	private Integer cd_offering_id;
	
	
	private Integer account_holder_id;
	
	
	
	public CDAccount() {
		super();		 
		
	}
	
	
	public CDAccount(CDOffering offering , double openingBalance) {
		super(openingBalance);
		this.interestRate = offering.getInterestRate();
		this.term = offering.getTerm();
	}
	
	public CDAccount(double startBalance , double interestRate , long accountNumber , java.util.Date startDate , int termToBeAdded) {
		
		super(accountNumber , startBalance , startDate);
		this.interestRate = interestRate;
		this.term = termToBeAdded;
	}
	
	
	
	public Integer getAccount_holder_id() {
		return account_holder_id;
	}


	public Integer getCd_offering_id() {
		return cd_offering_id;
	}


	public void setCd_offering_id(Integer cd_offering_id) {
		this.cd_offering_id = cd_offering_id;
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


	public int getTerm() {
		return term;
	}	
	
     public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double futureValue() {
	    return super.futureValue(term);
			
     }
     
     boolean withdraw(double amount) {
    	 return false;
     }
     
     boolean deposit(double amount) {
    	 return false;
     }
     
     static CDAccount readFromString(String accountData) {
 		
    	 CDAccount toBeAdded = null;
 		try{
 			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
 			String[] toBeParsed = accountData.split(",");
 			long accountNumberToAdd = Integer.parseInt(toBeParsed[0]);
 			double curentBalanceToBeAdded = Double.parseDouble(toBeParsed[1]);
 			double interestRateToBeAdded = Double.parseDouble(toBeParsed[2]);
 			java.util.Date dateToBeAdded = dateFormatter.parse(toBeParsed[3]);
 			int termToBeAdded = Integer.parseInt(toBeParsed[4]);
 			
 			toBeAdded = new CDAccount(curentBalanceToBeAdded , interestRateToBeAdded , accountNumberToAdd , dateToBeAdded , termToBeAdded);
 		
 		
 		}catch(NumberFormatException exception) {
			throw exception;
			
		}catch(ParseException exception) { 
			
		}
 		
 		return toBeAdded;
 		
 	} 
    	 //throws ParseException
    	 //Should throw a java.lang.NumberFormatException if String cannot be correctly parsed

     
     /*
     @Override 
     public String writeToString() {
 		StringBuilder sb = new StringBuilder(getAccountNumber() + "," + getBalance() + "," + term + "," + getInterestRate() + "," + getOpenedOn());
 		
 		int numberOfTransactions = super.getTransactions().size();
		sb.append(numberOfTransactions + "/n");
		for(int i = 0 ; i < getTransactionList().size() ; i++){
			sb.append(getTransactionList().get(i) + "/n");
		}
 		
 		String toBeReturned = sb.toString();
 		return toBeReturned;
 	}
     */
     
}



