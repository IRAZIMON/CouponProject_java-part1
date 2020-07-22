package com.ira.LoginManager;

import com.ira.RBAC.AdminFacade;
import com.ira.RBAC.ClientFacade;

import com.ira.RBAC.CompanyFacade;
import com.ira.RBAC.CustomerFacade;


public class LoginManager {

	private static LoginManager instance = null;

	private AdminFacade adminFacade;

	private CompanyFacade companyFacade;

	private CustomerFacade customerFacade;

	private LoginManager() {

	}

	public static LoginManager getInstanse() {

		if (instance == null) {
			synchronized (LoginManager.class) {
				if (instance == null) {
					instance = new LoginManager();
				}

			}

		}
		return instance;
	}

	public ClientFacade login(String email, String passward, ClientType clientype) {

		switch (clientype) {

		case Administrator:
			AdminFacade adminFacade = new AdminFacade();
			if (adminFacade.login(email, passward)) {
				return adminFacade;
			} else {
				return null;
			}

		case Company:

			CompanyFacade companyFacade = new CompanyFacade();
			if (companyFacade.login(email, passward)) {
				return companyFacade;
			} else {
				return null;
			}

		case Customer:

			CustomerFacade customerFacade = new CustomerFacade();

			if (customerFacade.login(email, passward)) {
				return customerFacade;

			} else {
				return null;
			}

		default:
			return null;

		}

	}

}
