package com.finance_app.finance_app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finance_app.finance_app.entities.Transaction;
import com.finance_app.finance_app.enums.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	@Query("SELECT t.category.id AS categoryId, c.name AS categoryName, SUM(t.amount) AS totalAmount "
		     + "FROM Transaction t INNER JOIN Category c ON(t.category.id = c.id) "
		     + "WHERE t.user.id = :userId AND t.date BETWEEN :startDate AND :endDate AND t.type = :type "
		     + "GROUP BY t.category.id")
	List<Object[]> getPercentageByCategoryForUser(@Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("type") TransactionType type);
	
	@Query("SELECT t.type AS type, t.amount AS amount, c.name AS nameCategory, t.date AS date "
			+ "FROM Transaction t INNER JOIN Category c ON (t.category.id = c.id) "
			+ "WHERE t.user.id = :userId "
			+ "ORDER BY t.date DESC")
	Page<Object[]> getAllTransactionsWithNameCategory(@Param("userId") Long userId, Pageable pageable);
	
}
