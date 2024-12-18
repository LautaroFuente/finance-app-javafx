package com.finance_app.finance_app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	public List<Object[]> getPercentageByCategoryForUser(Long userId, LocalDate startOfMonth, LocalDate endOfMonth){
		return this.transactionRepository.getPercentageByCategoryForUser(userId, startOfMonth, endOfMonth);
	}
	
	public Page<Object[]> getAllTransactionsWithNameCategory(Long userId, Pageable pageable){
		return this.transactionRepository.getAllTransactionsWithNameCategory(userId, pageable);
	}
}
