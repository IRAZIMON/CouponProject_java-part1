package com.ira.DB;

import java.sql.Connection;

import java.sql.PreparedStatement;

public class DatabaseManager {

	public static String url = "jdbc:mysql://localhost:3306/couponprojectira?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";
	public static String username = "root";
	public static String password = "ip200683";

	public static final String CREATE_SCHEMA_COUPONPROJECTIRA = "CREATE SCHEMA couponprojectira";
	public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `couponprojectira`.`categories` (\r\n"
			+ "  `ID` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `NAME` VARCHAR(45) NOT NULL,\r\n"
			+ "  PRIMARY KEY (`ID`));";

	public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `couponprojectira`.`companies` (\r\n"
			+ "  `ID` int NOT NULL AUTO_INCREMENT,\r\n" + "  `NAME` varchar(45) NOT NULL,\r\n"
			+ "  `EMAIL` varchar(45) NOT NULL,\r\n" + "  `PASSWORD` varchar(256) NOT NULL,\r\n"
			+ "  `SALT` varchar(45) NOT NULL,\r\n" + "  PRIMARY KEY (`ID`),\r\n"
			+ "  UNIQUE KEY `NAME_UNIQUE` (`NAME`));";

	public static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `couponprojectira`.`customers` (\r\n"
			+ "  `ID` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `FIRST_NAME` VARCHAR(45) NOT NULL,\r\n"
			+ "  `LAST_NAME` VARCHAR(45) NOT NULL,\r\n" + "  `EMAIL` VARCHAR(45) NOT NULL,\r\n"
			+ "  `PASSWORD` VARCHAR(256) NOT NULL,\r\n" + "  `SALT` varchar(45) NOT NULL,\r\n"
			+ "  PRIMARY KEY (`ID`));";
	public static final String CREATE_TABLE_COUPONS = " CREATE TABLE `couponprojectira`.`coupons` (\r\n"
			+ "  `ID` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `COMPANY_ID` INT NOT NULL,\r\n"
			+ "  `CATEGORY_ID` INT NOT NULL,\r\n" + "  `TITLE` VARCHAR(45) NOT NULL,\r\n"
			+ "  `DESCRIPTION` VARCHAR(45) NOT NULL,\r\n" + "  `START_DATE` DATE NOT NULL,\r\n"
			+ "  `END_DATE` DATE NOT NULL,\r\n" + "  `AMOUNT` INT NOT NULL,\r\n" + "  `PRICE` DOUBLE NOT NULL,\r\n"
			+ "  `IMAGE` VARCHAR(256) NOT NULL,\r\n" + "  PRIMARY KEY (`ID`),\r\n"
			+ "  INDEX `COMPANY_ID_idx` (`COMPANY_ID` ASC) VISIBLE,\r\n"
			+ "  INDEX `CATEGORY_ID_idx` (`CATEGORY_ID` ASC) VISIBLE,\r\n" + "  CONSTRAINT `COMPANY_ID`\r\n"
			+ "    FOREIGN KEY (`COMPANY_ID`)\r\n" + "   REFERENCES `couponprojectira`.`companies` (`ID`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `CATEGORY_ID`\r\n"
			+ "    FOREIGN KEY (`CATEGORY_ID`)\r\n" + "    REFERENCES `couponprojectira`.`categories` (`ID`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);";

	public static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE `couponprojectira`.`costumers_vs_coupons` (\r\n"
			+ "  `CUSTOMER_ID` INT NOT NULL,\r\n" + "  `COUPON_ID` INT NOT NULL,\r\n"
			+ "  PRIMARY KEY (`CUSTOMER_ID`, `COUPON_ID`),\r\n"
			+ "  INDEX `COUPON_ID_idx` (`COUPON_ID` ASC) VISIBLE,\r\n" + "  CONSTRAINT `CUSTOMER_ID`\r\n"
			+ "    FOREIGN KEY (`CUSTOMER_ID`)\r\n" + "    REFERENCES `couponprojectira`.`customers` (`ID`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `COUPON_ID`\r\n"
			+ "    FOREIGN KEY (`COUPON_ID`)\r\n" + "    REFERENCES `couponprojectira`.`coupons` (`ID`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);";

	public static String getUrl() {
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static void createAllTables() {

		dropAllTables();
		createObjectInDB(CREATE_SCHEMA_COUPONPROJECTIRA);
		createObjectInDB(CREATE_TABLE_CATEGORIES);
		createObjectInDB(CREATE_TABLE_COMPANIES);
		createObjectInDB(CREATE_TABLE_COUPONS);
		createObjectInDB(CREATE_TABLE_CUSTOMERS);
		createObjectInDB(CREATE_TABLE_CUSTOMERS_VS_COUPONS);

	}

	public static void dropAllTables() {

		dropObjectInDB("costumers_vs_coupons", ObjectType.Table);
		dropObjectInDB("customers", ObjectType.Table);
		dropObjectInDB("coupons", ObjectType.Table);
		dropObjectInDB("companies", ObjectType.Table);
		dropObjectInDB("categories", ObjectType.Table);
		dropObjectInDB("couponprojectira", ObjectType.Schema);

	}

	// Create Objects in DB
	private static void createObjectInDB(String sql) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);

		}
	}

	// Drop objects: Table or Schema from DB
	private static void dropObjectInDB(String objectName, ObjectType MyobjectType) {
		Connection connection = null;
	
		try {
			connection = ConnectionPool.getInstance().getConnection();
			
			String drop;
			if (MyobjectType == ObjectType.Table) {
				drop = "DROP TABLE IF EXISTS" + "`" + objectName + "`" + ";";
			} else {
				drop = "DROP SCHEMA IF EXISTS" + "`" + objectName + "`" + ";";
			}
			PreparedStatement statement = connection.prepareStatement(drop);
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			
			ConnectionPool.getInstance().returnConnection(connection);

		}
	}

}
