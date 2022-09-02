package com.mapledoum.pointscalc.entity;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Transaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id")
    private Long id;

    @Column(name="cust_id")
    private Long custId;

    @Column(name = "trans_date")
    private LocalDate transDate;

    @Column(name = "trans_amount")
    private BigDecimal transAmounnt;



}
