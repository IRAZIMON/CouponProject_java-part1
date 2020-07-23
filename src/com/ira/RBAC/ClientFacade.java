package com.ira.RBAC;

import com.ira.DAO.CompaniesDao;

import com.ira.DAO.CouponsDao;
import com.ira.DAO.CustomersDao;
import com.ira.DAO.Customers_vs_couponsDAO;

public abstract class ClientFacade {
	
	protected CompaniesDao companiesDao;
	
	protected CustomersDao customersDao;
	
	protected CouponsDao couponsDao;
	
	protected Customers_vs_couponsDAO customers_vs_couponsDAO ;
	
	

	public ClientFacade() {
		
	}

	public ClientFacade(CompaniesDao companiesDao, CustomersDao customersDao, CouponsDao couponsDao) {
		this.companiesDao = companiesDao;
		this.customersDao = customersDao;
		this.couponsDao = couponsDao;
	}

	public Customers_vs_couponsDAO getCustomers_vs_couponsDAO() {
		return customers_vs_couponsDAO;
	}

	public void setCustomers_vs_couponsDAO(Customers_vs_couponsDAO customers_vs_couponsDAO) {
		customers_vs_couponsDAO = customers_vs_couponsDAO;
	}

	public ClientFacade(CompaniesDao companiesDao, CustomersDao customersDao, CouponsDao couponsDao,
			com.ira.DAO.Customers_vs_couponsDAO customers_vs_couponsDAO) {
		this.companiesDao = companiesDao;
		this.customersDao = customersDao;
		this.couponsDao = couponsDao;
		this.customers_vs_couponsDAO = customers_vs_couponsDAO;
	}

	public CompaniesDao getCompaniesDao() {
		return companiesDao;
	}

	public void setCompaniesDao(CompaniesDao companiesDao) {
		this.companiesDao = companiesDao;
	}

	public CustomersDao getCustomersDao() {
		return customersDao;
	}

	public void setCustomersDao(CustomersDao customersDao) {
		this.customersDao = customersDao;
	}

	public CouponsDao getCouponsDao() {
		return couponsDao;
	}

	public void setCouponsDao(CouponsDao couponsDao) {
		this.couponsDao = couponsDao;
	}

	
	
	@Override
	public String toString() {
		return "ClientFacadeDao [companiesDao=" + companiesDao + ", customersDao=" + customersDao + ", couponsDao="
				+ couponsDao + ", Customers_vs_couponsDAO=" + customers_vs_couponsDAO + "]";
	}

	public abstract boolean login(String email, String password) ;
	
}
