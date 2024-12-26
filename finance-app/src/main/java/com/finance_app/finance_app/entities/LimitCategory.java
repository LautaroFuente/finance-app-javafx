package com.finance_app.finance_app.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class LimitCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	private BigDecimal max_limit;
	private LocalDateTime date_from;
	private LocalDateTime date_to;
	private BigDecimal total_expense;
	
	public LimitCategory(User user, Category category, BigDecimal max_limit, LocalDateTime date_from,
			LocalDateTime date_to) {
		super();
		this.user = user;
		this.category = category;
		this.max_limit = max_limit;
		this.date_from = date_from;
		this.date_to = date_to;
		this.total_expense = new BigDecimal(0.00);
	}

	public LimitCategory() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getMax_limit() {
		return max_limit;
	}

	public void setMax_limit(BigDecimal max_limit) {
		this.max_limit = max_limit;
	}

	public LocalDateTime getDate_from() {
		return date_from;
	}

	public void setDate_from(LocalDateTime date_from) {
		this.date_from = date_from;
	}

	public LocalDateTime getDate_to() {
		return date_to;
	}

	public void setDate_to(LocalDateTime date_to) {
		this.date_to = date_to;
	}

	public Long getId() {
		return id;
	}

	public BigDecimal getTotal_expense() {
		return total_expense;
	}

	public void setTotal_expense(BigDecimal total_expense) {
		this.total_expense = total_expense;
	}	
	
}
