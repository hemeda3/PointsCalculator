package com.mapledoum.pointscalc.service;


import com.mapledoum.pointscalc.controller.CustomerController;
import com.mapledoum.pointscalc.entity.Customer;
import com.mapledoum.pointscalc.entity.Transaction;
import com.mapledoum.pointscalc.model.ApiResponseDTO;
import com.mapledoum.pointscalc.repository.CustomerRepository;
import com.mapledoum.pointscalc.repository.TransactionRepository;
import com.mapledoum.pointscalc.utils.PointsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


public class IPointServiceTest {



    private static LocalDate the_date = LocalDate.of(1999,10,10);

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    TransactionRepository transactionRepositoryMock;


    @InjectMocks
    private PointsServiceImpl iPointsService;


    @MockBean
    private PointsUtils pointsUtils;


    @BeforeEach
    public void setUp(){
        customerRepository = mock(CustomerRepository.class);
        transactionRepositoryMock = mock(TransactionRepository.class);
        pointsUtils = mock(PointsUtils.class);
        Mockito.when(pointsUtils.convertStToDate( any())).thenReturn(the_date);
        MockitoAnnotations.openMocks(this);
    }





    @Test
    public void testCalcPointsForMonth(){
        LocalDate localDate = LocalDate.of(1999,9,9);
        Long cus_id = 1000L;
        Transaction transaction = new Transaction();
        transaction.setTransAmounnt(new BigDecimal(120L));
        transaction.setCustId(cus_id);
        transaction.setTransDate(localDate);
        given(transactionRepositoryMock.findAllByCustIdAndTransDateIs(anyLong(), any())).willReturn(transaction);
        given(customerRepository.findCustomerByCustID(anyLong())).willReturn(Customer.builder().custID(cus_id).build());

        ApiResponseDTO apiResponseDTO =  iPointsService.calcPointsForMonth(cus_id, localDate);
        assertEquals(apiResponseDTO.getTotal(), 110);
    }
    @Test
    public void testCalcPointsTotalFromTo(){
        LocalDate localDate = LocalDate.of(1999,9,9);
        Long cus_id = 1000L;
        Transaction transaction = new Transaction();
        transaction.setTransAmounnt(new BigDecimal(120L));
        transaction.setCustId(100L);
        transaction.setTransDate(localDate);
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        transactionList.add(transaction);
        given(transactionRepositoryMock.findAllByCustIdAndTransDateBetween(anyLong(), any(), any())).willReturn(transactionList);
        given(customerRepository.findCustomerByCustID(anyLong())).willReturn(Customer.builder().custID(cus_id).build());

        ApiResponseDTO apiResponseDTO =  iPointsService.calcPointsTotalFromTo(cus_id, localDate,localDate);
        assertEquals(apiResponseDTO.getTotal(), 220);
    }

}
