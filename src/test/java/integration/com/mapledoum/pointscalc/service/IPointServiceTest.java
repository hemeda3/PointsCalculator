package integration.com.mapledoum.pointscalc.service;


import com.mapledoum.pointscalc.PointsApplication;
import com.mapledoum.pointscalc.entity.Customer;
import com.mapledoum.pointscalc.entity.Transaction;
import com.mapledoum.pointscalc.model.ApiResponseDTO;
import com.mapledoum.pointscalc.repository.CustomerRepository;
import com.mapledoum.pointscalc.repository.TransactionRepository;
import com.mapledoum.pointscalc.service.IPointsService;
import integration.com.mapledoum.pointscalc.config.H2JpaConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@SpringBootTest(classes = {PointsApplication.class, H2JpaConfig.class})
@TestPropertySource(properties = "spring.config.location=classpath:/application-test.properties")
public class IPointServiceTest {


    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    IPointsService iPointsService;

    @BeforeEach
    public void setUp(){
        transactionRepository.deleteAll();;
    }
    @AfterEach
    public void tearDown(){
        transactionRepository.deleteAll();;
    }
    @Test
    public void testCalcPointsForMonth(){


        Customer customer = customerRepository.save(new Customer());

        LocalDate localDate = LocalDate.of(1999,9,9);
        Long cus_id = customer.getCustID();
        Transaction transaction = new Transaction();
        transaction.setTransAmounnt(new BigDecimal(120L));
        transaction.setCustId(customer.getCustID());
        transaction.setTransDate(localDate);

        Transaction transaction1 = transactionRepository.save(transaction);
        ApiResponseDTO apiResponseDTO =  iPointsService.calcPointsForMonth(cus_id, localDate);
        assertEquals(apiResponseDTO.getTotal(), 110);
    }
    @Test
    public void testCalcPointsTotalFromTo(){
        Customer customer = customerRepository.save(new Customer());

        LocalDate from = LocalDate.of(1999,9,9);
        LocalDate to = LocalDate.of(1999,9,9);
        Long cus_id = customer.getCustID();
        Transaction transaction = new Transaction();
        transaction.setTransAmounnt(new BigDecimal(120L));
        transaction.setCustId(cus_id);
        transaction.setTransDate(from);

        Transaction transaction2 = new Transaction();
        transaction2.setTransAmounnt(new BigDecimal(120L));
        transaction2.setCustId(cus_id);
        transaction2.setTransDate(to);

        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        ApiResponseDTO apiResponseDTO =  iPointsService.calcPointsTotalFromTo(cus_id, from,to);
        assertEquals(apiResponseDTO.getTotal(), 220);
    }

}
