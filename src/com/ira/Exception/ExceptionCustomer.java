package com.ira.Exception;
;

public class ExceptionCustomer extends Exception{
	

    String email;
    String msg;
	
	public ExceptionCustomer(String email,String msg)  {

		super("customer " + "with email " + email + msg);
		
	    this.email=email;
	    this.msg=msg;
	}

}
