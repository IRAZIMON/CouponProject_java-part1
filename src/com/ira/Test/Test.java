
package com.ira.Test;

import com.ira.Schedule.CouponExpirationDailyJob;


import com.ira.RBAC.AdminFacade;
import com.ira.RBAC.ClientFacade;
import com.ira.RBAC.CompanyFacade;
import com.ira.RBAC.CustomerFacade;
import com.ira.DB.DatabaseManager;
import com.ira.utils.CategoryUtil;
import com.ira.utils.DateUtil;
import com.ira.utils.HashUtil;
import com.ira.utils.PrintUtil;
import com.mysql.cj.util.Util;
import com.mysql.cj.x.protobuf.MysqlxCrud.Update;
import com.ira.DB.ObjectType;
import com.ira.DBDAO.CategoriesDBDAO;
import com.ira.DBDAO.CompaniesDBDAO;
import com.ira.DBDAO.CouponsDBDAO;
import com.ira.DBDAO.CustomersDBDAO;
import com.ira.DBDAO.Customers_vs_couponsDBDAO;
import com.ira.Exception.ExceptionCompany;
import com.ira.Exception.ExceptionCoupon;
import com.ira.Exception.ExceptionCustomer;
import com.ira.Exception.ExceptionLogin;
import com.ira.LoginManager.ClientType;
import com.ira.LoginManager.LoginManager;
import com.ira.beans.Category;
import com.ira.beans.Company;
import com.ira.beans.Coupon;
import com.ira.beans.Customer;
import java.awt.JobAttributes;
import java.awt.desktop.ScreenSleepEvent;
import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.security.auth.login.LoginException;
import com.ira.DAO.CategoriesDao;
import com.ira.DAO.CompaniesDao;
import com.ira.DAO.CouponsDao;
import com.ira.DAO.CustomersDao;
import com.ira.DAO.Customers_vs_couponsDAO;
import com.ira.DB.ConnectionPool;

public class Test {

	public static void TestAll() throws ClassNotFoundException, ExceptionCompany, ExceptionCustomer, ParseException,
			ExceptionCoupon, ExceptionLogin, InterruptedException {
		try {

			// =====================================================================================================
			PrintUtil.printTestInfo("CreateDB");
			couponSystemUtil.CreateDB();
//=====================================================================================================		
   			PrintUtil.printTestInfo("Create Daily Job");
			couponSystemUtil.CreateDailyJob();
//=====================================================================================================	
			   PrintUtil.printTestInfo("Add new category");
			   couponSystemUtil.addCategory(Category.ELECTRICITY);
			   couponSystemUtil.addCategory(Category.FOOD);
			   couponSystemUtil.addCategory(Category.RESTAURANT);
			   couponSystemUtil.addCategory(Category.VACATION);


//=====================================================================================================
			PrintUtil.printTestInfo("Adminstrator operations");
			ClientFacade adminFacade;
			if ((adminFacade = LoginManager.getInstanse().login("admin@admin.com", "admin",
					ClientType.Administrator)) != null) {
//=====================================================================================================	
				PrintUtil.printTestInfo("Register new companies");
				
				couponSystemUtil.RegisterNewCompany("cola@gmail.com", "Coca-cola", "2234");
				couponSystemUtil.RegisterNewCompany("aroma@gmail.com", "Aroma", "3234");
				couponSystemUtil.RegisterNewCompany("pepsi@gmail.com", "Pepsi", "5566");
				couponSystemUtil.RegisterNewCompany("DominosP@gmail.com", "Dominos", "302YY");
				couponSystemUtil.RegisterNewCompany("EL-AL@gmail.com", "El AL", "79087");

				// company already exist

				// couponSystemUtil.RegisterNewCompany("pepsi@gmail.com","Pepsi","5566");

				PrintUtil.printCompanies(((AdminFacade) adminFacade).getAllCompanies());
				

				PrintUtil.printTestInfo("Register new customers");

				couponSystemUtil.RegisterNewCustomer("Tal", "LEVI", "2233", "tal@walla.com", adminFacade);
				couponSystemUtil.RegisterNewCustomer("Gal", "Cohen", "567ddv", "gal@walla.com", adminFacade);
				couponSystemUtil.RegisterNewCustomer("Roni", "Zahavi", "rr2008", "roni@walla.com", adminFacade);
				couponSystemUtil.RegisterNewCustomer("Dana", "DORON", "8899", "dana@walla.com", adminFacade);
				couponSystemUtil.RegisterNewCustomer("Sam", "Ron", "5500", "sam@walla.com", adminFacade);

				PrintUtil.printCustomers(((AdminFacade) adminFacade).getAllCustomers());
			}
			else {
				throw new LoginException("admin@admin.com");
			}
//=====================================================================================================
				
				ClientFacade companyFacade;
				if ((companyFacade = LoginManager.getInstanse().login("pepsi@gmail.com", "5566",
						ClientType.Company)) != null) {

					PrintUtil.printTestInfo("Add new coupons");

					couponSystemUtil.addNewCoupon(Category.FOOD, "50%", "50% discount", DateUtil.convertDate("2019-07-13"),
							DateUtil.convertDate("2020-08-31"), 70, 250, "Coca-cola", companyFacade);
					couponSystemUtil.addNewCoupon(Category.ELECTRICITY, "1+1", "buy one Get one",
							DateUtil.convertDate("2020-01-23"), DateUtil.convertDate("2020-09-13"), 170, 350, "Coca-cola",
							companyFacade);
					couponSystemUtil.addNewCoupon(Category.VACATION, "50%", "50% discount",
							DateUtil.convertDate("2019-07-01"), DateUtil.convertDate("2020-11-23"), 300, 1250, "Aroma",
							companyFacade);
					couponSystemUtil.addNewCoupon(Category.FOOD, "40%", "40% discount", DateUtil.convertDate("2020-02-12"),
							DateUtil.convertDate("2020-10-18"), 270, 500, "Pepsi", companyFacade);
					couponSystemUtil.addNewCoupon(Category.FOOD, "1+1", "buy one Get one",
							DateUtil.convertDate("2020-01-13"), DateUtil.convertDate("2020-07-30"), 35, 80, "Dominos",
							companyFacade);
					couponSystemUtil.addNewCoupon(Category.FOOD, "50%", "50% discount", DateUtil.convertDate("2019-07-13"),
							DateUtil.convertDate("2020-07-22"), 70, 250, "Coca-cola", companyFacade);

					PrintUtil.printCoupons(((CompanyFacade) companyFacade).getCompanyCoupons());
				}
				else {
					throw new ExceptionCoupon("pepsi@gmail.com " +"Not exist");
				}
				
//======================================================================================================================
				
				PrintUtil.printTestInfo("Add customer coupons purchases");
				ClientFacade customerFacade;
				Coupon coupon;
				CouponsDao couponsDao = new CouponsDBDAO();

				if ((customerFacade = LoginManager.getInstanse().login("gal@walla.com", "567ddv",
						ClientType.Customer)) != null) {

					coupon = couponsDao.getOneCoupon(1);
					couponSystemUtil.addCouponPurchase(coupon, customerFacade);
					coupon = couponsDao.getOneCoupon(2);
					couponSystemUtil.addCouponPurchase(coupon, customerFacade);
					coupon = couponsDao.getOneCoupon(3);
					couponSystemUtil.addCouponPurchase(coupon, customerFacade);

				} else {
					throw new ExceptionLogin("gal@walla.com");
				}

				if ((customerFacade = (LoginManager.getInstanse()).login("roni@walla.com", "rr2008",
						ClientType.Customer)) != null) {

					coupon = couponsDao.getOneCoupon(1);
					couponSystemUtil.addCouponPurchase(coupon, customerFacade);
					coupon = couponsDao.getOneCoupon(2);
					couponSystemUtil.addCouponPurchase(coupon, customerFacade);
					coupon = couponsDao.getOneCoupon(3);
					couponSystemUtil.addCouponPurchase(coupon, customerFacade);

				} else {
					throw new ExceptionLogin("roni@walla.com");
				}
				if ((customerFacade = (LoginManager.getInstanse()).login("dana@walla.com", "5500",
						ClientType.Customer)) != null) {
					coupon = couponsDao.getOneCoupon(1);
					couponSystemUtil.addCouponPurchase(coupon, customerFacade);
					coupon = couponsDao.getOneCoupon(2);
					couponSystemUtil.addCouponPurchase(coupon, customerFacade);
					coupon = couponsDao.getOneCoupon(3);
					couponSystemUtil.addCouponPurchase(coupon, customerFacade);

				} else {
					throw new ExceptionLogin("dana@walla.com");
				}			
				
//=====================================================================================================	
				if ((adminFacade = LoginManager.getInstanse().login("admin@admin.com", "admin",
						ClientType.Administrator)) != null) {
					
				// update company cola by email Ira@gmail.com

				  PrintUtil.printTestInfo("Update Company");

			       Company company = new Company();
				   company.setId(1);
				   company.setEmail("Ira@gmail.com");
				   company.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
			       company.setPassword(HashUtil.generateHash("5566", "SHA-256", company.getSalt().getBytes()));

				   couponSystemUtil.updateCompany(adminFacade, company);

				   PrintUtil.printCompanies(((AdminFacade) adminFacade).getAllCompanies());
//======================================================================================================

				   // Deleting cola company

				   PrintUtil.printTestInfo("Delete Cola company");

				   couponSystemUtil.deleteCompany(2, adminFacade);

				   PrintUtil.printCompanies(((AdminFacade) adminFacade).getAllCompanies());
//=============================================================================================================		
			   	   PrintUtil.printTestInfo("Get all companies");

				   PrintUtil.printCompanies(couponSystemUtil.getAllCompanies(adminFacade));

//============================================================================================================

				   // Get Aroma company
				   
				   PrintUtil.printTestInfo("Get one company");
				   PrintUtil.printCompanyHeader();
				   PrintUtil.printCompany(couponSystemUtil.getOneCompany(2, adminFacade));

				

//====================================================================================================
				
				  

				 
				// Update Tal customer last name=moshe

			   	   PrintUtil.printTestInfo("Update customer");
				   CustomersDao customersDao = new CustomersDBDAO();
				   Customer customer = new Customer();
				   customer.setId(1);
				   customer.setFirstName("TAL");
				   customer.setLastName("MOSHE");
				   customer.setSalt(customersDao.getCustomerUserSalt(customer.getId()));
				   customer.setPassword(HashUtil.generateHash("2233", "SHA-256", customer.getSalt().getBytes()));
			   	   customer.setEmail("tal@walla.com");

				   couponSystemUtil.updateCustomer(adminFacade, customer);
				   PrintUtil.printCustomers(((AdminFacade) adminFacade).getAllCustomers());
//=======================================================================================================	

				   // Delete customer Dana

				   PrintUtil.printTestInfo("Delete customer");

				   // couponSystemUtil.deleteCustomer(2, adminFacade);

				   // PrintUtil.printCustomers(((AdminFacade) adminFacade).getAllCustomers());
//======================================================================================================

				   PrintUtil.printTestInfo("Get all customers");

				   couponSystemUtil.getAllCustomers(adminFacade);

				   PrintUtil.printCustomers(((AdminFacade) adminFacade).getAllCustomers());

//=======================================================================================================		

				   // Get Dana customer

				   PrintUtil.printTestInfo("Get One customer");

				   PrintUtil.printCustomerHeader();

				   PrintUtil.printCustomer((couponSystemUtil.OneCustomer(4, adminFacade)));

			} else {
				throw new ExceptionLogin("admin@admin.com");
			}
//=========================================================================================================		

			      PrintUtil.printTestInfo("Company operations");
			

			if ((companyFacade = LoginManager.getInstanse().login("pepsi@gmail.com", "5566",
					ClientType.Company)) != null) {


				  // Update price in coupon = 600
				  PrintUtil.printTestInfo("Update coupon");

				  coupon.setId(1);
				  coupon.setcategory(Category.FOOD);
				  coupon.setTitle("1+1");
				  coupon.setDescription("buy one get one");
				  coupon.setStartDate(DateUtil.convertDate("2019-07-13"));
				  coupon.setEndDate(DateUtil.convertDate("2020-08-16"));
				  coupon.setAmount(700);
				  coupon.setPrice(600);
				  coupon.setImage("http");
				  couponSystemUtil.UpdateCoupon(companyFacade, coupon);
				  PrintUtil.printCoupons(((CompanyFacade) companyFacade).getCompanyCoupons());
//=========================================================================================================

				  PrintUtil.printTestInfo("Delete coupon");

				 // couponSystemUtil.deleteCoupon(2, companyFacade);
				 // PrintUtil.printCoupons(couponSystemUtil.getAllCoupons(companyFacade));

//==========================================================================================================		

				  PrintUtil.printTestInfo("Get all logged in Pepsi company coupons");

				  PrintUtil.printCoupons(couponSystemUtil.getAllCoupons(companyFacade));

//==============================================================================================================		

				  // print all logged in company coupons by category=restaurant

				  PrintUtil.printTestInfo("Get all logged in company coupons by category");

				  PrintUtil.printCoupons(couponSystemUtil.getAllCouponByCategory(companyFacade, Category.FOOD));

				// print all coupons by category not exist to this company

				 PrintUtil.printCoupons(couponSystemUtil.getAllCouponByCategory(companyFacade, Category.RESTAURANT));
//============================================================================================================

				// print company coupons by max price=900

				PrintUtil.printTestInfo("Get all logged in company coupons by maxPrice");

				PrintUtil.printCoupons(couponSystemUtil.getAllCouponsByMaxCoupons(companyFacade, 900));

//============================================================================================================
				// Print Dominos company details

				PrintUtil.printTestInfo("Company details");

				PrintUtil.printCompany(couponSystemUtil.getCompanyDetails(companyFacade, 4));

			} else {
				throw new ExceptionLogin("pepsi@gmail.com");
			}

//=============================================================================================================
			   PrintUtil.printTestInfo("Customer operations");
			   

			   PrintUtil.printTestInfo("Get all logged in customer purches coupons by category=electricity");

			if ((customerFacade = LoginManager.getInstanse().login("gal@walla.com", "567ddv",ClientType.Customer)) != null) {

			   PrintUtil.printCoupons((couponSystemUtil.getAllCustomerPurchesCouponsByCategory(customerFacade,Category.FOOD)));
			} else {
				throw new ExceptionLogin("gal@walla.com");
			}
// ================================================================================================

			    PrintUtil.printTestInfo("Get all customer purches coupons by max price=1000");

			if ((customerFacade = (LoginManager.getInstanse()).login("roni@walla.com", "rr2008",ClientType.Customer)) != null) {

				PrintUtil.printCoupons((couponSystemUtil.getAllCustomerPurchesCouponsByMaxPrice(customerFacade, 1000)));

			} else {
				throw new ExceptionLogin("roni@walla.com");
			}

//========================================================================================================
			   PrintUtil.printTestInfo("Get logged in customer details");
			
			if ((customerFacade = (LoginManager.getInstanse()).login("ronit@walla.com", "rr2008",ClientType.Customer)) != null) {

			   PrintUtil.printCustomer((couponSystemUtil.getAllCustomerDetails(customerFacade)));
			
			} else {
				throw new ExceptionLogin("ronit@walla.com");
			}

// =======================================================================================================
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			 PrintUtil.printTestInfo("stop daily job");
			 Thread.sleep(500000);
			 couponSystemUtil.StopDailyJob();
		}

	}
}
