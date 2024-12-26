package com.finance_app.finance_app.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.finance_app.finance_app.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
	
	@Query("SELECT w FROM Wallet w WHERE w.user.id = :userId")
	Wallet getOneWallet(@Param("userId") Long userId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Wallet w SET w.total = w.total + :balance WHERE w.user.id = :userId")
	int updateWalletTotalWithIncome(@Param("userId") Long userId, @Param("balance") BigDecimal balance);

	@Transactional
	@Modifying
	@Query("UPDATE Wallet w SET w.total = w.total - :balance WHERE w.user.id = :userId")
	int updateWalletTotalWithExpense(@Param("userId") Long userId, @Param("balance") BigDecimal balance);


}
