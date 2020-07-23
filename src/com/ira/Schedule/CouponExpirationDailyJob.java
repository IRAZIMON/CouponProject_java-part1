package com.ira.Schedule;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.ira.DAO.CouponsDao;
import com.ira.DAO.Customers_vs_couponsDAO;
import com.ira.DBDAO.CouponsDBDAO;
import com.ira.DBDAO.Customers_vs_couponsDBDAO;
import com.ira.beans.Coupon;
import com.ira.utils.PrintUtil;

public class CouponExpirationDailyJob implements Runnable {

	private CouponsDao couponsDao= new CouponsDBDAO();

	private Customers_vs_couponsDAO customers_vs_couponsDAO = new Customers_vs_couponsDBDAO() ;

	private boolean quit = false;

	public CouponExpirationDailyJob() {

	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			System.out.println(e1.getMessage());
		}
		
	    while (!quit) {
			try {
				deleteExpiredCoupons();
				PrintUtil.printTestInfo("Outdated coupons were removed");
				PrintUtil.printCoupons(couponsDao.getAllCoupons());
				
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}

			try {
				Thread.sleep(24*60*60*1000);
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
				couponsDao.deleteCoupon(couponID.getId());
			}
			
		}

	}

	public void stop() {
		quit = true;
	}

}
