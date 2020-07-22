package com.ira.RBAC;

import java.util.ArrayList;

import java.util.List;
import com.ira.DAO.CompaniesDao;
import com.ira.DAO.CouponsDao;
import com.ira.DAO.CustomersDao;
import com.ira.DBDAO.CompaniesDBDAO;
import com.ira.DBDAO.CouponsDBDAO;
import com.ira.DBDAO.Customers_vs_couponsDBDAO;
import com.ira.Exception.ExceptionCoupon;
import com.ira.beans.Category;
import com.ira.beans.Company;
import com.ira.beans.Coupon;
import com.ira.utils.HashUtil;

public class CompanyFacade extends ClientFacade {

	private int companyID;

	public CompanyFacade() {

	}

	public CompanyFacade(CompaniesDao companiesDao, CustomersDao customersDao, CouponsDao couponsDao) {
		super(companiesDao, customersDao, couponsDao);

	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	@Override
	public boolean login(String email, String password) {
		String salt;
		String hashedSaltPassword;
		boolean activeId = false;
		companiesDao = new CompaniesDBDAO();
		if ((salt = companiesDao.getCompanyUserSalt(email)) != null) {
			hashedSaltPassword = HashUtil.generateHash(password, "SHA-256", salt.getBytes());
			activeId = companiesDao.isCompanyExists(email, hashedSaltPassword);
		}

		if (activeId) {
			companyID = companiesDao.getCompanyIdByEmail(email);
		}

		return activeId;
	}

	public void addCoupon(Coupon coupon) throws ExceptionCoupon {

		boolean tempCoupon;

		couponsDao = new CouponsDBDAO();
		tempCoupon = couponsDao.IsCoponExist(coupon.getTitle(), coupon.getcompany_ID());
		if (!tempCoupon) {
			couponsDao.addCoupon(coupon);
		} else {
			throw new ExceptionCoupon(coupon.getTitle(), coupon.getcompany_ID(), " already exists");
		}
	}

	public void updateCoupon(Coupon coupon) {

		couponsDao = new CouponsDBDAO();
		couponsDao.UpdateCoupon(coupon);

	}

	public void deleteCoupon(int coupon_id) {
		couponsDao = new CouponsDBDAO();

		customers_vs_couponsDAO = new Customers_vs_couponsDBDAO();

		customers_vs_couponsDAO.deleteCouponPurchesCouponByID(coupon_id);

		couponsDao.deleteCoupon(coupon_id);

	}

	public List<Coupon> getCompanyCoupons() {
		couponsDao = new CouponsDBDAO();

		List<Coupon> couponByCompanyId = new ArrayList<Coupon>();

		List<Coupon> coupons = couponsDao.getAllCoupons();

		for (Coupon coupon : coupons) {

			if (coupon.getcompany_ID() == companyID) {
				couponByCompanyId.add(coupon);

			}
		}

		return couponByCompanyId;

	}

	public List<Coupon> getCompanyCouponsByCategory(Category category) {
        List<Coupon> couponByCompanyId = new ArrayList<Coupon>();
        List<Coupon> coupons = couponsDao.getCompanyCouponsByCategory(category);
		for (Coupon coupon : coupons) {
            if (coupon.getcompany_ID() == companyID) {
				couponByCompanyId.add(coupon);
			}
		}
		return couponByCompanyId;
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {
		couponsDao = new CouponsDBDAO();

		List<Coupon> couponByCompanyId = new ArrayList<Coupon>();

		List<Coupon> coupons = couponsDao.getAllCoupons();

		for (Coupon coupon : coupons) {

			if (coupon.getcompany_ID() == companyID && coupon.getPrice() < maxPrice) {

				couponByCompanyId.add(coupon);
			}

		}

		return couponByCompanyId;
	}

	public Company getCompanyDetails(int company_id) {
		couponsDao = new CouponsDBDAO();

		Company tmpCompany = companiesDao.getOneCompany(company_id);
		tmpCompany.setCoupons(couponsDao.getAllCouponsByCompanyId(company_id));
System.out.println("company datails company facade"+tmpCompany);
		return tmpCompany;

	}

	@Override
	public String toString() {
		return "CompanyFacade [companyID=" + companyID + "]";
	}

}