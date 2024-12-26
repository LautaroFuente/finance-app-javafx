package com.finance_app.finance_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance_app.finance_app.entities.User;


public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);

}
