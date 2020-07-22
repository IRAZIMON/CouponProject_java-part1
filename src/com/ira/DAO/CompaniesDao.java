package com.ira.DAO;

import java.util.List;

import com.ira.beans.Company;


public interface CompaniesDao {
	
	boolean isCompanyExists(String email, String password);

	void addCompany(Company company);

	void UpdateCompany(Company company);

	void DeleteCompany(int companyID);

	List<Company> getAllCompanies();

	Company getOneCompany(int companyID);
	
	String getCompanyUserSalt(String email);
	
	int getCompanyIdByEmail(String email);

	boolean isCompanyExistsByEmailOrName(String email, String name);
}
