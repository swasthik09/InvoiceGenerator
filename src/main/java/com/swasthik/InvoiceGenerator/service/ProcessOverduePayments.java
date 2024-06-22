package com.swasthik.InvoiceGenerator.service;

import java.util.List;

import com.swasthik.InvoiceGenerator.model.CreateInvoiceResponse;
import com.swasthik.InvoiceGenerator.model.ProcessOverduePaymentsRequest;

public interface ProcessOverduePayments {
	List<CreateInvoiceResponse> processOverduePayments(ProcessOverduePaymentsRequest request);
}
