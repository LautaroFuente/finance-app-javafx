package com.finance_app.finance_app.validation;

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
}
