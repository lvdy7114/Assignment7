package com.meritamerica.assignment5.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.OneToOne;

@Entity
@Table(name ="Users", catalog = "meritbank")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "user_id")
	private Integer id;
	
	private String username;
	private String password;

	private boolean isactive;
	private String roles;

	//Mapped to accountHolder 
	@OneToOne
	@JoinColumn(name= "accountholders" ,referencedColumnName= "id")
	AccountHolder accountHolder;

	
	
	
	public Users() {
		
		this.username = " ";
		this.password = " ";
		this.isactive = true;
		this.roles = roles;
	
	}
	public Users(String username, String password, boolean isactive, String roles ) {
	
		this.username = username;
		this.password = password;
		this.isactive = true;
		this.roles = " ";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	public boolean isActive() {
		return true;
	}
	public void setActive(boolean active) {
		this.isactive = active;
	}
	
	
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public AccountHolder getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}
	
	
	
	
	}
	
	
			
			
			
			
			
	
	
	
	
	
	
	
	
	

