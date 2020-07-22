package com.ira.Schedule;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.ira.DAO.CouponsDao;
import com.ira.DAO.Customers_vs_couponsDAO;
import com.ira.DBDAO.CouponsDBDAO;
import com.ira.beans.Coupon;

public class CouponExpirationDailyJob implements Runnable {

	private CouponsDao couponsDao;

	private Customers_vs_couponsDAO customers_vs_couponsDAO;

	private boolean quit = false;

	public CouponExpirationDailyJob() {

	}

	@Override
	public void run() {
		couponsDao = new CouponsDBDAO();
		while (!quit) {

			try {
				deleteExpiredCoupons();
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

		}
	}

	public void deleteExpiredCoupons() throws ParseException {

		List<Coupon> coupons = couponsDao.getAllCoupons();
		for (Coupon couponID : coupons) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(LocalDate.now().toString());
			boolean isExpired = date1.after(couponID.getEndDate());
			if (isExpired) {
				customers_vs_couponsDAO.deleteCouponPurchesCouponByID(couponID.getId());
			}
			couponsDao.deleteCoupon(couponID.getId());
		}

	}

	public void stop() {
		quit = true;
	}

}
