package com.mapledoum.pointscalc.repository;


import com.mapledoum.pointscalc.entity.Customer;
import com.mapledoum.pointscalc.entity.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestPropertySource(properties = "spring.config.location=classpath:/application-test.properties")
public class TransactionRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository repository;


    @Test
    public void should_find_after_save() {
        Transaction subject = new Transaction();
        Assertions.assertNull(subject.getId());
        entityManager.persist(subject);
        Iterable result = repository.findAll();
        Assertions.assertNotNull(subject.getId());
        assertThat(result).hasSize(1).contains(subject);
    }
}
