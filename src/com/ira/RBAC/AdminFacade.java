package com.ira.RBAC;

import java.util.ArrayList;

import java.util.List;

import com.ira.DAO.CompaniesDao;
import com.ira.DAO.CouponsDao;
import com.ira.DAO.CustomersDao;
import com.ira.DAO.Customers_vs_couponsDAO;
import com.ira.DBDAO.CompaniesDBDAO;
import com.ira.DBDAO.CouponsDBDAO;
import com.ira.DBDAO.CustomersDBDAO;
import com.ira.DBDAO.Customers_vs_couponsDBDAO;
import com.ira.Exception.ExceptionCompany;
import com.ira.Exception.ExceptionCustomer;
import com.ira.beans.Company;
import com.ira.beans.Coupon;
import com.ira.beans.Customer;

public class AdminFacade extends ClientFacade {

	public AdminFacade() {

	}

	public AdminFacade(CompaniesDao companiesDao, CustomersDao customersDao, CouponsDao couponsDao) {
		super(companiesDao, customersDao, couponsDao);

	}

	public boolean login(String username, String password) {

		return username.equals("admin@admin.com") && password.equals("admin");

	}

	public void addCompany(Company company) throws ExceptionCompany {
		companiesDao = new CompaniesDBDAO();
		couponsDao = new CouponsDBDAO();
		boolean tmpcompany;

		tmpcompany = companiesDao.isCompanyExistsByEmailOrName(company.getEmail(), company.getName());

		if (!tmpcompany) {
			companiesDao.addCompany(company);

		} else {
			throw new ExceptionCompany(company.getName(), company.getEmail(), "already exists");
		}
	}

	public void updateCompany(Company company) {

		companiesDao = new CompaniesDBDAO();
		companiesDao.UpdateCompany(company);

	}

	public void deleteCompany(int company_ID) {

		couponsDao = new CouponsDBDAO();
		customers_vs_couponsDAO = new Customers_vs_couponsDBDAO();
		companiesDao = new CompaniesDBDAO();

		List<Coupon> listCouponsId = couponsDao.getAllCouponsByCompanyId(company_ID);

		for (Coupon coupon : listCouponsId) {
			// step 1=delete all coupon purches
			customers_vs_couponsDAO.deleteCouponPurchesCouponByID(coupon.getId());
		}
		// step 2 =delete all coupons
		for (Coupon coupon2 : listCouponsId) {
			couponsDao.deleteCoupon(coupon2.getId());
		}
		// Delete company
		companiesDao.DeleteCompany(company_ID);
	}

	public List<Company> getAllCompanies() {
		companiesDao = new CompaniesDBDAO();
		List<Company> companies = companiesDao.getAllCompanies();
		couponsDao = new CouponsDBDAO();
		for (Company company : companies) {
			System.out.println("companyid" + company.getId());
			System.out.println("admin get all" + couponsDao.getAllCouponsByCompanyId(company.getId()));
			company.setCoupons(couponsDao.getAllCouponsByCompanyId(company.getId()));

		}
		System.out.println("adminfacade" + companies);
		return companies;

	}

	public Company getOneCompanyById(int company_id) {

		couponsDao = new CouponsDBDAO();

		Company tmpCompany = companiesDao.getOneCompany(company_id);

		tmpCompany.setCoupons(couponsDao.getAllCouponsByCompanyId(company_id));

		return tmpCompany;
	}

	public void addCustumer(Customer customer) throws ExceptionCustomer {

		customersDao = new CustomersDBDAO();

		boolean tmpcustumer;

		tmpcustumer = customersDao.isCustomerExistsByEmail(customer.getEmail());

		if (!tmpcustumer) {

			customersDao.addCustomer(customer);

		} else {

			throw new ExceptionCustomer(customer.getEmail(), " already exists");
		}
	}

	public void updateCustomer(Customer customer) {

		customersDao = new CustomersDBDAO();

		customersDao.updateCustomer(customer);
	}

	public void deleteCustomer(int customerID) {

		customersDao = new CustomersDBDAO();

		customers_vs_couponsDAO = new Customers_vs_couponsDBDAO();

		customers_vs_couponsDAO.deleteCustomerPurchesCouponHistory(customerID);
		customersDao.deleteCustomer(customerID);

	}

// coupons=null
	public List<Customer> getAllCustomers() {
		customersDao = new CustomersDBDAO();
		customers_vs_couponsDAO = new Customers_vs_couponsDBDAO();
		List<Customer> customers = customersDao.getAllCustomers();
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Customer customer : customers) {
			List<Integer> tmpCouponsID = customers_vs_couponsDAO.getCouponIdList(customer.getId());

			for (Integer couponID : tmpCouponsID) {
				coupons.add(couponsDao.getOneCoupon(couponID));

			}
			customer.setCoupons(coupons);
			coupons.clear();
		}
		return customers;
	}

//// coupons=null
	public Customer getOneCustomer(int customer_ID) {
		CouponsDao couponsDao = new CouponsDBDAO();
		customersDao = new CustomersDBDAO();
		Customers_vs_couponsDAO customers_vs_couponsDAO = new Customers_vs_couponsDBDAO();
		List<Coupon> tmpListCoupons = new ArrayList<Coupon>();
		Customer tmpCustomer = customersDao.getOneCustomer(customer_ID);
		System.out.println("tmpcustomer"+tmpCustomer);
		System.out.println("customerid"+customer_ID);
		List<Integer> couponIdList = customers_vs_couponsDAO.getCouponIdList(tmpCustomer.getId());
		System.out.println("couponidleast"+couponIdList);
		for (Integer couponID : couponIdList) {
			tmpListCoupons.add(couponsDao.getOneCoupon(couponID));
		}
		tmpCustomer.setCoupons(tmpListCoupons);
		
		System.out.println("tmpcustomerlist "+tmpListCoupons);

		return tmpCustomer;
	}

	@Override
	public String toString() {
		return "AdminFacade [" + "admin" + "]";
	}
}