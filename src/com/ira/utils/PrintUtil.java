package com.ira.utils;

import java.util.List;

import com.ira.beans.Company;
import com.ira.beans.Coupon;
import com.ira.beans.Customer;

public class PrintUtil {

	public static void printTestInfo(String msg) {
		System.out.println("-------------------------" + msg + "-------------------------------\n");

	}

	public static void printCoupon(Coupon coupon) {

		String[] row = new String[] { String.valueOf(coupon.getId()), String.valueOf(coupon.getcompany_ID()),
				CategoryUtil.CategoryConvert(coupon.getcategory()), coupon.getTitle(), coupon.getDescription(),
				DateUtil.GetDateStr(coupon.getStartDate()), DateUtil.GetDateStr(coupon.getStartDate()),
				String.valueOf(coupon.getAmount()), String.valueOf(coupon.getPrice()), coupon.getImage() };

		System.out.format("%20s|%20s|%20s|%20s|%20s|%20s|%20s|%20s|%20s|%20s\n", row);
	}

	public static void printCoupons(List<Coupon> coupons) {

		printCouponHeader();
		for (Coupon coupon : coupons) {

			printCoupon(coupon);

		}

	}

	public static void printCompany(Company company) {

		if (company != null)
		{
		 String[] row = new String[] { String.valueOf(company.getId()), company.getName(), company.getEmail(),
				(company.getCoupons()).toString() };

		  System.out.format("%20s|%20s|%20s%20s\n", row);
		}

	}

	public static void printCompanies(List<Company> allCompanies) {

		printCompanyHeader();

		for (Company company : allCompanies) {
			printCompany(company);

		}
	}

	public static void printCustomer(Customer customer) {
		
		
        String[] row = new String[] { String.valueOf(customer.getId()), customer.getFirstName(), customer.getLastName(),
				customer.getEmail(),customer.getLastName(),customer.getCoupons().toString() };
		System.out.format("%20s|%20s|%20s|%20s|%20s|%20s\n", row);

	}

	public static void printCustomers(List<Customer> allCustomers) {

		printCustomerHeader();

		for (Customer customer : allCustomers) {

			printCustomer(customer);
		}

	}


	public static void printCustomerHeader() {
		String[] header = new String[] { "ID", "FIRST_NAME", "LAST_NAME", "EMAIL", "COUPONS_LIST" };
		System.out.format("%20s|%20s|%20s|%20s|%20s\n", header);
	}

	public static void printCompanyHeader() {
		String[] header = new String[] { "ID", "NAME", "EMAIL", "COUPONS_LIST" };
		System.out.format("%20s|%20s|%20s|%20s\n", header);
	}

	public static void printCouponHeader() {
		String[] header = new String[] { "ID", " COMPANY_ID", "CATEGORY", "TITLE", " DESCRIPTION", "START_DATE",
				"END_DATE", "ANOUNT", "PRICE", "IMAGE" };
		System.out.format("%20s|%20s|%20s|%20s|%20s|%20s|%20s|%20s|%20s|%20s\n", header);
	}
	
}
