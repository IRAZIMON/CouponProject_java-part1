package com.ira.DBDAO;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.ira.DB.ConnectionPool;
import com.ira.DAO.CompaniesDao;
import com.ira.beans.Company;

public class CompaniesDBDAO implements CompaniesDao {

	private static final String IS_COMPANY_EXISTS_QUERY = "SELECT * FROM couponprojectira.companies  WHERE email = ? and password = ?";
	private static final String ADD_COMPANY_QUERY = "INSERT INTO `couponprojectira`.`companies` (`NAME`, `EMAIL`, `PASSWORD`,`SALT`) VALUES (?,?,?,?)";
	private static final String UPDATE_COMPANY_QUERY = "UPDATE couponprojectira.companies SET email =? ,password =?, salt=? WHERE id=?";
	private static final String DELETE_COMPANY_QUERY = " DELETE FROM couponprojectira.companies WHERE id =?";
	private static final String LIST_COMPANY_QUERY = "SELECT *FROM couponprojectira.companies";
	private static final String GET_ONE_COMPANY_QUERY = "SELECT * FROM couponprojectira.companies WHERE id =?";
	private static final String GET_COMPANY_SALT_QUERY = "SELECT SALT FROM couponprojectira.companies WHERE email=?";
	private static final String GET_COMPANY_ID_BY_EMAIL_QUERY = "SELECT id FROM couponprojectira.companies WHERE email=?";
	private static final String IS_COMPANY_EXISTS_BY_EMAIL_OR_NAME_QUERY = "SELECT * FROM couponprojectira.companies  WHERE email = ? OR name = ?";

	@Override
	public boolean isCompanyExists(String email, String password) {
		Connection connection = null;
		boolean result = false;

		try {
			connection = ConnectionPool.getInstance().getConnection();
            String sql = IS_COMPANY_EXISTS_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
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

	@Override
	public void addCompany(Company company) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = ADD_COMPANY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.setString(4, company.getSalt());
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void UpdateCompany(Company company) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = UPDATE_COMPANY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getEmail());
			statement.setString(2, company.getPassword());
			statement.setString(3, company.getSalt());
			statement.setInt(4, company.getId());
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void DeleteCompany(int CompanyID) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = DELETE_COMPANY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, CompanyID);
			statement.execute();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public List<Company> getAllCompanies() {

		List<Company> companies = new ArrayList<Company>();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = LIST_COMPANY_QUERY;
             PreparedStatement statement = connection.prepareStatement(sql);
         	ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				String password = resultSet.getString(4);
				String salt = resultSet.getString(5);
				companies.add(new Company(id, name, email, password,salt,null));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);

		}
		
		return companies;
	}

	@Override
	public Company getOneCompany(int companyID) {
		Company company = null;
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_ONE_COMPANY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			String email = resultSet.getString(3);
			String password = resultSet.getString(4);
			company = new Company(id, name, email, password);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return company;
	}

	/**
	 * Get company salt by email of user
	 */
	@Override
	public String getCompanyUserSalt(String email) {
		String salt = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_COMPANY_SALT_QUERY;
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
	 * Get company id by email of user
	 */
	@Override
	public int getCompanyIdByEmail(String email) {
		int id = -1;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_COMPANY_ID_BY_EMAIL_QUERY;
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
	 * Checks if exists in coupon system a company by name and email
	 */
	@Override
	public boolean isCompanyExistsByEmailOrName(String email, String name) {
		Connection connection = null;
		boolean result = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = IS_COMPANY_EXISTS_BY_EMAIL_OR_NAME_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, name);
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
}