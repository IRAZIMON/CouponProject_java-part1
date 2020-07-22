package com.ira.beans;

import java.util.ArrayList;

import java.util.List;

public class Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String salt;
	private List<Coupon> coupons = new ArrayList<Coupon>();

	public Customer() {

	}

	public Customer(int id,String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.id=id;
	

	}
	public Customer(int id, String firstName, String lastName, String email, String password, String salt){
			 
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.salt = salt;
		
	}

	public Customer(int id, String firstName, String lastName, String email, String password, String salt,
			List<Coupon> coupons) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.coupons = coupons;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSalt() {
		return salt;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", salt=" + salt + ", coupons=" + coupons + "]";
	}
	

	public boolean isCouponExist(Coupon coupon) {
		boolean flag = false;
	
		for (Coupon coupon1 : coupons) {

			if (coupon1.getTitle() == coupon.getTitle()) {
				flag = true;
				break;
			}
		}

		return flag;

	}

	public void addCustomerCoupon(Coupon coupon) {

		coupons.add(coupon);

	}

}
