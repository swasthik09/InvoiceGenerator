package com.swasthik.InvoiceGenerator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swasthik.InvoiceGenerator.model.CustomerInvoice;
import com.swasthik.InvoiceGenerator.model.InvoiceStatus;
import com.swasthik.InvoiceGenerator.model.MakePaymentRequest;
import com.swasthik.InvoiceGenerator.model.UserInvoiceResponse;
import com.swasthik.InvoiceGenerator.repo.UserRepository;
import com.swasthik.InvoiceGenerator.service.impl.MakePaymentForInvoiceImpl;

@ExtendWith(MockitoExtension.class)
public class MakePaymentForInvoiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private MakePaymentForInvoiceImpl makePaymentInvoiceService;

	private CustomerInvoice customerInvoice;

	private MakePaymentRequest makePaymentRequest;

	/**
	 * this method creates mock data sets to test service before the testing
	 */
	@BeforeEach
	public void setUp() {
		customerInvoice = new CustomerInvoice();
		customerInvoice.setId(1L);
		customerInvoice.setAmount(100.0);
		customerInvoice.setPaid_amount(50.0);
		customerInvoice.setStatus(InvoiceStatus.PENDING);

		makePaymentRequest = new MakePaymentRequest();
		makePaymentRequest.setAmount(50.0);
	}

	/**
	 * test scenario when Invoice is fully paid
	 */
	@Test
    public void testMakePayment_PaidInFull() {
        when(userRepository.findById(1)).thenReturn(Optional.of(customerInvoice));
        makePaymentRequest.setAmount(50.0); 
        UserInvoiceResponse response = makePaymentInvoiceService.makePayment("1", makePaymentRequest);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(any(CustomerInvoice.class));
        assertEquals(InvoiceStatus.PAID, response.getStatus());
        assertEquals(100.0, response.getAmount());
        assertEquals(100.0, response.getPaid_amount());
    }

	/**
	 * test scenario when Invoice is not found
	 */
	@Test
    public void testMakePayment_InvoiceNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        UserInvoiceResponse response = makePaymentInvoiceService.makePayment("1", makePaymentRequest);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(0)).save(any(CustomerInvoice.class));
        assertNull(response);
    }

	/**
	 * test scenario when Invoice is partially paid
	 */
	@Test
    public void testMakePayment_PartialPayment() {
        when(userRepository.findById(1)).thenReturn(Optional.of(customerInvoice));
        makePaymentRequest.setAmount(25.0);	
        UserInvoiceResponse response = makePaymentInvoiceService.makePayment("1", makePaymentRequest);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(any(CustomerInvoice.class));
        assertEquals(InvoiceStatus.PENDING, response.getStatus());
        assertEquals(100.0, response.getAmount());
        assertNotEquals(100.0, response.getPaid_amount());
    }

}
