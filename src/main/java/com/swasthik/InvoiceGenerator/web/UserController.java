package com.swasthik.InvoiceGenerator.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swasthik.InvoiceGenerator.model.CreateInvoiceResponse;
import com.swasthik.InvoiceGenerator.model.CustomerRequest;
import com.swasthik.InvoiceGenerator.model.MakePaymentRequest;
import com.swasthik.InvoiceGenerator.model.ProcessOverduePaymentsRequest;
import com.swasthik.InvoiceGenerator.model.UserInvoiceResponse;
import com.swasthik.InvoiceGenerator.service.CreateUserInvoiceService;
import com.swasthik.InvoiceGenerator.service.GetAllUserInvoices;
import com.swasthik.InvoiceGenerator.service.MakePaymentForInvoice;
import com.swasthik.InvoiceGenerator.service.ProcessOverduePayments;

@RestController
@RequestMapping(value = "/invoices")
public class UserController {

	@Autowired
	private CreateUserInvoiceService createUserService;

	@Autowired
	private GetAllUserInvoices getAllUserInvoices;

	@Autowired
	private MakePaymentForInvoice makePaymentForInvoice;

	@Autowired
	private ProcessOverduePayments processOverduePayments;

	@GetMapping
	private List<UserInvoiceResponse> getAllUserInvoices() {
		return getAllUserInvoices.getAllUserInvoices();
	}

	@PostMapping
	private CreateInvoiceResponse createUserInvoice(@RequestBody CustomerRequest customerRequest) {
		return createUserService.createUserInvoice(customerRequest);
	}

	@PostMapping(value = "/{userid}/payments")
	private UserInvoiceResponse makePaymentForInvoice(@PathVariable("userid") String userid,
			@RequestBody MakePaymentRequest request) {
		return makePaymentForInvoice.makePayment(userid, request);
	}

	@PostMapping(value = "/process-overdue")
	private List<CreateInvoiceResponse> processOverDuePayments(@RequestBody ProcessOverduePaymentsRequest request) {
		return processOverduePayments.processOverduePayments(request);
	}
}
