package com.finance_app.finance_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance_app.finance_app.entities.LimitCategory;

public interface LimitCategoryRepository extends JpaRepository<LimitCategory, Long>{

}
