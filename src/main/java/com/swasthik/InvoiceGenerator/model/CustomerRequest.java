package com.swasthik.InvoiceGenerator.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CustomerRequest {

	@JsonAlias(value = "due_date")
	private String dueDate;
	
	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String duedate) {
		this.dueDate = duedate;
	}
}
