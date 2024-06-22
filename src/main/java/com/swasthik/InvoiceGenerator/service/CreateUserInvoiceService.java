package com.swasthik.InvoiceGenerator.service;

import com.swasthik.InvoiceGenerator.model.CreateInvoiceResponse;
import com.swasthik.InvoiceGenerator.model.CustomerRequest;

public interface CreateUserInvoiceService {
CreateInvoiceResponse createUserInvoice(CustomerRequest request);
}
