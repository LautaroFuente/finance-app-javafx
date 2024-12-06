package com.finance_app.finance_app.DTO;

import java.time.LocalDateTime;

public class TransactionForListDTO {


    private String type;
	
	private double amount;
	private String nameCategory;
	private LocalDateTime date;
	
	public TransactionForListDTO(String type, double amount, String nameCategory, LocalDateTime date) {
		super();
		this.type = type;
		this.amount = amount;
		this.nameCategory = nameCategory;
		this.date = date;
	}

	public TransactionForListDTO() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNameCategory() {
		return nameCategory;
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
}
