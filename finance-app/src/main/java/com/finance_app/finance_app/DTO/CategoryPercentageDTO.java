package com.finance_app.finance_app.DTO;

public class CategoryPercentageDTO {

	private String name;
	private double percentage;
	
	public CategoryPercentageDTO(String name, double percentage) {
		super();
		this.name = name;
		this.percentage = percentage;
	}

	public CategoryPercentageDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
}
