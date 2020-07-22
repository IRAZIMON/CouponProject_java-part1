package com.ira.Exception;

public class ExceptionLogin extends Exception {

	String email;

	public ExceptionLogin(String email) {

		super("email" + email + "failed . Please try again");

		this.email = email;
	}

}
