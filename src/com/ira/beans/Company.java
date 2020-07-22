package com.ira.beans;

import java.util.List;

public class Company {

		private int id;
		private String name;
		private String email;
		private String password;
		private List<Coupon> coupons;
		private String salt;

		public Company() {
			
			
		}
		public Company(int id,String name, String email, String password) {
			this.name = name;
			this.email = email;
			this.password = password;
			this.id=id;
		}
		

		public Company(int id, String name, String email, String password,String salt, List<Coupon> coupons) {
			this.name = name;
			this.email = email;
			this.password = password;
			this.coupons = coupons;
			this.id=id;
			this.salt=salt;
			
			
		}
		
		

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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

		public String getSalt() {
			return salt;
		}
		public void setSalt(String salt) {
			this.salt = salt;
		}
		
		@Override
		public String toString() {
			return "Company [name=" + name + ", email=" + email + ", password=" + password + ", coupons=" + coupons + ",salt="+salt+"]";
		}

	}


