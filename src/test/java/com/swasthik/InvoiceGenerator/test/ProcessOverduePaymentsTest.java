package com.swasthik.InvoiceGenerator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.swasthik.InvoiceGenerator.model.ProcessOverduePaymentsRequest;
import com.swasthik.InvoiceGenerator.repo.UserRepository;
import com.swasthik.InvoiceGenerator.service.CreateUserInvoiceService;
import com.swasthik.InvoiceGenerator.service.impl.ProcessOverduePaymentsImpl;

@ExtendWith(MockitoExtension.class)
public class ProcessOverduePaymentsTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreateUserInvoiceService createUserInvoiceService;

    @InjectMocks
    private ProcessOverduePaymentsImpl invoiceService; // Assuming the service class is named InvoiceService

    private ProcessOverduePaymentsRequest processOverduePaymentsRequest;

    @BeforeEach
    public void setUp() {
        processOverduePaymentsRequest = new ProcessOverduePaymentsRequest();
        processOverduePaymentsRequest.setLateFee(10.0);
        processOverduePaymentsRequest.setOverDueDays(7);
    }

    @Test
    public void testProcessOverduePayments_NoPendingInvoices() {
        // Given
        when(userRepository.findAllInvoiceByStatus(InvoiceStatus.PENDING)).thenReturn(new ArrayList<>());

        // When
        List<CreateInvoiceResponse> response = invoiceService.processOverduePayments(processOverduePaymentsRequest);

        // Then
        verify(userRepository, times(1)).findAllInvoiceByStatus(InvoiceStatus.PENDING);
        verify(userRepository, never()).save(any(CustomerInvoice.class));
        verify(createUserInvoiceService, never()).createUserInvoice(any(CustomerRequest.class));
        assertTrue(response.isEmpty());
    }

    @Test
    public void testProcessOverduePayments_WithPendingInvoices() {
        // Given
        List<CustomerInvoice> pendingInvoices = new ArrayList<>();
        CustomerInvoice invoice1 = new CustomerInvoice();
        invoice1.setId(1L);
        invoice1.setAmount(100.0);
        invoice1.setPaid_amount(50.0);
        invoice1.setStatus(InvoiceStatus.PENDING);
        pendingInvoices.add(invoice1);

        when(userRepository.findAllInvoiceByStatus(InvoiceStatus.PENDING)).thenReturn(pendingInvoices);
        when(createUserInvoiceService.createUserInvoice(any(CustomerRequest.class))).thenReturn(new CreateInvoiceResponse());

        // When
        List<CreateInvoiceResponse> response = invoiceService.processOverduePayments(processOverduePaymentsRequest);

        // Then
        verify(userRepository, times(1)).findAllInvoiceByStatus(InvoiceStatus.PENDING);
        verify(userRepository, times(1)).save(any(CustomerInvoice.class));
        verify(createUserInvoiceService, times(1)).createUserInvoice(any(CustomerRequest.class));
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }
}

