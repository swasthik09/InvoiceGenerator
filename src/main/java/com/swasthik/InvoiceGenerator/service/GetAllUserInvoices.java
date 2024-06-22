package com.swasthik.InvoiceGenerator.service;

import java.util.List;

import com.swasthik.InvoiceGenerator.model.UserInvoiceResponse;

public interface GetAllUserInvoices {
	List<UserInvoiceResponse> getAllUserInvoices();
}
