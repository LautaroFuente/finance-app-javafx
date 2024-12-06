package com.finance_app.finance_app.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.DTO.TransactionForListDTO;
import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.utils.SessionManager;

@Service
public class TransactionListDataService {

	@Autowired
	private TransactionService transactionService;
	
	public List<TransactionForListDTO> getTransactionsForListWallet(){
		try {
        	// Obtener el usuario con la sesion activa
        	User user = SessionManager.getInstance().getUser();
        	
        	// Si existe obtener la lista de transacciones
        	if(user != null) {
        		
        		// Obtener las transacciones
        		List<Object[]> list = this.transactionService.getAllTransactionsWithNameCategory(user.getId());
        		
        		List<TransactionForListDTO> result = new ArrayList<TransactionForListDTO>();
        		
        		// Recorrer las transacciones y crear el objeto DTO para agregar a la lista resultado
        		for(Object[] data : list) {
        			String type = (String) data[0];
        			BigDecimal amount = (BigDecimal) data[1];
        		    String nameCategory = (String) data[2];
        		    LocalDateTime date = (LocalDateTime) data[3];

        		    double amountDouble = amount.doubleValue();
        		    
        		    // Crear objeto y agregar a la lista a devolver
        		    TransactionForListDTO transaction = new TransactionForListDTO(type, amountDouble, nameCategory, date);
        		    result.add(transaction);
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
