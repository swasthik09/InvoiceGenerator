package com.swasthik.InvoiceGenerator.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swasthik.InvoiceGenerator.model.CustomerInvoice;
import com.swasthik.InvoiceGenerator.model.InvoiceStatus;
import com.swasthik.InvoiceGenerator.model.MakePaymentRequest;
import com.swasthik.InvoiceGenerator.model.UserInvoiceResponse;
import com.swasthik.InvoiceGenerator.repo.UserRepository;
import com.swasthik.InvoiceGenerator.service.MakePaymentForInvoice;

@Service
public class MakePaymentForInvoiceImpl implements MakePaymentForInvoice {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserInvoiceResponse makePayment(String id, MakePaymentRequest request) {
		Optional<CustomerInvoice> customerInvoice = userRepository.findById(Integer.parseInt(id));
		CustomerInvoice Invoice = null;
		UserInvoiceResponse response = null;
		InvoiceStatus status = null;
		if (customerInvoice.isPresent()
				&& customerInvoice.get().getAmount() != customerInvoice.get().getPaid_amount()) {
			Invoice = customerInvoice.get();
			double total = Invoice.getAmount();
			double alreadyGiven = Invoice.getPaid_amount() + request.getAmount();
			if (alreadyGiven == total) {
				status = InvoiceStatus.PAID;
			} else {
				status = InvoiceStatus.PENDING;
			}
			Invoice.setStatus(status);
			Invoice.setPaid_amount(alreadyGiven);
			userRepository.save(Invoice);
			response = new UserInvoiceResponse();
			response.setAmount(Invoice.getAmount());
			response.setDue_date(Invoice.getDue_date());
			response.setId(Invoice.getId());
			response.setPaid_amount(Invoice.getPaid_amount());
			response.setStatus(status);
		}
		return response;
	}

}
