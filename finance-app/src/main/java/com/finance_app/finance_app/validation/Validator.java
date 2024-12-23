package com.finance_app.finance_app.validation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class Validator {

    public static boolean validateUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean validateFields(String username, String email, String password) {
        return validateUsername(username) && validateEmail(email) && validatePassword(password);
    }
    
    public static boolean validateNumericText(String text) {
        try {
            // Convertir el texto a un número
            BigDecimal value = new BigDecimal(text);

            // Verificar que el número sea mayor a cero
            return value.compareTo(BigDecimal.ZERO) > 0;
        } catch (NumberFormatException e) {
            // Si el texto no puede convertirse a un número, es inválido
            return false;
        }
    }
    
    public static boolean validateDate(LocalDateTime date) {
    	
    	if (date == null) {
    	    return false;
    	}
    	
    	LocalDateTime currentDate = LocalDateTime.now();
    	if (date.isBefore(currentDate)) {
    	    return false;
    	}
    	
    	return true;
    	
    }
}
