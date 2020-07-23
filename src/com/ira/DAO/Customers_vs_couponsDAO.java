package com.ira.DAO;

import java.util.List;

import com.ira.beans.Coupon;

public interface Customers_vs_couponsDAO {

	void deleteCustomerPurchesCouponHistory(int customer_id);

	void addNewPurchesByCustomer(int customer_id, int coupon_id);

	List<Integer> getCouponIdList(int customer_id);

	void deleteCouponPurchesCouponByID(int COUPON_ID);
	
	 List<Coupon> getAllCouponsByCustomerId(int customer_ID);
}
