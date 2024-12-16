package com.finance_app.finance_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.Category;
import com.finance_app.finance_app.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategory(){
		return this.categoryRepository.findAll();
	}
}
