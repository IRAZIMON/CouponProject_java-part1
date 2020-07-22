package com.ira.Test;

import com.ira.Schedule.*;

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

//=====================================================================================================		
			PrintUtil.printTestInfo("CreateDB");
			couponSystemUtil.CreateDB();
//=====================================================================================================		
			PrintUtil.printTestInfo("Create Daily Job");
			// couponSystemUtil.CreateDailyJob();
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
				
				 //company already exist
			
				// couponSystemUtil.RegisterNewCompany("pepsi@gmail.com","Pepsi","5566");

				PrintUtil.printCompanies(((AdminFacade) adminFacade).getAllCompanies());
//=====================================================================================================	
				
				// update company cola by email
				
//				PrintUtil.printTestInfo("Update Company");
//				couponSystemUtil.updateCompany(adminFacade);
//
//				PrintUtil.printCompanies(((AdminFacade) adminFacade).getAllCompanies());
//======================================================================================================
               
                // Deleting  cola company
                 
					
//				PrintUtil.printTestInfo("Delete company");
//
//				couponSystemUtil.deleteCompany(1, adminFacade);
//
//				PrintUtil.printCompanies(((AdminFacade) adminFacade).getAllCompanies());
//=============================================================================================================		
				PrintUtil.printTestInfo("Get all companies");

			
				PrintUtil.printCompanies(couponSystemUtil.getAllCompanies(adminFacade));

			

//============================================================================================================
				PrintUtil.printTestInfo("Get one company");
				PrintUtil.printCompanyHeader();
				PrintUtil.printCompany(couponSystemUtil.getOneCompany(2, adminFacade));

//==========================================================================================================

				PrintUtil.printTestInfo("Register new customers");

				couponSystemUtil.RegisterNewCustomer("Tal", "LEVI", "2233", "tal@walla.com");
				couponSystemUtil.RegisterNewCustomer("Gal", "Cohen", "567ddv", "gal@walla.com");
				couponSystemUtil.RegisterNewCustomer("Roni", "Zahavi", "rr2008", "ron@walla.com");
				couponSystemUtil.RegisterNewCustomer("Dana", "DORON", "8899", "dana@walla.com");
				couponSystemUtil.RegisterNewCustomer("Sam", "Ron", "5500", "sam@walla.com");

				PrintUtil.printCustomers(((AdminFacade) adminFacade).getAllCustomers());

//====================================================================================================

				// Update Tal customer last name
				PrintUtil.printTestInfo("Update customer");

				//couponSystemUtil.updateCustomer(adminFacade);
				//PrintUtil.printCustomers(((AdminFacade) adminFacade).getAllCustomers());
//=======================================================================================================	

				//Delete customer Dana 
				
				PrintUtil.printTestInfo("Delete customer");

				//couponSystemUtil.deleteCustomer(4, adminFacade);

			//	PrintUtil.printCustomers(((AdminFacade) adminFacade).getAllCustomers());
//======================================================================================================

				PrintUtil.printTestInfo("Get all customers");

				couponSystemUtil.getAllCustomers(adminFacade);
				
				PrintUtil.printCustomers(((AdminFacade) adminFacade).getAllCustomers());

//=======================================================================================================		
				PrintUtil.printTestInfo("Get One customer");

				PrintUtil.printCustomerHeader();

				PrintUtil.printCustomer((couponSystemUtil.OneCustomer(2, adminFacade)));

			} else {
				throw new ExceptionLogin("admin@admin.com");
			}
//=========================================================================================================		

			PrintUtil.printTestInfo("Company operations");
			ClientFacade companyFacade;

			if ((companyFacade = LoginManager.getInstanse().login("pepsi@gmail.com", "5566",
					ClientType.Company)) != null) {
// ====================================================================================================

				PrintUtil.printTestInfo("Add new coupons");

				couponSystemUtil.addNewCoupon(1, Category.FOOD, "50%", "50% discount",
						DateUtil.convertDate("2019-07-13"), DateUtil.convertDate("2020-07-23"), 70, 250, "Coca-cola");
				couponSystemUtil.addNewCoupon(1, Category.ELECTRICITY, "1+1", "buy one Get one",
						DateUtil.convertDate("2020-01-23"), DateUtil.convertDate("2020-09-13"), 170, 350, "Coca-cola");
				couponSystemUtil.addNewCoupon(2, Category.VACATION, "50%", "50% discount",
						DateUtil.convertDate("2019-07-01"), DateUtil.convertDate("2020-11-23"), 300, 1250, "Aroma");
				couponSystemUtil.addNewCoupon(3, Category.RESTAURANT, "40%", "40% discount",
						DateUtil.convertDate("2020-02-12"), DateUtil.convertDate("2020-10-18"), 270, 500, "Pepsi");
				couponSystemUtil.addNewCoupon(4, Category.FOOD, "1+1", "buy one Get one",
						DateUtil.convertDate("2020-01-13"), DateUtil.convertDate("2020-07-30"), 35, 80, "Dominos");

				CouponsDao couponsDao = new CouponsDBDAO();

				PrintUtil.printCoupons(couponsDao.getAllCoupons());

//=========================================================================================================		
				
				//Update price in coupon num 1
				PrintUtil.printTestInfo("Update coupon");
				couponSystemUtil.UpdateCoupon(companyFacade);

//=========================================================================================================

				PrintUtil.printTestInfo("Delete coupon");

			//	couponSystemUtil.deleteCoupon(3, companyFacade);
				
			//  PrintUtil.printCoupons(couponSystemUtil.getAllCoupons(companyFacade);
//==========================================================================================================		

				PrintUtil.printTestInfo("Get all company coupons");

				PrintUtil.printCoupons(couponSystemUtil.getAllCoupons(companyFacade));

//==============================================================================================================		

				// print all coupons by category the company that login
				
				PrintUtil.printTestInfo("Get all company coupons by category");

				PrintUtil.printCoupons(couponSystemUtil.getAllCouponByCategory(companyFacade, Category.RESTAURANT));
				
				//print all coupons by category not exist to this company
				//PrintUtil.printCoupons(couponSystemUtil.getAllCouponByCategory(companyFacade, Category.FOOD));
//============================================================================================================
				PrintUtil.printTestInfo("Get all company coupons by maxPrice");

				PrintUtil.printCoupons(couponSystemUtil.getAllCouponsByMaxCoupons(companyFacade, 900));

//============================================================================================================
				PrintUtil.printTestInfo("Company details");

				PrintUtil.printCompany(couponSystemUtil.getCompanyDetails(companyFacade, 4));

			} else {
				throw new ExceptionLogin("pepsi@gmail.com");
			}

//=============================================================================================================
			PrintUtil.printTestInfo("Customer operations");
			ClientFacade customerFacade;

			if ((customerFacade = LoginManager.getInstanse().login("gal@walla.com", "567ddv",
					ClientType.Customer)) != null) {

//====================================================================================================================		
				PrintUtil.printTestInfo("Add customer coupons purches");

				ClientFacade customerFacad;
				Coupon coupon;
				CouponsDao couponsDao = new CouponsDBDAO();

				if ((customerFacad = (LoginManager.getInstanse()).login("tal@walla.com", "2233",
						ClientType.Customer)) != null) {

					coupon = couponsDao.getOneCoupon(1);
					couponSystemUtil.addCouponPurchase(coupon, customerFacad);
					coupon = couponsDao.getOneCoupon(2);
					couponSystemUtil.addCouponPurchase(coupon, customerFacad);
					coupon = couponsDao.getOneCoupon(3);
					couponSystemUtil.addCouponPurchase(coupon, customerFacad);
					
			
				}

				if ((customerFacad = (LoginManager.getInstanse()).login("gal@walla.com", "567ddv",
						ClientType.Customer)) != null) {

					coupon = couponsDao.getOneCoupon(1);
					couponSystemUtil.addCouponPurchase(coupon, customerFacad);
					coupon = couponsDao.getOneCoupon(2);
					couponSystemUtil.addCouponPurchase(coupon, customerFacad);
					coupon = couponsDao.getOneCoupon(3);
					couponSystemUtil.addCouponPurchase(coupon, customerFacad);

				}
				if ((customerFacad = (LoginManager.getInstanse()).login("dana@walla.com", "5500",
						ClientType.Customer)) != null) {
					coupon = couponsDao.getOneCoupon(1);
					couponSystemUtil.addCouponPurchase(coupon, customerFacad);
					coupon = couponsDao.getOneCoupon(2);
					couponSystemUtil.addCouponPurchase(coupon, customerFacad);
					coupon = couponsDao.getOneCoupon(3);
					couponSystemUtil.addCouponPurchase(coupon, customerFacad);

				}
//==================================================================================================		
				PrintUtil.printTestInfo("Get all logged in customer purches coupons");

				PrintUtil.printCoupons((couponSystemUtil.getAllCustomerPurchesCoupons(customerFacad, 2)));

//===============================================================================================================	
				PrintUtil.printTestInfo("Get all logged in customer purches coupons by category");

				PrintUtil.printCoupons((couponSystemUtil.getAllCustomerPurchesCouponsByCategory(customerFacade,
						Category.ELECTRICITY)));

// ================================================================================================
				PrintUtil.printTestInfo("Get all logged in customer purches coupons by max price");

				PrintUtil.printCoupons((couponSystemUtil.getAllCustomerPurchesCouponsByMaxPrice(customerFacade, 1000)));

//========================================================================================================
				PrintUtil.printTestInfo("Get all  logged in customer details");

				PrintUtil.printCustomer((couponSystemUtil.getAllCustomerDetails(customerFacad)));

			} else {
				throw new ExceptionLogin("gal@walla.com");
			}

		}
// =======================================================================================================

		catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// PrintUtil.printTestInfo("stop daily job");
			// Thread.sleep(2000000);
			// couponSystemUtil.StopDailyJob();
		}

	}
}

// System.out.println("*******GET ONE COMPANY FACADE*****");

//
//				System.out.println(((AdminFacade) adminFacadeDao).getOneCompanyById(company2.getId()));
//
// System.out.println("*****UPDATE COMPANY FACADE*******");
//
//			company.setName("gogo");
//
//			((AdminFacade) adminFacadeDao).updateCompany(company);
//
//			company.setCoupons(couponsDao.getAllCouponsByCompanyId(company.getId()));
//
//			System.out.println("update company" + company);

//	if (CustomerFacade.login("tal@walla.com", "2233")!=null) {
//
//					System.out.println("login succeed");
//
//					coupon = couponsDao.getOneCoupon(1);
//
//					((CustomerFacade) CustomerFacade).addPurchaseCoupon(coupon);
//
//					coupon = couponsDao.getOneCoupon(2);
//
//					((CustomerFacade) CustomerFacade).addPurchaseCoupon(coupon);
//
//					coupon = couponsDao.getOneCoupon(3);
//
//					((CustomerFacade) CustomerFacade).addPurchaseCoupon(coupon);

//====================================================================================================
//		System.out.println("*************************************************************");
//
//		System.out.println("********TEST COMPANY******");
//
//		System.out.println("create 1 company: ");
//
//		Company company = new Company();
//		company.setId(1);
//		company.setEmail("cola@gmail.com");
//		company.setName("coca-cola");
//		company.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
//		company.setPassword(HashUtil.generateHash("2234", "SHA-256", company.getSalt().getBytes()));
//		System.out.println("company " + company);
//
//		System.out.println("create 2 company: ");
//
//		Company company2 = new Company();
//		company2.setId(2);
//		company2.setEmail("aroma@gmail.com");
//		company2.setName("aroma");
//		company2.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
//		company2.setPassword(HashUtil.generateHash("3234", "SHA-256", company2.getSalt().getBytes()));
//
//		System.out.println("company2 " + company2);
//
//		System.out.println("create 3 company: ");
//
//		Company company3 = new Company();
//		company3.setId(3);
//		company3.setEmail("pepsi@gmail.com");
//		company3.setName("pepsi");
//		company3.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
//		company3.setPassword(HashUtil.generateHash("5566", "SHA-256", company3.getSalt().getBytes()));
//
//		System.out.println("company " + company3);
//
//		CompaniesDao companiesDao = new CompaniesDBDO();
//
//		System.out.println("ADD COMPANY: ");
//		companiesDao.addCompany(company);
//		companiesDao.addCompany(company2);
//		companiesDao.addCompany(company3);
//
//		// System.out.println("***GET ONE COMPANY****8);
//		// Company fromDB=companiesDao.getOneCompany(1);
//		// fromDB.setName("pepsi");
//
//		// System.out.println(****"UPDATE COMPANY"****);
//		// companiesDao.UpdateCompany(fromDB);
//
//		// System.out.println(*****"DELETE COMPANY"*****);
//		// companiesDao.DeleteCompany(1);
//		// companiesDao.DeleteCompany(2);
//
//		// System.out.println(*****"GET ALL COMPANIES"****);
//		// companiesDao.getAllCompanies();
//
//		System.out.println("************************************************************************");
//
//		System.out.println("*****************TEST CUSTOMER***********************");
//
//		System.out.println("create 1 customer:");
//
//		Customer customer = new Customer();
//		customer.setId(1);
//		customer.setFirstName("TAL");
//		customer.setLastName("LEVI");
//		customer.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
//		customer.setPassword(HashUtil.generateHash("2233", "SHA-256", customer.getSalt().getBytes()));
//		customer.setEmail("tal@walla.com");
//
//		System.out.println("customer " + customer);
//
//		System.out.println("create 2 customer:");
//		Customer customer2 = new Customer();
//		customer2.setId(2);
//		customer2.setFirstName("GAL");
//		customer2.setLastName("COHEN");
//		customer2.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
//		customer2.setPassword(HashUtil.generateHash("567ddv", "SHA-256", customer2.getSalt().getBytes()));
//		customer2.setEmail("gal@walla.com");
//
//		System.out.println("customer 2 " + customer2);
//
//		System.out.println("create 3 customer:");
//
//		Customer customer3 = new Customer();
//		customer3.setId(3);
//		customer3.setFirstName("RON");
//		customer3.setLastName("ZEHAVI");
//		customer3.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
//		customer3.setPassword(HashUtil.generateHash("rr2008", "SHA-256", customer3.getSalt().getBytes()));
//		customer3.setEmail("ron@walla.com");
//
//		System.out.println("customer3 " + customer3);
//
//		CustomersDao customersDao = new CustomersDBDO();
//
//		System.out.println("ADD CUSTOMER: ");
//		customersDao.addCustomer(customer);
//		customersDao.addCustomer(customer2);
//		customersDao.addCustomer(customer3);
//
//		/// customersDBDO.UpdateCustomer(customer);
//		// customersDBDO.DeleteCustomer(2);
//		// customersDBDO.getAllCustomers();
//		// customersDBDO.getOneCustomer(2);
//
//		// test
//		// System.out.println(customer.getEmail());
//		// System.out.println(customer.getPassword());
//
//		System.out.println("*****************TEST CATEGORIES************");
//
//		System.out.println("******CREATE CATEGORIES*****");
//
//		Category category = Category.FOOD;
//
//		Category category2 = Category.ELECTRICITY;
//
//		Category category3 = Category.RESTAURANT;
//
//		Category category4 = Category.VACATION;
//
//		CategoriesDao categoriesDao = new CategoriesDBDO();
//
//		categoriesDao.addCategory(category);
//		categoriesDao.addCategory(category2);
//		categoriesDao.addCategory(category3);
//		categoriesDao.addCategory(category4);
//
//		System.out.println("*************************************************");
//
//		PrintUtil.printTestInfo("print coupon");
//
//		PrintUtil.printTestInfo("create 1 coupon: ");
//
//		Coupon coupon = new Coupon();
//		coupon.setId(1);
//		coupon.setcompany_ID(1);
//		coupon.setcategory(Category.FOOD);
//		coupon.setTitle("1+1");
//		coupon.setDescription("buy one get one");
//		coupon.setStartDate(DateUtil.convertDate("2019-07-13"));
//		coupon.setEndDate(DateUtil.convertDate("2020-08-16"));
//		coupon.setAmount(700);
//		coupon.setPrice(70);
//		coupon.setImage("http");
//
//		System.out.println("create 2 coupon: ");
//		Coupon coupon2 = new Coupon();
//		coupon2.setId(2);
//		coupon2.setcompany_ID(2);
//		coupon2.setcategory(Category.ELECTRICITY);
//		coupon2.setTitle("1+1");
//		coupon2.setDescription("buy one get one");
//		coupon2.setStartDate(DateUtil.convertDate("2019-07-13"));
//		coupon2.setEndDate(DateUtil.convertDate("2020-08-16"));
//		coupon2.setAmount(300);
//		coupon2.setPrice(500);
//		coupon2.setImage("http");
//
//		System.out.println("create 3 coupon: ");
//		Coupon coupon3 = new Coupon();
//		coupon3.setId(3);
//		coupon3.setcompany_ID(1);
//		coupon3.setcategory(Category.ELECTRICITY);
//		coupon3.setTitle("1+1");
//		coupon3.setDescription("buy one get one");
//		coupon3.setStartDate(DateUtil.convertDate("2019-07-13"));
//		coupon3.setEndDate(DateUtil.convertDate("2020-08-16"));
//		coupon3.setAmount(800);
//		coupon3.setPrice(800);
//		coupon3.setImage("http");
//
//		CouponsDao couponsDao = new CouponsDBDO();
//
//		System.out.println("**************************************************************");
//		System.out.println("************COMPANY FACADE TEST******************************");
//
//		ClientFacade companyFacade = new CompanyFacade();
//
//		PrintUtil.printTestInfo("ADD COUPON FACADE");
//
//		((CompanyFacade) companyFacade).addCoupon(coupon);
//
//		Coupon tmpcoupon = couponsDao.getOneCoupon(coupon.getId());
//
//		PrintUtil.printCoupon(tmpcoupon);
//
//		((CompanyFacade) companyFacade).addCoupon(coupon2);
//
//		Coupon tmpcoupon2 = couponsDao.getOneCoupon(coupon2.getId());
//
//		PrintUtil.printCoupon(tmpcoupon2);
//
//		((CompanyFacade) companyFacade).addCoupon(coupon3);
//
//		Coupon tmpcoupon3 = couponsDao.getOneCoupon(coupon3.getId());
//
//		PrintUtil.printCoupon(tmpcoupon3);
//
//		PrintUtil.printTestInfo("UPDATE COUPON FACADE");
//
//		coupon2.setAmount(9000);
//
//		((CompanyFacade) companyFacade).updateCoupon(coupon2);
//
//		tmpcoupon = couponsDao.getOneCoupon(coupon2.getId());
//
//		PrintUtil.printTestInfo("DELETE COUPON FACADE");
//
//		// ((CompanyFacade) companyFacade).deleteCoupon(1);
//
//		PrintUtil.printTestInfo("GET COMPANY COUPONS");
//
//		if (companyFacade.login("cola@gmail.com", "2234")) {
//			PrintUtil.printTestInfo(" login succeed");
//			List<Coupon> coupons = ((CompanyFacade) companyFacade).getCompanyCoupons();
//
//			PrintUtil.PrintCoupons(coupons);
//			// for (Coupon couponsList : coupons) {
//			// System.out.println(couponsList);
//
//		} else {
//			PrintUtil.printTestInfo(" login failed");
//		}
//
//		System.out.println("*****GET COMPANY COUPONS BY CATEGORY****");
//
//		if (companyFacade.login("aroma@gmail.com", "3234")) {
//			System.out.println("succeed");
//
//			List<Coupon> coupons = ((CompanyFacade) companyFacade).getCompanyCouponsByCategory(category2);
//			for (Coupon couponsList : coupons) {
//				System.out.println(couponsList);
//			}
//		} else {
//			System.out.println("*******login failed*****");
//		}
//
//		System.out.println("GET COLA COUPONS BY MAX PRICE: ");
//		if (companyFacade.login("pepsi@gmail.com", "5566")) {
//
//			System.out.println("succeed");
//
//			List<Coupon> coupons = ((CompanyFacade) companyFacade).getCompanyCoupons(2000);
//			for (Coupon couponList : coupons) {
//				System.out.println(couponList);
//
//			}
//		} else {
//			System.out.println("login failed");
//		}
//
//		System.out.println("******GET COMPANY DETAILS*****");
//
//		if (companyFacade.login("cola@gmail.com", "2234")) {
//
//			System.out.println("login succeed");
//
//			System.out.println(
//					((CompanyFacade) companyFacade).getCompanyDetails(((CompanyFacade) companyFacade).getCompanyID()));
//
//		} else {
//			System.out.println("login failed");
//		}
//		System.out.println("**************************************************************");
//
//		System.out.println("************ADMIN FACADE TEST******************************");
//
//		System.out.println("*******ADD COMPANY FACADE*****");
//
//		ClientFacade adminFacadeDao = new AdminFacade();
//
//		((AdminFacade) adminFacadeDao).addCompany(company3);
//
//		System.out.println("*****UPDATE COMPANY FACADE*******");
//
//		company.setName("gogo");
//
//		((AdminFacade) adminFacadeDao).updateCompany(company);
//
//		company.setCoupons(couponsDao.getAllCouponsByCompanyId(company.getId()));
//
//		System.out.println("update company" + company);
//
//		System.out.println("******DELETE COMPANY FACADE*******");
//
//		Customers_vs_couponsDAO customers_vs_couponsDAO = new Customers_vs_couponsDBDO();
//
//		((AdminFacade) adminFacadeDao).deleteCompany(1);
//
//		System.out.println("*******GET LIST COMPANIES FACADE****");
//		System.out.println(((AdminFacade) adminFacadeDao).getAllCompanies());
//
//		System.out.println("*******GET ONE COMPANY FACADE*****");
//
//		System.out.println(((AdminFacade) adminFacadeDao).getOneCompanyById(company2.getId()));
//
//		System.out.println("******ADD NEW CUSTOMER ADMIN FACADE****");
//
//		((AdminFacade) adminFacadeDao).addCustumer(customer);
//		((AdminFacade) adminFacadeDao).addCustumer(customer2);
//		((AdminFacade) adminFacadeDao).addCustumer(customer3);
//
//		System.out.println("add 1 customer" + customer);
//		System.out.println("add 2 customer" + customer2);
//		System.out.println("add 3 customer" + customer3);
//
//		System.out.println("***UPDATE CUSTOMER ADMIN FACADE******");
//
//		customer2.setFirstName("DUDI");
//
//		((AdminFacade) adminFacadeDao).updateCustomer(customer2);
//
//		System.out.println(customer2);
//
//////chek
//		System.out.println("*****DELETE CUSTOMER ADMIN FACADE***********");
//
//		// ((AdminFacade) adminFacadeDao).deleteCustomer(customer.getId());
//		// add purchas coupon to delete customer in admin facade
//
//		// System.out.println("******GET ALL CUSTOMERS ADMIN FACADE*****");
//
//		// System.out.println(((AdminFacade) adminFacadeDao).getAllCustomers());
//
//		// System.out.println("******GET ONE CUSTOMER ADMIN FACADE******");
//
//		// customers_vs_couponsDAO = new Customers_vs_couponsDBDO();
//
//		// customer2.setCoupons(customers_vs_couponsDAO.getAllCouponsByCustomerId(customer2));
//
//		// System.out.println((((AdminFacade)
//		// adminFacadeDao).getOneCustomer(customer2.getId())));
//
//		// update list of coupons after add purches coupons by customer
//
//		System.out.println("**************************************************************");
//
//		System.out.println("***********CUSTOMER FACADE TEST******************************");
//
//		System.out.println("**************addPurchaseCoupon*********************************");
//		ClientFacade CustomerFacade = new CustomerFacade();
//
//		if (CustomerFacade.login("tal@walla.com", "2233")) {
//
//			System.out.println("login succeed");
//
//			coupon = couponsDao.getOneCoupon(1);
//
//			((CustomerFacade) CustomerFacade).addPurchaseCoupon(coupon);
//
//			coupon = couponsDao.getOneCoupon(2);
//
//			((CustomerFacade) CustomerFacade).addPurchaseCoupon(coupon);
//
//			coupon = couponsDao.getOneCoupon(3);
//
//			((CustomerFacade) CustomerFacade).addPurchaseCoupon(coupon);
//
//			System.out.println("*****: GET ALL CUSTOMERS ADMIN FACADE*****");
//			System.out.println(((AdminFacade) adminFacadeDao).getAllCustomers());
//
//			System.out.println("**************GET CUSTOMER COUPON*********************************");
//			if (CustomerFacade.login("tal@walla.com", "2233")) {
//
//				System.out.println("login succeed");
//
//				System.out.println(((CustomerFacade) CustomerFacade).getAllCouponsPurchasCustomer());
//
//			}
//
//		}
//		System.out.println("**************GET CUSTOMER COUPON BY CATEGORY*********************************");
//
//		if (CustomerFacade.login("ron@walla.com", "rr2008")) {
//
//			System.out.println("login succeed");
//
//			System.out.println(((CustomerFacade) CustomerFacade).getCustomerCoupons(category2));
//		}
//
//		System.out.println("**************GET CUSTOMER COUPON BY MAX PRICE*********************************");
//
//		if (CustomerFacade.login("tal@walla.com", "2233")) {
//
//			System.out.println("login succeed");
//
//			System.out.println(((CustomerFacade) CustomerFacade).getCustomerCouponsMax(2000));
//		}
//		System.out.println("**************GET CUSTOMER COUPON BY DETAILS*********************************");
//		if (CustomerFacade.login("tal@walla.com", "2233")) {
//
//			System.out.println("login succeed");
//
//			System.out.println(((CustomerFacade) CustomerFacade).getCustomerDetails());
//
//		}
//		System.out.println("********LOGIN MANAGER*****************************");
//
//		ClientFacade clientFacade = LoginManager.getInstanse().login("ron@walla.com", "rr2008", ClientType.Customer);
//		System.out.println(clientFacade);
//
//		// Thread.sleep(2000000);
//
//		// StopDailyJob
