package integration.com.mapledoum.pointscalc.repository;


import com.mapledoum.pointscalc.PointsApplication;
import com.mapledoum.pointscalc.entity.Transaction;
import com.mapledoum.pointscalc.repository.TransactionRepository;
import integration.com.mapledoum.pointscalc.config.H2JpaConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {PointsApplication.class, H2JpaConfig.class})
@TestPropertySource(properties = "spring.config.location=classpath:/application-test.properties")
public class TransactionRepositoryTest {


    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp(){
        transactionRepository.deleteAll();;
    }
    @AfterEach
    public void tearDown(){
        transactionRepository.deleteAll();;
    }

    @Test
    public void testFindByIdAndTransDate(){

        LocalDate from = LocalDate.of(1999,10,10);

        Transaction test = new Transaction();
        test.setTransAmounnt(new BigDecimal(100L));
        test.setTransDate(from);
        test.setCustId(1000L);
        Transaction saved = transactionRepository.save(test);
        assertNotNull(transactionRepository.findAllByCustIdAndTransDateIs(saved.getCustId(), from));

    }

    @Test
    public void testFindByIdAndTransDateRange(){

        LocalDate from = LocalDate.of(1999,10,10);
        LocalDate to = LocalDate.of(2020,10,10);
        Transaction test = new Transaction();
        test.setTransAmounnt(new BigDecimal(100L));
        test.setTransDate(from);
        test.setCustId(1000L);

        Transaction test2 = new Transaction();
        test2.setTransAmounnt(new BigDecimal(100L));
        test2.setTransDate(to);
        test2.setCustId(1000L);

        Transaction saved = transactionRepository.save(test);
        Transaction saved2 = transactionRepository.save(test2);
        assertNotNull(transactionRepository.findAllByCustIdAndTransDateBetween(saved.getCustId(), from, to));
        assertEquals(transactionRepository.findAllByCustIdAndTransDateBetween(saved.getCustId(), from, to).size(),2);

    }
}
