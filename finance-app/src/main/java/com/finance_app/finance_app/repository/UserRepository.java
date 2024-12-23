package com.finance_app.finance_app.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.finance_app.finance_app.entities.User;


public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.balance = u.balance + :balance WHERE u.id = :id")
	int updateUserBalanceWithIncome(@Param("id") Long id, @Param("balance") BigDecimal balance);
	
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.balance = u.balance - :balance WHERE u.id = :id")
	int updateUserBalanceWithExpense(@Param("id") Long id, @Param("balance") BigDecimal balance);

}
