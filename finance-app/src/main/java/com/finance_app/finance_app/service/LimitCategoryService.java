package com.finance_app.finance_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.LimitCategory;
import com.finance_app.finance_app.entities.Transaction;
import com.finance_app.finance_app.repository.LimitCategoryRepository;

@Service
public class LimitCategoryService {

	@Autowired
	private LimitCategoryRepository limitCategoryRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	public LimitCategory addLimitCategory(LimitCategory limitCategory) {
		return this.limitCategoryRepository.save(limitCategory);
	}
	
	public void deleteLimitCategory (LimitCategory limitCategory) {
		this.limitCategoryRepository.delete(limitCategory);
	}
	
	public void updateTotalIfExistLimitVigent(Transaction transaction) {
		
		// Verificar si existe algun limite vigente en la categoria y en cuyo caso actualizar el total
		List<LimitCategory> activeLimits = limitCategoryRepository.findActiveLimitsForCategory(
		        transaction.getUser().getId(),
		        transaction.getCategory().getId(),
		        transaction.getDate()
		    );
		
		// Si existen límites activos para esa categoría
	    if (!activeLimits.isEmpty()) {
	        // Recorrer cada límite para ver si se excede el máximo
	        for (LimitCategory limit : activeLimits) {
	        	limit.setTotal_expense(limit.getTotal_expense().add(transaction.getAmount()));;
	            limitCategoryRepository.save(limit);

	            // Verificar si el total gastado ha superado el límite
	            if (limit.getTotal_expense().compareTo(limit.getMax_limit()) > 0) {
	            	
	            	// Si se supera el limite enviar notificación
	                this.notificationService.addNotification(
	                		transaction.getUser(), 
	                		"Se ha exedido el limite de: " + limit.getMax_limit() + " de la categoria: " + transaction.getCategory().getName()
	                );
	            }
	            
	        }
	    }
	}
}
