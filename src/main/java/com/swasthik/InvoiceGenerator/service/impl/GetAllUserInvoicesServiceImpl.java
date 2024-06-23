package com.swasthik.InvoiceGenerator.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swasthik.InvoiceGenerator.model.CustomerInvoice;
import com.swasthik.InvoiceGenerator.model.UserInvoiceResponse;
import com.swasthik.InvoiceGenerator.repo.UserRepository;
import com.swasthik.InvoiceGenerator.service.GetAllUserInvoices;

@Service
public class GetAllUserInvoicesServiceImpl implements GetAllUserInvoices {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserInvoiceResponse> getAllUserInvoices() {
		List<UserInvoiceResponse> response = new ArrayList<>();
		try {
			List<CustomerInvoice> invoices = userRepository.findAll();
			for (CustomerInvoice invoice : invoices) {
				UserInvoiceResponse userInvoiceResponse = new UserInvoiceResponse();
				userInvoiceResponse.setAmount(invoice.getAmount());
				userInvoiceResponse.setDue_date(invoice.getDue_date());
				userInvoiceResponse.setId(invoice.getId());
				userInvoiceResponse.setPaid_amount(invoice.getPaid_amount());
				userInvoiceResponse.setStatus(invoice.getStatus());
				response.add(userInvoiceResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
