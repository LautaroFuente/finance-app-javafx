package com.finance_app.finance_app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.finance_app.finance_app.DTO.CategoryPercentageDTO;
import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.utils.SessionManager;

@Service
public class ChartDataService {
	
	@Autowired
	private TransactionService transactionService;

	public List<CategoryPercentageDTO> getPercentageForCategory(){
		// Calcular inicio y fin del mes actual
		LocalDate now = LocalDate.now();
		LocalDate startOfMonth = now.withDayOfMonth(1);
		LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

		LocalDateTime startDateTime = startOfMonth.atStartOfDay();
		LocalDateTime endDateTime = endOfMonth.atTime(LocalTime.MAX);

        try {
        	// Obtener el usuario con la sesion activa
        	User user = SessionManager.getInstance().getUser();
        	
        	// Si existe obtener los porcentajes
        	if(user != null) {
        		// Obtener los gastado por cada categoria
        		List<Object[]> list = this.transactionService.getPercentageByCategoryForUser(user.getId(), startDateTime, endDateTime);
        		
        		BigDecimal total = BigDecimal.ZERO;
        		// Obtener el total gastado
        		for(Object[] data : list) {
        			BigDecimal amount = (BigDecimal) data[2];
        			total = total.add(amount);
        		}
        		
        		List<CategoryPercentageDTO> result = new ArrayList<CategoryPercentageDTO>();
        		// Calcular los porcentajes y agregar la categoria con su porcentaje a la lista resultado
        		for(Object[] data : list) {
        			String categoryName = (String) data[1];
        		    BigDecimal totalAmount = (BigDecimal) data[2];

        		    BigDecimal percentage = BigDecimal.ZERO;
        		    if (total.compareTo(BigDecimal.ZERO) > 0) {
        		        percentage = totalAmount.multiply(new BigDecimal(100)).divide(total, 2, RoundingMode.HALF_UP);  // Multiplicar por 100 y dividir por el total
        		    }
        		    double percentageDouble = percentage.doubleValue();
        		    
        		    // Crear objeto y agregar a la lista a devolver
        		    CategoryPercentageDTO category = new CategoryPercentageDTO(categoryName, percentageDouble);
        		    result.add(category);
        		}
        		return result;
        		
        	}
        	
        	// Si no existe lanzar excepcion
        	else {
        		throw new AuthenticationException("El usuario no está autenticado.");
        	}
        }catch(AuthenticationException e) {
        	System.out.println(e);
        	return null;
        }
	}
}
