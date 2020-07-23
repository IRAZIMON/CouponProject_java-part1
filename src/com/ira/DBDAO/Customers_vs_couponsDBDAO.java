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
	private static final String LIST_COUPON_BY_CUSTOMER_ID_QUERY = "SELECT *FROM couponprojectira.costumers_vs_coupons WHERE CUSTOMER_ID = ?";

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

		try {

			connection = ConnectionPool.getInstance().getConnection();
			String sql = LIST_COUPON_ID_BY_CUSTOMER_ID_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1,customer_id);
			ResultSet resultset = statement.executeQuery();
		
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
	/**
	 * Requesting a one coupon by customer id 
	 */

	@Override
	public List<Coupon> getAllCouponsByCustomerId(int customer_ID) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = LIST_COUPON_BY_CUSTOMER_ID_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customer_ID);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt(1);
				int companyID = resultSet.getInt(2);
				Category category = Category.values()[resultSet.getInt(3) - 1];
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date start_Date = resultSet.getDate(6);
				Date end_Date = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				coupons.add(new Coupon(id, companyID, category, title, description, DateUtil.convertDate(start_Date),
						DateUtil.convertDate(end_Date), amount, price, image));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return coupons;
	}

}
