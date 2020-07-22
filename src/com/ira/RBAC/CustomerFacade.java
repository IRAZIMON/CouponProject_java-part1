package com.ira.RBAC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.ira.DAO.CustomersDao;
import com.ira.DAO.Customers_vs_couponsDAO;
import com.ira.DBDAO.CompaniesDBDAO;
import com.ira.DBDAO.CouponsDBDAO;
import com.ira.DBDAO.CustomersDBDAO;
import com.ira.DBDAO.Customers_vs_couponsDBDAO;
import com.ira.Exception.ExceptionCoupon;
import com.ira.beans.Category;
import com.ira.beans.Coupon;
import com.ira.beans.Customer;
import com.ira.utils.CategoryUtil;
import com.ira.utils.DateUtil;
import com.ira.utils.HashUtil;

public class CustomerFacade extends ClientFacade {

	int customerID;

	@Override
	public boolean login(String email, String password) {

		String salt;
		String hashedSaltPassword;
		boolean activeId = false;

		customersDao = new CustomersDBDAO();
		salt = customersDao.getCustomerUserSalt(email);
		if (salt != null) {
			hashedSaltPassword = HashUtil.generateHash(password, "SHA-256", salt.getBytes());
			activeId = customersDao.isCustomerExists(email, hashedSaltPassword);

		}
		if (activeId) {
			customerID = customersDao.getCustomerIdByEmail(email);

		}

		return activeId;
	}

	public void addPurchaseCoupon(Coupon coupon) throws ParseException, ExceptionCoupon {

		if (coupon != null) {
			customers_vs_couponsDAO = new Customers_vs_couponsDBDAO();
			customersDao = new CustomersDBDAO();
			couponsDao = new CouponsDBDAO();

			Customer customer = customersDao.getOneCustomer(customerID);

			Coupon tmpcoupon = couponsDao.getOneCoupon(coupon.getId());

			if (tmpcoupon != null) {

				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = sdf.parse(LocalDate.now().toString());

				boolean isExpired = date1.after(coupon.getEndDate());

				if (!customer.isCouponExist(coupon) && ((tmpcoupon).getAmount() > 0) && !isExpired) {

					customers_vs_couponsDAO.addNewPurchesByCustomer(customer.getId(), coupon.getId());

					coupon.setAmount(coupon.getAmount() - 1);

					couponsDao.UpdateCoupon(coupon);

					customer.addCustomerCoupon(coupon);
				}

				else {
					throw new ExceptionCoupon(coupon.getTitle(), coupon.getcompany_ID(), " already exists");
				}
			}
		}

	}

	public List<Coupon> getAllCouponsPurchasCustomer() {

		customers_vs_couponsDAO = new Customers_vs_couponsDBDAO();

		couponsDao = new CouponsDBDAO();

		List<Coupon> listCoupons = new ArrayList<Coupon>();

		Coupon tmpCoupon;

		List<Integer> listCouponId = customers_vs_couponsDAO.getCouponIdList(customerID);

		for (Integer couponId : listCouponId) {

			tmpCoupon = couponsDao.getOneCoupon(couponId.intValue());

			listCoupons.add(tmpCoupon);
		}

		return listCoupons;
	}

	public List<Coupon> getCustomerCoupons(Category category) {

		customers_vs_couponsDAO = new Customers_vs_couponsDBDAO();

		couponsDao = new CouponsDBDAO();

		List<Coupon> listCouponIC = new ArrayList<Coupon>();

		List<Integer> listCouponsID = customers_vs_couponsDAO.getCouponIdList(customerID);

		for (Integer couponID : listCouponsID) {

			Coupon couponCategory = couponsDao.getOneCoupon(couponID, category);
			if (couponCategory != null) {

				listCouponIC.add(couponCategory);
			}

		}

		return listCouponIC;

	}

	public List<Coupon> getCustomerCouponsMax(double maxPrice) {

		customers_vs_couponsDAO = new Customers_vs_couponsDBDAO();

		couponsDao = new CouponsDBDAO();

		List<Coupon> listCouponsIC = new ArrayList<Coupon>();

		List<Integer> listCouponId = customers_vs_couponsDAO.getCouponIdList(customerID);

		for (Integer couponID : listCouponId) {

			Coupon couponMaxPrice = couponsDao.getOneCoupon(couponID);

			if (couponMaxPrice.getPrice() < maxPrice) {

				listCouponsIC.add(couponMaxPrice);
			}

		}
		return listCouponsIC;

	}

	public Customer getCustomerDetails() {
		
	

		Customer tmpCustomer = customersDao.getOneCustomer(customerID);

		tmpCustomer.setCoupons(getAllCouponsPurchasCustomer());
		return tmpCustomer;

	}

	@Override
	public String toString() {
		return "CustomerFacade [customerID=" + customerID + "]";
	}

}
