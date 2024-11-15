package com.finance_app.finance_app.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.finance_app.finance_app.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private BigDecimal amount;
	private String description;
	private LocalDateTime date;
	
	@Enumerated(EnumType.STRING)
    private TransactionType type;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	public Transaction(User user, BigDecimal amount, String description, LocalDateTime date, TransactionType type,
			Category category) {
		super();
		this.user = user;
		this.amount = amount;
		this.description = description;
		this.date = date;
		this.type = type;
		this.category = category;
	}

	public Transaction() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getId() {
		return id;
	}
	
}
