package com.mapledoum.pointscalc.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mapledoum.pointscalc.entity.Transaction;

@Repository
@Transactional
public interface TransactionRepository extends CrudRepository<Transaction,Long> {

    List<Transaction> findAllByCustIdAndTransDateBetween(Long id, LocalDate from, LocalDate to);
    Transaction findAllByCustIdAndTransDateIs(Long id, LocalDate date);
}
