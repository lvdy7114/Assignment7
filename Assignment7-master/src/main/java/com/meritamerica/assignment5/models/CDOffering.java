package com.meritamerica.assignment5.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Entity
@Table(name ="cdOffering", catalog = "meritbank")
public class CDOffering {
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	private Integer cd_offering_id;
	
	@Min(value  = 1 , message = "term size error too small")
	private int term;
	
	//@DecimalMin(value = "0.0", inclusive = false , message = "interest rate size error too small")
	//@Max(value = (long) 0.9999999999 , message = "interest rate size error too big")
	private double interestRate;
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "cd_offering_id")	
	private List<CDAccount> cdAccounts;
	
	

	public CDOffering() {
		this.term = term;
		this.interestRate= interestRate;
		cdAccounts = new ArrayList<CDAccount>();
}
	
	CDOffering(int term, double interestRate) {
		//constructs the CDOffering
		this.term = term;
		this.interestRate = interestRate;	
		//added this
		cdAccounts = new ArrayList<CDAccount>();
	}
	
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Integer getCd_offering_id() {
		return cd_offering_id;
	}

	public void setCd_offering_id(Integer cd_offering_id) {
		this.cd_offering_id = cd_offering_id;
	}
	
	
	

	public List<CDAccount> getCdAccounts() {
		return cdAccounts;
	}

	public void setCdAccounts(List<CDAccount> cdAccounts) {
		this.cdAccounts = cdAccounts;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	
	/*

	static CDOffering readFromString(String cdOfferingDataString) {
		
		CDOffering toBeAdded = null;
		try{
			String[] toBeParsed = cdOfferingDataString.split(",");
			int termToAdd = Integer.parseInt(toBeParsed[0]);
			double interestRateToBeAdded = Double.parseDouble(toBeParsed[1]);
			
			toBeAdded = new CDOffering(termToAdd , interestRateToBeAdded);
			
			
			
			
		}catch(NumberFormatException exception) {
			throw exception;		
		}
		
		return toBeAdded;
		
	}
	
	
	public String writeToString() {
		StringBuilder cdoStringBuilder = new StringBuilder(term + "," + interestRate);
		String toString = cdoStringBuilder.toString();
		return toString;
		
	}

	*/
	
	
}
