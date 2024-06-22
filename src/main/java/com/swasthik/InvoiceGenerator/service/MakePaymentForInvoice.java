package com.swasthik.InvoiceGenerator.service;

import com.swasthik.InvoiceGenerator.model.MakePaymentRequest;
import com.swasthik.InvoiceGenerator.model.UserInvoiceResponse;

public interface MakePaymentForInvoice {
	UserInvoiceResponse makePayment(String id , MakePaymentRequest request);
}
