package com.vngrs.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
@Entity
@Data
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long transactionId;
     private String transactionType; //cancel appointment transaction (C) or save (S) appointment transaction
     private String transactionState; // transaction is active (A) or returned (R)(in the case of cancellation)
     private Double price;
     private Timestamp transactionDate;
     private Long appointmentId;
}
