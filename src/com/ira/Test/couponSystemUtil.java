package com.ira.Test;

import java.text.ParseException;

import java.util.Date;
import java.util.List;
import com.ira.DAO.CategoriesDao;
import com.ira.DAO.CouponsDao;
import com.ira.DAO.CustomersDao;
import com.ira.DB.DatabaseManager;
import com.ira.DBDAO.CategoriesDBDAO;
import com.ira.DBDAO.CouponsDBDAO;
import com.ira.DBDAO.CustomersDBDAO;
import com.ira.Exception.ExceptionCompany;
import com.ira.Exception.ExceptionCoupon;
import com.ira.Exception.ExceptionCustomer;
import com.ira.RBAC.AdminFacade;
import com.ira.RBAC.ClientFacade;
import com.ira.RBAC.CompanyFacade;
import com.ira.RBAC.CustomerFacade;
import com.ira.Schedule.CouponExpirationDailyJob;
import com.ira.beans.Category;
import com.ira.beans.Company;
import com.ira.beans.Coupon;
import com.ira.beans.Customer;
import com.ira.utils.DateUtil;
import com.ira.utils.HashUtil;
import com.ira.utils.PrintUtil;

public class couponSystemUtil {

	static Thread job;

	public static void CreateDB() throws ClassNotFoundException {
		// Loading JDBC MySQL Driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		DatabaseManager.createAllTables();
	}

	public static void CreateDailyJob() {
		job = new Thread(new CouponExpirationDailyJob());
		job.start();
	}

	public static void StopDailyJob() {
		job.stop();
	}

	public static void RegisterNewCompany(String email, String compName, String Pass) throws ExceptionCompany {
		Company company = new Company();
        company.setEmail(email);
		company.setName(compName);
		company.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
		company.setPassword(HashUtil.generateHash(Pass, "SHA-256", company.getSalt().getBytes()));

		ClientFacade adminFacadeDao = new AdminFacade();
		((AdminFacade) adminFacadeDao).addCompany(company);
	}

	public static void RegisterNewCustomer(String firstName, String lastName, String Pass, String email)
			throws ExceptionCompany, ExceptionCustomer {
		Customer customer = new Customer();
        customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
		customer.setPassword(HashUtil.generateHash(Pass, "SHA-256", customer.getSalt().getBytes()));
		customer.setEmail(email);

		ClientFacade adminFacadeDao = new AdminFacade();
		((AdminFacade) adminFacadeDao).addCustumer(customer);
	}

	public static void addNewCoupon(int company_ID, Category category, String title, String description,
			Date start_date, Date end_date, int amount, double price, String image) throws ExceptionCoupon {
		Coupon coupon = new Coupon();
		coupon.setcompany_ID(company_ID);
		coupon.setcategory(category);
		coupon.setTitle(title);
		coupon.setDescription(description);
		coupon.setStartDate(start_date);
		coupon.setEndDate(end_date);
		coupon.setAmount(amount);
		coupon.setPrice(price);
		coupon.setImage(image);

		ClientFacade companyFasade = new CompanyFacade();

		((CompanyFacade) companyFasade).addCoupon(coupon);

	}

	public static void addCategory(Category category) {
		CategoriesDao categoriesDao = new CategoriesDBDAO();

		categoriesDao.addCategory(category);
	}

	public static void addCouponPurchase(Coupon coupon, ClientFacade customerFacad) throws ParseException, ExceptionCoupon {
		((CustomerFacade) customerFacad).addPurchaseCoupon(coupon);
	}

	public static void updateCompany(ClientFacade adminFasade) {
		CouponsDao couponsDao = new CouponsDBDAO();
		Company company = new Company();
		company.setId(1);
		company.setEmail("Ira@gmail.com");
		company.setSalt(HashUtil.bytesToStringHex(HashUtil.createSalt()));
		company.setPassword(HashUtil.generateHash("5566", "SHA-256", company.getSalt().getBytes()));

		((AdminFacade) adminFasade).updateCompany(company);
		// have to
		// print??????????????????????????????????????????????????????????????????
		// company.setCoupons(couponsDao.getAllCouponsByCompanyId(company.getId()));
		// PrintUtil.PrintCompany(company);

	}

	public static void deleteCompany(int companyID, ClientFacade adminFacade) {

		((AdminFacade) adminFacade).deleteCompany(companyID);

	}

	public static List<Company> getAllCompanies(ClientFacade adminFacade) {
		 return ((AdminFacade) adminFacade).getAllCompanies();

	}

	public static Company getOneCompany(int company, ClientFacade adminFacade) {
		return ((AdminFacade) adminFacade).getOneCompanyById(company);

	}

	public static void updateCustomer(ClientFacade adminFacade) {

		CustomersDao customersDao = new CustomersDBDAO();
		Customer customer = new Customer();
		customer.setId(1);
		customer.setFirstName("TAL");
		customer.setLastName("MOSHE");
		customer.setSalt(customersDao.getCustomerUserSalt(customer.getId()));
		customer.setPassword(HashUtil.generateHash("2233", "SHA-256", customer.getSalt().getBytes()));
		customer.setEmail("tal@walla.com");

		((AdminFacade) adminFacade).updateCustomer(customer);
	}

	public static void deleteCustomer(int customer_ID, ClientFacade adminFacade) {
		((AdminFacade) adminFacade).deleteCustomer(customer_ID);

	}

	public static List<Customer> getAllCustomers(ClientFacade adminFacade) {
		
		return ((AdminFacade) adminFacade).getAllCustomers();
		

	}

	public static Customer OneCustomer(int customer, ClientFacade adminFacade) {


		return ((AdminFacade) adminFacade).getOneCustomer(customer);

	}

	public static void UpdateCoupon(ClientFacade companyFacade) throws ParseException {
		Coupon coupon = new Coupon();
		coupon.setId(1);
		coupon.setcompany_ID(1);
		coupon.setcategory(Category.FOOD);
		coupon.setTitle("1+1");
		coupon.setDescription("buy one get one");
		coupon.setStartDate(DateUtil.convertDate("2019-07-13"));
		coupon.setEndDate(DateUtil.convertDate("2020-08-16"));
		coupon.setAmount(700);
		coupon.setPrice(600);
		coupon.setImage("http");

		((CompanyFacade) companyFacade).updateCoupon(coupon);
	}

	public static void deleteCoupon(int coupon_id, ClientFacade companyFacade) {
		 ((CompanyFacade) companyFacade).deleteCoupon(coupon_id);

	}

	public static List<Coupon> getAllCoupons(ClientFacade companyFacade) {

       return ((CompanyFacade) companyFacade).getCompanyCoupons();
		

	}

	public static List<Coupon> getAllCouponByCategory(ClientFacade companyFacade, Category category)
			throws ParseException {
		return ((CompanyFacade) companyFacade).getCompanyCouponsByCategory(category);

	}

	public static List<Coupon> getAllCouponsByMaxCoupons(ClientFacade companyFacade, double maxPrice) {
		return ((CompanyFacade) companyFacade).getCompanyCoupons(maxPrice);

	}

	public static Company getCompanyDetails(ClientFacade companyFacade, int company_id) {

		return ((CompanyFacade) companyFacade).getCompanyDetails(company_id);
	}

	public static List<Coupon> getAllCustomerPurchesCoupons(ClientFacade customerFacad, int customer_id) {
		return ((CustomerFacade) customerFacad).getAllCouponsPurchasCustomer();

	}

	public static List<Coupon> getAllCustomerPurchesCouponsByCategory(ClientFacade customerFacade, Category category) {
		return ((CustomerFacade) customerFacade).getCustomerCoupons(category);

	}

	public static List<Coupon>getAllCustomerPurchesCouponsByMaxPrice(ClientFacade customerFacade,double maxprice){
		return ((CustomerFacade)customerFacade).getCustomerCouponsMax(maxprice);
		
}

	public static Customer getAllCustomerDetails(ClientFacade customerFacad) {
		return  ((CustomerFacade)customerFacad).getCustomerDetails();
		
	}
}