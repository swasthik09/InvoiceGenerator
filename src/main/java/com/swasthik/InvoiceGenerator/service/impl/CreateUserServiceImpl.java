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
public class CreateUserServiceImpl implements CreateUserInvoiceService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public CreateInvoiceResponse createUserInvoice(CustomerRequest request) {
		CreateInvoiceResponse createInvoiceResponse = new CreateInvoiceResponse();
		try {
			if (request != null && request.getAmount() != null && request.getDueDate() != null) {
				CustomerInvoice customerInvoice = new CustomerInvoice();
				customerInvoice.setAmount(request.getAmount());
				customerInvoice.setDue_date(request.getDueDate());
				customerInvoice.setPaid_amount(0.0);
				customerInvoice.setStatus(InvoiceStatus.PENDING);
				CustomerInvoice response = userRepository.save(customerInvoice);
				if (response != null && response.getId() != null) {
					createInvoiceResponse = new CreateInvoiceResponse();
					createInvoiceResponse.setId(String.valueOf(response.getId()));
				}
			} else {
				createInvoiceResponse.setResponseMessage("Invalid request Param");
			}
		} catch (Exception e) {
			createInvoiceResponse.setResponseMessage("Unexpected Error Please try after some time");
		}
		return createInvoiceResponse;
	}

}
