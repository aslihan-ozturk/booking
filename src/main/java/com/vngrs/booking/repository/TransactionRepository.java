package com.vngrs.booking.repository;

import com.vngrs.booking.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long > {

    Transaction getTransactionByAppointmentIdAndTransactionType(Long appointmentId, String transactionType);

}
