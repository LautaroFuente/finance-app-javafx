package com.finance_app.finance_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.LimitCategory;
import com.finance_app.finance_app.repository.LimitCategoryRepository;

@Service
public class LimitCategoryService {

	@Autowired
	private LimitCategoryRepository limitCategoryRepository;
	
	public LimitCategory addLimitCategory(LimitCategory limitCategory) {
		return this.limitCategoryRepository.save(limitCategory);
	}
	
	public void deleteLimitCategory (LimitCategory limitCategory) {
		this.limitCategoryRepository.delete(limitCategory);
	}
	
}
