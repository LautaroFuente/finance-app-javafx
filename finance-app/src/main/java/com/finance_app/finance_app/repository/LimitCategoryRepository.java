package com.finance_app.finance_app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finance_app.finance_app.entities.LimitCategory;

public interface LimitCategoryRepository extends JpaRepository<LimitCategory, Long>{

	@Query("SELECT lc FROM LimitCategory lc WHERE lc.user.id = :userId AND lc.category.id = :categoryId AND lc.date_from <= :date AND lc.date_to >= :date")
	List<LimitCategory> findActiveLimitsForCategory(@Param("userId") Long userId, 
	                                                @Param("categoryId") Long categoryId, 
	                                                @Param("date") LocalDateTime date);

}
