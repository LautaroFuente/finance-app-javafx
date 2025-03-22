package com.finance_app.finance_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class FinanceAppSpring {

    public static void main(String[] args) {
    	
    	//Dotenv dotenv = Dotenv.load();
    	//System.setProperty("DB_URL", dotenv.get("DB_URL"));
    	//System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
    	//System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    	        
        SpringApplication.run(FinanceAppSpring.class, args);
        
        System.out.println("Backend funcionando");

    }
}
