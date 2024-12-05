package com.finance_app.finance_app.utils;

import com.finance_app.finance_app.entities.User;

public class SessionManager {

	private static SessionManager instance;
	private User user;
	
	private SessionManager() {
        // Constructor privado para evitar instanciación externa
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
	
	public void login(User user) {
		this.user = user;
	}

	public void logout() {
		this.user = null;
	}

	public User getUser() {
		return user;
	}
	
	
}
