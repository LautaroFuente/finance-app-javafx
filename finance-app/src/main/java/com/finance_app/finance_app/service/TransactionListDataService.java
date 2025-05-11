package com.finance_app.finance_app.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.DTO.TransactionForListDTO;
import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.enums.TransactionType;
import com.finance_app.finance_app.utils.SessionManager;

@Service
public class TransactionListDataService {

	@Autowired
	private TransactionService transactionService;
	
	private int totalPagesByLastQuery;
	
	public List<TransactionForListDTO> getTransactionsForListWallet(int from, int to){
		try {
        	// Obtener el usuario con la sesion activa
        	User user = SessionManager.getInstance().getUser();
        	
        	// Si existe obtener la lista de transacciones
        	if(user != null) {
        		
        		// Crear Paginador
        		Pageable pageable = PageRequest.of(from, to);
        		// Obtener las transacciones
        		Page<Object[]> resultPage = this.transactionService.getAllTransactionsWithNameCategory(user.getId(), pageable);
        		
        		// Asignar a lista
        		List<Object[]> list = resultPage.getContent();
        		
        		// Actualizar total de paginas de la ultima consulta
        		this.setTotalPagesByLastQuery(resultPage.getTotalPages());
        		
        		List<TransactionForListDTO> result = new ArrayList<TransactionForListDTO>();
        		
        		// Recorrer las transacciones y crear el objeto DTO para agregar a la lista resultado
        		for(Object[] data : list) {
        			String type = ((TransactionType) data[0]).name();
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

	public int getTotalPagesByLastQuery() {
		return totalPagesByLastQuery;
	}

	public void setTotalPagesByLastQuery(int totalPagesByLastQuery) {
		this.totalPagesByLastQuery = totalPagesByLastQuery;
	}
	
	
}
