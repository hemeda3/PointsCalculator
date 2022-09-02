package integration.com.mapledoum.pointscalc.repository;


import com.mapledoum.pointscalc.PointsApplication;
import com.mapledoum.pointscalc.entity.Customer;
import com.mapledoum.pointscalc.repository.CustomerRepository;
import integration.com.mapledoum.pointscalc.config.H2JpaConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {PointsApplication.class, H2JpaConfig.class})
@TestPropertySource(properties = "spring.config.location=classpath:/application-test.properties")
public class CustomerRepositoryTest {




    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){
        customerRepository.deleteAll();;
    }
    @AfterEach
    public void tearDown(){
        customerRepository.deleteAll();;
    }

    @Test
    public void testFindCustomer(){


        Customer c = new Customer();
        Customer saved = customerRepository.save(c);
        assertNotNull(customerRepository.findCustomerByCustID(saved.getCustID()));

    }
}
