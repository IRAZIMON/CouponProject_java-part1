package com.ira.DAO;

import java.util.List;

import com.ira.beans.Company;
import com.ira.beans.Customer;

public interface CustomersDao {
	
	boolean isCustomerExists(String email, String password);

	void addCustomer(Customer Custumer);

	void updateCustomer(Customer Custumer);

	void deleteCustomer(int customer_ID);

	List<Customer> getAllCustomers();

	Customer getOneCustomer(int customerID);
	
    int getCustomerIdByEmail(String email);

	String getCustomerUserSalt(String email);
	
	boolean isCustomerExistsByEmail(String email);
	
	
	String getCustomerUserSalt(int customer_id);
	
	//List<Customer> getAllCustomers(int customer_ID);
}
