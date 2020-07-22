package com.ira.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ira.DAO.Customers_vs_couponsDAO;
import com.ira.DB.ConnectionPool;
import com.ira.beans.Category;
import com.ira.beans.Coupon;
import com.ira.beans.Customer;
import com.ira.utils.CategoryUtil;
import com.ira.utils.DateUtil;

public class Customers_vs_couponsDBDAO implements Customers_vs_couponsDAO {

	private static final String DELETE_CUSTOMER_PURCHES_COUPON_HISTORY_QUERY = " DELETE FROM couponprojectira.costumers_vs_coupons WHERE CUSTOMER_ID =?";
	private static final String ADD_NEW_PURCHAS_BY_CUSTOMER_QUERY = "INSERT INTO couponprojectira.costumers_vs_coupons (`CUSTOMER_ID`,`COUPON_ID`) VALUES (?,?)";
	private static final String LIST_COUPON_ID_BY_CUSTOMER_ID_QUERY = "SELECT COUPON_ID FROM couponprojectira.costumers_vs_coupons WHERE CUSTOMER_ID =?";
	private static final String DELETE_CUSTOMER_PURCHES_COUPON_HISTORY_BY_ID__QUERY = "DELETE FROM couponprojectira.costumers_vs_coupons where COUPON_ID = ?";

	/**
	 * Deletes coupons history purchased by the customer
	 */
	@Override
	public void deleteCustomerPurchesCouponHistory(int customer_id) {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = DELETE_CUSTOMER_PURCHES_COUPON_HISTORY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customer_id);
			statement.execute();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}

	}

	/**
	 * Purchase of new customer coupons by customer id and coupon id
	 */
	public void addNewPurchesByCustomer(int customer_id, int coupon_id) {

		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();
			String sql = ADD_NEW_PURCHAS_BY_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customer_id);
			statement.setInt(2, coupon_id);
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}

	}

	/**
	 * Get list of coupons by coupon id
	 */
	@SuppressWarnings("deprecation")
	public List<Integer> getCouponIdList(int customer_id) {
		List<Integer> couponsId = new ArrayList<Integer>();
		Connection connection = null;
System.out.println("customer yyid"+customer_id);
		try {

			connection = ConnectionPool.getInstance().getConnection();
			String sql = LIST_COUPON_ID_BY_CUSTOMER_ID_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, 2);
			ResultSet resultset = statement.executeQuery();
			System.out.println("resultset"+resultset);
			while (resultset.next()) {
				int id = resultset.getInt(1);
				couponsId.add(new Integer(id));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
		}

		return couponsId;
	}

	/**
	 * Deletes coupons history purchased by coupon id
	 */
	@Override
	public void deleteCouponPurchesCouponByID(int COUPON_ID) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = DELETE_CUSTOMER_PURCHES_COUPON_HISTORY_BY_ID__QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, COUPON_ID);
			statement.execute();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}

	}

}
