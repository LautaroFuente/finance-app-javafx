package com.finance_app.finance_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String addUser(String name, String email, String password) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		String hashedPassword = passwordEncoder.encode(password);
		user.setPassword(hashedPassword);
		
		try {
			User userSaved = this.userRepository.save(user);
			
			if(userSaved != null) {
				return "Usuario registrado exitosamente";
			}
			else {
				throw new Error("Error al guardar el usuario");
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
