package com.finance_app.finance_app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.Transaction;
import com.finance_app.finance_app.enums.TransactionType;
import com.finance_app.finance_app.repository.TransactionRepository;
import com.finance_app.finance_app.utils.SessionManager;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private LimitCategoryService limitCategoryService;
	
	public List<Object[]> getPercentageByCategoryForUser(Long userId, LocalDateTime startOfMonth, LocalDateTime endOfMonth){
		return this.transactionRepository.getPercentageByCategoryForUser(userId, startOfMonth, endOfMonth, TransactionType.GASTO);
	}
	
	public Page<Object[]> getAllTransactionsWithNameCategory(Long userId, Pageable pageable){
		return this.transactionRepository.getAllTransactionsWithNameCategory(userId, pageable);
	}
	
	public String addTransactionForUser(Transaction transaction){
	    try {
	        // Guardar la transacción en la base de datos
	        Transaction transactionSaved = this.transactionRepository.save(transaction);
	        
	        // Verifica si la transacción fue guardada correctamente
	        if (transactionSaved != null) {
	        	
	        	// Asignar nuevo saldo del usuario	        	
	        	if(transactionSaved.getType() == TransactionType.INGRESO) {
	        		this.walletService.updateWalletTotalWithIncome(SessionManager.getInstance().getUser().getId(), transactionSaved.getAmount());
	        	}
	        	else if(transactionSaved.getType() == TransactionType.GASTO) {
	        		this.walletService.updateWalletTotalWithExpense(SessionManager.getInstance().getUser().getId(), transactionSaved.getAmount());
	        		
	        		// Llamar a LimitCategoryService para que se actualice (si existe) el total gastado de un limite asignado a una categoria
	        		this.limitCategoryService.updateTotalIfExistLimitVigent(transactionSaved);
	        	}
	        	
	            return "Transacción guardada exitosamente";
	        } else {
	            return "Error al guardar la transacción";
	        }
	    } catch (DataAccessException e) {
	        // Si ocurre un error en el acceso a la base de datos
	        return "Error al guardar la transacción: " + e.getMessage();
	    } catch (Exception e) {
	        // Cualquier otro error inesperado
	        return "Error inesperado: " + e.getMessage();
	    }
	}
}

