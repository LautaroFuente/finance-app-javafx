package com.finance_app.finance_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.repository.LimitCategoryRepository;

@Service
public class LimitCategoryService {

	@Autowired
	LimitCategoryRepository limitCategoryRepository;
}
