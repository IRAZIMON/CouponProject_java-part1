package com.ira.DBDAO;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.ira.DB.ConnectionPool;
import com.ira.DAO.CustomersDao;
import com.ira.beans.Customer;

public class CustomersDBDAO implements CustomersDao {

	private static Connection connection = null;

	private static final String IS_CUSTOMER_EXISTS_QUERY = " SELECT * FROM couponprojectira.customers WHERE email =? and password=?";
	private static final String ADD_CUSTOMER_QUERY = "INSERT INTO `couponprojectira`.`customers` (`FIRST_NAME`,`LAST_NAME`,`EMAIL`, `PASSWORD`,`SALT`) VALUES (?,?,?,?,?)";
	private static final String UPDATE_CUSTOMER_QUERY = "UPDATE couponprojectira.customers SET first_name=?, last_name=? ,email =? ,password =? WHERE id=?";
	private static final String DELETE_CUSTOMER_QUERY = "DELETE FROM couponprojectira.customers WHERE (id =?)";
	private static final String LIST_CUSTOMER_QUERY = "SELECT *FROM couponprojectira.customers";
	private static final String GET_ONE_CUSTOMER_QUERY = "SELECT * FROM couponprojectira.customers WHERE id =?";
	private static final String GET_CUSTOMER_SALT_BY_EMAIL_QUERY = "SELECT salt FROM couponprojectira.customers WHERE email=?";
	private static final String GET_CUSTOMER_ID_BY_EMAIL_QUERY = "SELECT id FROM couponprojectira.customers WHERE email=?";
	private static final String IS_CUSTOMER_EXISTS_BY_EMAIL_QUERY = " SELECT * FROM couponprojectira.customers WHERE email =?";
	private static final String GET_CUSTOMER_SALT_BY_ID_QUERY = "SELECT salt FROM couponprojectira.customers WHERE id=?";

	@Override
	public boolean isCustomerExists(String email, String password) {
		Connection connection = null;
		boolean result = false;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = IS_CUSTOMER_EXISTS_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet != null) {
				result = true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return result;
	}

	@Override
	public void addCustomer(Customer customer) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = ADD_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setString(5, customer.getSalt());
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = UPDATE_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setInt(5, customer.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void deleteCustomer(int customer_ID) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = DELETE_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customer_ID);
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = LIST_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstname = resultSet.getString(2);
				String lastname = resultSet.getString(3);
				String email = resultSet.getString(4);
				String password = resultSet.getString(5);
				String salt = resultSet.getString(6);
				customers.add(new Customer(id, firstname, lastname, email, password, salt, null));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
		}
		return customers;
	}

	@Override
	public Customer getOneCustomer(int customerID) {
		Customer customer = null;
		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_ONE_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstname = resultSet.getString(2);
				String lastname = resultSet.getString(3);
				String email = resultSet.getString(4);
                String password=resultSet.getString(5);
                String salt=resultSet.getString(6);
				customer = new Customer(customerID, firstname, lastname, email,password,salt);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return customer;
	}

	/**
	 *  Get customer salt by email of user
	 */
	@Override
	public String getCustomerUserSalt(String email) {
		String salt = null;
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_CUSTOMER_SALT_BY_EMAIL_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				salt = resultSet.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return salt;
	}

	/**
	 * Requesting a customer by email
	 */
	@Override
	public int getCustomerIdByEmail(String email) {
		int id = -1;
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_CUSTOMER_ID_BY_EMAIL_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			id = resultSet.getInt(1);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return id;
	}
	/**
	 * Checks if a customer exists by email 
	 */

	public boolean isCustomerExistsByEmail(String email) {
		boolean result = false;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = IS_CUSTOMER_EXISTS_BY_EMAIL_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return result;
	}
	
	/**
	 * Get customer salt by email of user
	 */

	@Override
	public String getCustomerUserSalt(int customer_ID) {
		String salt = null;
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_CUSTOMER_SALT_BY_ID_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customer_ID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				salt = resultSet.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return salt;
	}

}