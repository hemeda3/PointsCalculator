package com.mapledoum.pointscalc.repository;


import com.mapledoum.pointscalc.controller.CustomerController;
import com.mapledoum.pointscalc.entity.Customer;
import com.mapledoum.pointscalc.service.IPointsService;
import com.mapledoum.pointscalc.utils.PointsUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
@DataJpaTest
@TestPropertySource(properties = "spring.config.location=classpath:/application-test.properties")

public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    private static LocalDate the_date = LocalDate.of(1999,10,10);


    @Autowired
    private CustomerRepository repository;


    @BeforeEach
    public void setUp(){



    }


    @Test
    public void should_find_after_save() {
        Customer customer = new Customer();
        Assertions.assertNull(customer.getCustID());
        entityManager.persist(customer);
        entityManager.persist(customer);
        Iterable result = repository.findAll();
        Assertions.assertNotNull(customer.getCustID());

        assertThat(result).hasSize(1).contains(customer);
    }
}
