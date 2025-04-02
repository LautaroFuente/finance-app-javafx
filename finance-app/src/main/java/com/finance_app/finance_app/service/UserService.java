package com.finance_app.finance_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;	
	private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
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
	
	public User getOneUser(String email) {
		Optional<User> usuario = this.userRepository.findByEmail(email);
	    return usuario.orElse(null);
	}
	
	public void deleteUser(User user) {
		this.userRepository.delete(user);
	}
	
}
