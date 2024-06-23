package com.swasthik.InvoiceGenerator.model;

public class UserInvoiceResponse {
	
	private Long id;
	
	private String due_date;
	
	private Double paid_amount;
	
	private InvoiceStatus status;
	
	private Double amount;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}


	public Double getPaid_amount() {
		return paid_amount;
	}

	public void setPaid_amount(Double paid_amount) {
		this.paid_amount = paid_amount;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	

}

