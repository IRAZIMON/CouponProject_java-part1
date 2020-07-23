package com.ira.Exception;

public class ExceptionCompany extends Exception {

	String name;
	String email;
    String msg;
	

	public ExceptionCompany(String name,String email,String msg)  {

		super("company name " + name + " with email "  + email +" "+ msg);
		
		this.name=name;
		this.email=email;
		this.msg=msg;
	}
	public ExceptionCompany(String msg)  {

		super(msg);
		this.msg=msg;
	}
	
	
	
}
