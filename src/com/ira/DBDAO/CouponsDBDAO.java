package com.ira.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ira.DB.ConnectionPool;
import com.ira.DAO.CouponsDao;
import com.ira.beans.Category;
import com.ira.beans.Coupon;
import com.ira.utils.CategoryUtil;
import com.ira.utils.DateUtil;

public class CouponsDBDAO implements CouponsDao {

	private static final String ADD_COUPON_QUERY = "INSERT INTO `couponprojectira`.`coupons` (`COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) VALUES ( ?,?,?,?,?,?,?,?,?)";
	private static final String DELETE_COUPON_QUERY = "DELETE FROM couponprojectira.coupons WHERE (id=?)";
	private static final String UPDATE_COUPON_QUERY = "UPDATE `couponprojectira`.`coupons` SET `TITLE` = ?, `DESCRIPTION` = ?, `START_DATE` = ?, `END_DATE` = ?, `AMOUNT` = ?, `PRICE` = ?, `IMAGE` = ? WHERE (`ID` = ?)";
	private static final String LIST_COUPON_QUERY = "SELECT *FROM couponprojectira.coupons;";
	private static final String GET_ONE_COUPON_QUERY = "SELECT * FROM couponprojectira.coupons WHERE ID =?";
	private static final String ADD_COUPON_PURCHASE_QUERY = "INSERT INTO couponprojectira.coupons ( coupon_id, customer_id ) VALUES (?,?)";
	private static final String DELETE_COUPON_PURCHASE_QUERY = "DELETE FROM couponprojectira.coupons ( coupon_id, customer_id ) WHERE (id=?)";
	private static final String IS_COUPON_EXIST_QUERY = "SELECT * FROM couponprojectira.coupons  WHERE title = ? and company_id = ?";
	private static final String LIST_COUPON_BY_CATEGORY_QUERY = "SELECT *FROM couponprojectira.coupons WHERE (Category_id=?)";
	private static final String LIST_COUPON_BY_COMPANY_ID_QUERY = "SELECT *FROM couponprojectira.coupons WHERE (company_id = ?)";
	private static final String GET_ONE_COUPON_BY_ID_CATEGORY_QUERY = "SELECT * FROM couponprojectira.coupons WHERE ID =? AND CATEGORY_ID=?";
	
	@Override
	public void addCoupon(Coupon coupon) {
		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();
			String sql = ADD_COUPON_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, coupon.getcompany_ID());
			statement.setInt(2, coupon.getcategory().ordinal() + 1);
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, DateUtil.convertDate(coupon.getStartDate()));
			statement.setDate(6, DateUtil.convertDate(coupon.getEndDate()));
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());

			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void UpdateCoupon(Coupon coupon) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = UPDATE_COUPON_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, coupon.getTitle());
			statement.setString(2, coupon.getDescription());
			statement.setDate(3, DateUtil.convertDate(coupon.getStartDate()));
			statement.setDate(4, DateUtil.convertDate(coupon.getEndDate()));
			statement.setInt(5, coupon.getAmount());
			statement.setDouble(6, coupon.getPrice());
			statement.setString(7, coupon.getImage());
			statement.setInt(8, coupon.getId());
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void deleteCoupon(int couponID) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = DELETE_COUPON_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			statement.execute();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public List<Coupon> getAllCoupons() {

		List<Coupon> coupons = new ArrayList<>();
		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();
			String sql = LIST_COUPON_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				int id = resultset.getInt(1);
				int companyID = resultset.getInt(2);
				Category category = Category.values()[resultset.getInt(3) - 1];
				String title = resultset.getString(4);
				String description = resultset.getString(5);
				Date start_Date = resultset.getDate(6);
				Date end_Date = resultset.getDate(7);
				int amount = resultset.getInt(8);
				double price = resultset.getDouble(9);
				String image = resultset.getString(10);
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

	@Override
	public Coupon getOneCoupon(int couponID) {
		Connection connection = null;
		Coupon coupon = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_ONE_COUPON_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet resultset = statement.executeQuery();

			if (resultset.next()) {
				coupon = new Coupon();
				coupon.setId(resultset.getInt(1));
				coupon.setcompany_ID(resultset.getInt(2));
				coupon.setcategory(Category.values()[resultset.getInt(3) - 1]);
				coupon.setTitle(resultset.getString(4));
				coupon.setDescription(resultset.getString(5));
				coupon.setStartDate(resultset.getDate(6));
				coupon.setEndDate(resultset.getDate(7));
				coupon.setAmount(resultset.getInt(8));
				coupon.setPrice(resultset.getDouble(9));
				coupon.setImage(resultset.getString(10));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
		}
		return coupon;
	}

	@Override
	public void addCouponPurchase(int customer_ID, int coupon_ID) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = ADD_COUPON_PURCHASE_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon_ID);
			statement.setInt(2, customer_ID);
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public void deleteCouponPurchase(int customer_ID, int coupon_ID) {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = DELETE_COUPON_PURCHASE_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon_ID);
			statement.setInt(2, customer_ID);
			statement.execute();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	/**
	 * Checks if a coupon exists by title and by company id
	 */
	@Override
	public boolean IsCoponExist(String title, int company_ID) {
		Connection connection = null;
		boolean result = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = IS_COUPON_EXIST_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, title);
			statement.setInt(2, company_ID);
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
	 * Requesting a coupon by category
	 */
	@Override
	public List<Coupon> getCompanyCouponsByCategory(Category category) {
		List<Coupon> coupons = new ArrayList<Coupon>();

		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();
			String sql = LIST_COUPON_BY_CATEGORY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, CategoryUtil.CategoryConvertInt(category));
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {

				int id = resultset.getInt(1);
				int companyID = resultset.getInt(2);
				Category category2 = Category.values()[resultset.getInt(3) - 1];
				String title = resultset.getString(4);
				String description = resultset.getString(5);
				Date start_Date = resultset.getDate(6);
				Date end_Date = resultset.getDate(7);
				int amount = resultset.getInt(8);
				double price = resultset.getDouble(9);
				String image = resultset.getString(10);
				coupons.add(new Coupon(id, companyID, category2, title, description, DateUtil.convertDate(start_Date),
						DateUtil.convertDate(end_Date), amount, price, image));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
		}
		return coupons;
	}

	/**
	 * Checks if a coupon exists by id
	 */
	@Override
	public boolean IsCoponExistById(int id) {

		Connection connection = null;
		boolean result = false;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = LIST_COUPON_BY_COMPANY_ID_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
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
	 * Requesting a coupons by company id and category
	 */

	@Override
	public Coupon getOneCouponByCouponIdCategory(int couponID, Category category) {
		Connection connection = null;
		Coupon coupon = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_ONE_COUPON_BY_ID_CATEGORY_QUERY;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			statement.setInt(2, CategoryUtil.CategoryConvertInt(category));

			ResultSet resultset = statement.executeQuery();
			if (resultset.next()) {

				coupon = new Coupon();
				coupon.setId(resultset.getInt(1));
				coupon.setcompany_ID(resultset.getInt(2));
				coupon.setcategory(Category.values()[resultset.getInt(3) - 1]);
				coupon.setTitle(resultset.getString(4));
				coupon.setDescription(resultset.getString(5));
				coupon.setStartDate(resultset.getDate(6));
				coupon.setEndDate(resultset.getDate(7));
				coupon.setAmount(resultset.getInt(8));
				coupon.setPrice(resultset.getDouble(9));
				coupon.setImage(resultset.getString(10));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return coupon;

	}

	@Override

	public List<Coupon> getAllCouponsByCompanyId(int company_ID) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = LIST_COUPON_BY_COMPANY_ID_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, company_ID);
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