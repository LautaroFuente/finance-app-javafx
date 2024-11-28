package com.finance_app.finance_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.User;


@Service
public class AuthService {
	
	@Autowired
	UserService userService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	public String authUser(String email, String password) {
		User user = this.userService.getOneUser(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return "Usuario logueado";
        }
        else {
        	return "Contraseña incorrecta o email inexistente";
        }
	}

}
