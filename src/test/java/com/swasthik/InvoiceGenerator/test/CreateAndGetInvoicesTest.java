package com.swasthik.InvoiceGenerator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swasthik.InvoiceGenerator.model.CreateInvoiceResponse;
import com.swasthik.InvoiceGenerator.model.CustomerInvoice;
import com.swasthik.InvoiceGenerator.model.CustomerRequest;
import com.swasthik.InvoiceGenerator.model.InvoiceStatus;
import com.swasthik.InvoiceGenerator.model.MakePaymentRequest;
import com.swasthik.InvoiceGenerator.model.UserInvoiceResponse;
import com.swasthik.InvoiceGenerator.repo.UserRepository;
import com.swasthik.InvoiceGenerator.service.impl.CreateUserInvoiceServiceImpl;
import com.swasthik.InvoiceGenerator.service.impl.GetAllUserInvoicesServiceImpl;


@ExtendWith(MockitoExtension.class)
class createGetInvoicesTests {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private CreateUserInvoiceServiceImpl invoiceService;

	private CustomerRequest customerRequest;

	@InjectMocks
	private GetAllUserInvoicesServiceImpl getInvoiceService;

	private CustomerInvoice customerInvoice1;
	private CustomerInvoice customerInvoice2;
	private MakePaymentRequest makePaymentRequest;

	/**
	 * this method creates mock data sets to test service before the testing
	 */
	@BeforeEach
	public void setUp() {
		customerRequest = new CustomerRequest();
		customerRequest.setAmount(100.0);
		customerRequest.setDueDate("2023-12-31");

		customerInvoice1 = new CustomerInvoice();
		customerInvoice1.setId(1L);
		customerInvoice1.setAmount(100.0);
		customerInvoice1.setDue_date("2023-12-31");
		customerInvoice1.setPaid_amount(50.0);
		customerInvoice1.setStatus(InvoiceStatus.PENDING);

		customerInvoice2 = new CustomerInvoice();
		customerInvoice2.setId(2L);
		customerInvoice2.setAmount(200.0);
		customerInvoice2.setDue_date("2024-01-31");
		customerInvoice2.setPaid_amount(200.0);
		customerInvoice2.setStatus(InvoiceStatus.PAID);

		makePaymentRequest = new MakePaymentRequest();
		makePaymentRequest.setAmount(50.0);
	}

	/**
	 *  this test method is used to test create invoice service class
	 * @throws Exception 
	 */
	@Test
	public void testCreateUserInvoice() {
		CustomerInvoice savedInvoice = new CustomerInvoice();
		savedInvoice.setId(1L); // Assuming ID is of type Long
		when(userRepository.save(any(CustomerInvoice.class))).thenReturn(savedInvoice);
		CreateInvoiceResponse response = invoiceService.createUserInvoice(customerRequest);
		verify(userRepository, times(1)).save(any(CustomerInvoice.class));
		assertEquals("1", response.getId());
	}

	/**
	 * this test method is used to test get all invoice service class
	 */
	@Test
	public void testGetAllUserInvoices() {
		List<CustomerInvoice> invoices = Arrays.asList(customerInvoice1, customerInvoice2);
		when(userRepository.findAll()).thenReturn(invoices);
		List<UserInvoiceResponse> responses = getInvoiceService.getAllUserInvoices();
		verify(userRepository, times(1)).findAll();
		assertEquals(2, responses.size());

		UserInvoiceResponse response1 = responses.get(0);
		assertEquals(1L, response1.getId().longValue());
		assertEquals(Double.valueOf(100.0), response1.getAmount());
		assertEquals("2023-12-31", response1.getDue_date());
		assertEquals(Double.valueOf(50.0), response1.getPaid_amount());
		assertEquals(InvoiceStatus.PENDING, response1.getStatus());

		UserInvoiceResponse response2 = responses.get(1);
		assertEquals(2L, response2.getId().longValue());
		assertEquals(Double.valueOf(200.0), response2.getAmount());
		assertEquals("2024-01-31", response2.getDue_date());
		assertEquals(Double.valueOf(200.0), response2.getPaid_amount());
		assertEquals(InvoiceStatus.PAID, response2.getStatus());
	}

}
