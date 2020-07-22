package com.ira.DBDAO;

import java.sql.Connection;

import java.sql.PreparedStatement;

import com.ira.DAO.CategoriesDao;
import com.ira.DB.ConnectionPool;
import com.ira.beans.Category;
import com.ira.utils.CategoryUtil;

public class CategoriesDBDAO implements CategoriesDao {

	private static final String ADD_CATEGORY_QUERY = "INSERT INTO `couponprojectira`.`categories`(`NAME`) VALUES (?)";

	@Override
	public void addCategory(Category category_name) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = ADD_CATEGORY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, CategoryUtil.CategoryConvert(category_name));

			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);

		}

	}

}
