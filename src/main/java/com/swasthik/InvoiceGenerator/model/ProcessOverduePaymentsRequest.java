package com.swasthik.InvoiceGenerator.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProcessOverduePaymentsRequest {

	@JsonAlias(value = "late_fee")
	private double lateFee;
	
	@JsonAlias(value = "overdue_days")
	private  long overDueDays;

	public double getLateFee() {
		return lateFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	public long getOverDueDays() {
		return overDueDays;
	}

	public void setOverDueDays(long overDueDays) {
		this.overDueDays = overDueDays;
	}
}
