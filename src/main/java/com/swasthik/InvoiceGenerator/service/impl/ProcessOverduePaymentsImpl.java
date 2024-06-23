package com.swasthik.InvoiceGenerator.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swasthik.InvoiceGenerator.model.CreateInvoiceResponse;
import com.swasthik.InvoiceGenerator.model.CustomerInvoice;
import com.swasthik.InvoiceGenerator.model.CustomerRequest;
import com.swasthik.InvoiceGenerator.model.InvoiceStatus;
import com.swasthik.InvoiceGenerator.model.ProcessOverduePaymentsRequest;
import com.swasthik.InvoiceGenerator.repo.UserRepository;
import com.swasthik.InvoiceGenerator.service.CreateUserInvoiceService;
import com.swasthik.InvoiceGenerator.service.ProcessOverduePayments;

@Service
public class ProcessOverduePaymentsImpl implements ProcessOverduePayments {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CreateUserInvoiceService createUserInvoiceService;

	@Override
	public List<CreateInvoiceResponse> processOverduePayments(ProcessOverduePaymentsRequest request) {
		List<CustomerInvoice> pendingInvoices = userRepository.findAllInvoiceByStatus(InvoiceStatus.PENDING);
		List<CreateInvoiceResponse> Response = new ArrayList<>();
		if (!pendingInvoices.isEmpty()) {
			for (CustomerInvoice customerInvoice : pendingInvoices) {
				double balance = customerInvoice.getAmount() - customerInvoice.getPaid_amount();
				CustomerRequest customerRequest = new CustomerRequest();
				customerRequest.setAmount(balance + request.getLateFee());
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				customerRequest.setDueDate(LocalDateTime.now().plusDays(request.getOverDueDays()).format(format));
				if (customerInvoice.getPaid_amount() == 0) {
					customerInvoice.setStatus(InvoiceStatus.VOID);
				} else {
					customerInvoice.setStatus(InvoiceStatus.PAID);
				}
				userRepository.save(customerInvoice);
				CreateInvoiceResponse userInvoiceResponse = null;
				userInvoiceResponse = createUserInvoiceService.createUserInvoice(customerRequest);
				userInvoiceResponse
						.setResponseMessage("new Invoice Created with total amount: " + customerRequest.getAmount());
				Response.add(userInvoiceResponse);
			}
		}
		return Response;
	}

}
