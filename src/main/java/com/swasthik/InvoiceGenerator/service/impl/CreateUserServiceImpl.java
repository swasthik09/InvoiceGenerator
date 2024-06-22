package com.swasthik.InvoiceGenerator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swasthik.InvoiceGenerator.model.CreateInvoiceResponse;
import com.swasthik.InvoiceGenerator.model.CustomerInvoice;
import com.swasthik.InvoiceGenerator.model.CustomerRequest;
import com.swasthik.InvoiceGenerator.model.InvoiceStatus;
import com.swasthik.InvoiceGenerator.service.CreateUserInvoiceService;
import com.swasthik.InvoiceGenerator.repo.UserRepository;

@Service
public class CreateUserServiceImpl implements CreateUserInvoiceService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public CreateInvoiceResponse createUserInvoice(CustomerRequest req) {
		CustomerInvoice customerInvoice = new CustomerInvoice();
		customerInvoice.setAmount(req.getAmount());
		customerInvoice.setDue_date(req.getDueDate());
		customerInvoice.setPaid_amount(0.0);
		customerInvoice.setStatus(InvoiceStatus.PENDING);
		CustomerInvoice response = userRepository.save(customerInvoice);
		CreateInvoiceResponse createInvoiceResponse = new CreateInvoiceResponse();
		createInvoiceResponse.setId(String.valueOf(response.getId()));
		return createInvoiceResponse;
	}

}
