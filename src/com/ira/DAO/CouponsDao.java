package com.ira.DAO;

import java.util.List;

import com.ira.beans.Category;
import com.ira.beans.Company;
import com.ira.beans.Coupon;

public interface CouponsDao {

	void addCoupon(Coupon coupon);

	void UpdateCoupon(Coupon coupon);

	void deleteCoupon(int CouponID);

	List<Coupon> getAllCoupons();

	Coupon getOneCoupon(int couponID);

	void addCouponPurchase(int customerID, int couponID);

	void deleteCouponPurchase(int customerID, int couponID);

	boolean IsCoponExist(String titel, int CompanyID);

	boolean IsCoponExistById(int company_ID);

	List<Coupon> getCompanyCouponsByCategory(Category category);

//	void deleteCouponPurchaseCouponId(int coupon_ID);

    List<Coupon> getAllCouponsByCompanyId(int company_ID);
    
  

	Coupon getOneCoupon(int couponID, Category category);

	
}
