package com.meritamerica.assignment5.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meritamerica.assignment5.controller.AccountHolderController;
import com.meritamerica.assignment5.repositories.CDAccountRepository;
import com.meritamerica.assignment5.repositories.CheckingAccountRepository;
import com.meritamerica.assignment5.repositories.SavingsAccountRepository;



@Entity
@Table(name ="accountholders", catalog = "meritbank")
public class AccountHolder {
	
	//public static int nextid = 0;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank(message = "firtName is missing")
	@Column(name="firstName")
	private String firstName;
		
	private String middleName;
	
	@NotBlank(message = "lastName is missing")
	private String lastName;
	
	@NotBlank(message = "ssn is missing")
	private String ssn;
	
	//private BankAccount[] bankAccounts;
	//private ArrayList<CheckingAccount> checkingAccounts = null;
	//private ArrayList<SavingsAccount> savingsAccounts = null;
	//private ArrayList<CDAccount> cdAccounts = null;
	
	//@OneToOne(cascade = CascadeType.ALL , mappedBy = "account_holder_id")
	//private List <AccountHoldersContactDetails> contactDetails;
	//AccountHoldersContactDetails contactDetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contact_Info_id" , referencedColumnName = "id")
	private AccountHoldersContactDetails accountHoldersContactDetails;
	
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "account_holder_id")	
	private List<CDAccount> cdAccounts;
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "account_holder_id")	
	private List<SavingsAccount> savingsAccounts;
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "account_holder_id")	
	private List<CheckingAccount> checkingAccounts;
	
	
	

	public AccountHolder() {
	//	this.id = ++nextid;
		
		this.firstName = "";
		this.lastName = "";
		this.ssn = "";
		checkingAccounts = new ArrayList<CheckingAccount>();
		savingsAccounts = new ArrayList<SavingsAccount>();
		cdAccounts = new ArrayList<CDAccount>();
		//contactDetails = new AccountHoldersContactDetails();
		//this.contactDetails= contactDetails;
	}
	
	//need to adjust this to suit the calls when I find out what they are.
	public AccountHolder(String firstName, String middleName, String lastName, String ssn){
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
		checkingAccounts = new ArrayList<CheckingAccount>();
		savingsAccounts = new ArrayList<SavingsAccount>();
		cdAccounts = new ArrayList<CDAccount>();
		//contactDetails = new AccountHoldersContactDetails();
		//this.contactDetails= contactDetails;

	// this.id = ++nextid;
	}
	
	public AccountHolder(String firstName, String middleName, String lastName, String ssn, double checkingAccountOpeningBalance, double savingsAccountOpeningBalance) {
		
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
		checkingAccounts = new ArrayList<CheckingAccount>();
		savingsAccounts = new ArrayList<SavingsAccount>();
		cdAccounts = new ArrayList<CDAccount>();		
		CheckingAccount ca1 = new CheckingAccount(checkingAccountOpeningBalance);
		SavingsAccount sa1 = new SavingsAccount(savingsAccountOpeningBalance);
		addCheckingAccounts(ca1);
		addSavingsAccounts(sa1);
//		this.id = ++nextid;
		
	}
	
	
	public double getCombinedBalance() {
		double total = 0;
		if(checkingAccounts != null) {
			for(CheckingAccount ca : checkingAccounts) {
				total = total + ca.getBalance();
			}
		}
		if(savingsAccounts != null) {
			for(SavingsAccount sa : savingsAccounts) {
				total = total + sa.getBalance();
			}
		}
		if(cdAccounts != null) {
			for(CDAccount cda : cdAccounts) {
				total = total + cda.getBalance();
			}
		}
		return total;
	}
		
	public void addCheckingAccounts(CheckingAccount ca){
		checkingAccounts.add(ca);
		
	}
	
	public void addSavingsAccounts(SavingsAccount sa){
		savingsAccounts.add(sa);
	}
	
	public void addCDAccounts(CDAccount cda){
		cdAccounts.add(cda);		
	}
	
	/*
	public void addDetails(AccountHoldersContactDetails cd) {
		contactDetails.a(cd);
	}	
		
	*/	
	
	
	
	public CheckingAccount addCheckingAccount(double startBalance) throws ExceedsCombinedBalanceLimitException {
		if(getCombinedBalance() + startBalance >= ExceedsCombinedBalanceLimitException.getCombinedbalancelimit()){
			throw new ExceedsCombinedBalanceLimitException();
		}
		CheckingAccount e = new CheckingAccount(startBalance);
		checkingAccounts.add(e);
		
		//adds deposit to transaction list
		DepositTransaction dt = new DepositTransaction(e , startBalance);
		//e.addTransaction(dt);
		
		if(startBalance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}
			
		return e;
		
	}
	
	public CheckingAccount addCheckingAccount(CheckingAccount ca) throws ExceedsCombinedBalanceLimitException {
		Double balance = ca.getBalance();
		
		if(getCombinedBalance() + balance >= ExceedsCombinedBalanceLimitException.getCombinedbalancelimit()){
			throw new ExceedsCombinedBalanceLimitException();
		}
		checkingAccounts.add(ca); //add(ca);
		ca.setAccount_holder_id(this.getId());
		
		//adds deposit to transaction list
		DepositTransaction dt = new DepositTransaction(ca , balance);
		//ca.addTransaction(dt);
		
		if(balance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}		
		
		return ca;
		
	}
	
	public SavingsAccount addSavingsAccount(double startBalance) throws ExceedsCombinedBalanceLimitException {
		if(getCombinedBalance() + startBalance >= ExceedsCombinedBalanceLimitException.getCombinedbalancelimit()){
			throw new ExceedsCombinedBalanceLimitException();
		}
		SavingsAccount e = new SavingsAccount(startBalance);
		savingsAccounts.add(e);
		
		//adds deposit to transaction list
		DepositTransaction dt = new DepositTransaction(e , startBalance);
		//e.addTransaction(dt);
		
		if(startBalance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}		
		
		return e;
	}
	
	public SavingsAccount addSavingsAccount(SavingsAccount sa) throws ExceedsCombinedBalanceLimitException {
		Double balance = sa.getBalance();
		
		if(getCombinedBalance() + balance >= ExceedsCombinedBalanceLimitException.getCombinedbalancelimit()){
			throw new ExceedsCombinedBalanceLimitException();
		}
		savingsAccounts.add(sa);
		sa.setAccount_holder_id(this.getId());
		
		//adds deposit to transaction list
		DepositTransaction dt = new DepositTransaction(sa , balance);
		//sa.addTransaction(dt);
		
		if(balance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}		
		
		return sa;
		
	}	
	
	public CDAccount addCDAccount(CDOffering cDOffering , double startBalance) throws ExceedsFraudSuspicionLimitException , NegativeAmountException {
		

		CDAccount e = new CDAccount(cDOffering , startBalance);
		cdAccounts.add(e);
		
		//adds deposit to transaction list
		Double balance = e.getBalance();
		DepositTransaction dt = new DepositTransaction(e , balance);
		//e.addTransaction(dt);
		
		if(balance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			throw new ExceedsFraudSuspicionLimitException();
		}	
		
		
		return e;				
				
	}
	
	public CDAccount addCDAccount(CDAccount cda) {
		
		cdAccounts.add(cda);
		cda.setAccount_holder_id(this.getId());
		//adds deposit to transaction list
		Double balance = cda.getBalance();
		DepositTransaction dt = new DepositTransaction(cda , balance);
		//cda.addTransaction(dt);		
		
		if(balance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}	
		
		return cda;				
				
	}
	
	

	//Getters and Setters
	
	
		
	public List<CheckingAccount> getCheckingAccounts() {
		return checkingAccounts;
	}

	

	public AccountHoldersContactDetails getAccountHoldersContactDetails() {
		return accountHoldersContactDetails;
	}

	public void setAccountHoldersContactDetails(AccountHoldersContactDetails accountHoldersContactDetails) {
		this.accountHoldersContactDetails = accountHoldersContactDetails;
	}

	public void setCheckingAccounts(ArrayList<CheckingAccount> checkingAccounts) {
		this.checkingAccounts = checkingAccounts;
	}

	public List<SavingsAccount> getSavingsAccounts() {
		return savingsAccounts;
	}

	public void setSavingsAccounts(ArrayList<SavingsAccount> savingsAccounts) {
		this.savingsAccounts = savingsAccounts;
	}

	public List<CDAccount> getCdAccounts() {
		return cdAccounts;
	}

	public void setCdAccounts(ArrayList<CDAccount> cdAccounts) {
		this.cdAccounts = cdAccounts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;}
	
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
		
	}
	
	public String getLastName() {
		return lastName;
	}
			
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public int getNumberOfCheckingAccounts() {
		if(checkingAccounts != null) {
			return checkingAccounts.size();
		}else {
			return 0;
		}
	}
	
	public int getNumberOfSavingsAccounts() {
		if(savingsAccounts != null) {
			return savingsAccounts.size();
		}else {
			return 0;
		}
		
	}
	
	public int getNumberOfCDAccounts() {
		if(cdAccounts != null) {
			return cdAccounts.size();
		}else {
			return 0;
		}
	}	
	
	/*
	public static AccountHolder[] sortAccounts(AccountHolder[] toBeSorted) {
		AccountHolder tempAH; 
		
		for(int i = 0 ; i < toBeSorted.length ; i++) {
			for(int j = i + 1 ; j < toBeSorted.length ; j++) {
				if(toBeSorted[i].getCombinedBalance() > toBeSorted[j].getCombinedBalance()) {
					tempAH = toBeSorted[i];
					toBeSorted[i] = toBeSorted[j];
					toBeSorted[j] = tempAH;
					
				}
			}
		}
		return toBeSorted;
		
	}*/
	/*
	public String writeToString() {
		StringBuilder holderSB = new StringBuilder(firstName + "," + middleName + "," + lastName + "," + ssn + "\n");
		
		int counterCA = getNumberOfCheckingAccounts();
		int counterSA = getNumberOfSavingsAccounts();
		int counterCDA = getNumberOfCDAccounts();
	
		holderSB.append(counterCA + "\n");		
		
		for(CheckingAccount ba : checkingAccounts) {
			holderSB.append(ba.writeToString() + "\n");								
		}
		
		holderSB.append(counterSA + "\n");
		
        for(SavingsAccount ba : savingsAccounts) {
				holderSB.append(ba.writeToString() + "\n");			
        }
        
        holderSB.append(counterCDA + "\n");
		
        for(CDAccount ba : cdAccounts) {
				holderSB.append(ba.writeToString() + "\n");				
        }
                	
		String toBeReturned = holderSB.toString();
		return toBeReturned;
	}
	
	static AccountHolder readFromString(String accountHolderData) {
		AccountHolder toBeAdded = null;
		try{
			
			String[] toBeParsed = accountHolderData.split(",");
			String firstNameToBeAdded = toBeParsed[0];
			String middleNameToBeAdded = toBeParsed[1];
			String lastNameToBeAdded = toBeParsed[2];
			String ssnToBeAdded = toBeParsed[3];
			
			toBeAdded = new AccountHolder(firstNameToBeAdded , middleNameToBeAdded , lastNameToBeAdded , ssnToBeAdded);
			
		}catch(java.lang.Exception exception){
			
		}
		return toBeAdded;
		
	}
	
			
			//throws Exception	
			//Should throw a java.lang.Exception if String cannot be correctly parsed

	
*/

    
	
}