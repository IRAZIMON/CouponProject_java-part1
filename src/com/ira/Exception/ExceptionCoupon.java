package com.ira.Exception;

public class ExceptionCoupon extends Exception {

	String title;
	int company_ID;
	String msg;

	public ExceptionCoupon(String title, int company_ID, String msg) {

		super("coupon title" + title + " with company_ID " + company_ID + msg);
		this.title = title;
		this.company_ID = company_ID;
		this.msg = msg;
	}

	    public ExceptionCoupon(String msg) {

		super(msg);
		this.msg = msg;
	}
}
