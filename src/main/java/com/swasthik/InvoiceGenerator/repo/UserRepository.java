package com.swasthik.InvoiceGenerator.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swasthik.InvoiceGenerator.model.CustomerInvoice;
import com.swasthik.InvoiceGenerator.model.InvoiceStatus;

@Repository
public interface UserRepository extends JpaRepository<CustomerInvoice, Integer> {

	@Query("select c from customerinvoice c where c.status = :status")
	List<CustomerInvoice> findAllInvoiceByStatus(@Param("status") InvoiceStatus status);
}
