package com.mapledoum.pointscalc.controller;;


import com.mapledoum.pointscalc.entity.Customer;
import com.mapledoum.pointscalc.exceptions.NoRecordFoundException;
import com.mapledoum.pointscalc.model.ApiResponseDTO;
import com.mapledoum.pointscalc.repository.CustomerRepository;
import com.mapledoum.pointscalc.service.IPointsService;
import com.mapledoum.pointscalc.utils.PointsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static com.mapledoum.pointscalc.utils.PointsUtils.CUSTOMER_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


public class CustomersControllerTest {




    private static LocalDate the_date = LocalDate.of(1999,10,10);

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private IPointsService iPointsService;


    @MockBean
    private PointsUtils pointsUtils;


    @InjectMocks
    CustomerController customerController  ;


    @BeforeEach
    public void setUp(){
        iPointsService = mock(IPointsService.class);
        customerRepository = mock(CustomerRepository.class);
        pointsUtils = mock(PointsUtils.class);
        Mockito.when(pointsUtils.convertStToDate( any())).thenReturn(the_date);
        MockitoAnnotations.openMocks(this);


    }


    @Test
    public void shouldReturnTotalPointsForSingleMonth() throws Exception {

        int total = 100;
         Customer customer = new Customer();
        customer.setCustID(9999L);
        ApiResponseDTO responseDTO = ApiResponseDTO.builder().total(total).build();
        given(iPointsService.calcPointsForMonth(anyLong(), any())).willReturn(responseDTO);
        ResponseEntity<ApiResponseDTO> res = customerController.calcPointsByMonth(customer.getCustID(), the_date.toString());
        assertEquals(res.getStatusCode().value(), 200);

    }

    @Test
    public void shouldReturnTotalPointsForRange() throws Exception {

        int total = 100;
        Customer customer = new Customer();
        customer.setCustID(9999L);
        ApiResponseDTO responseDTO = ApiResponseDTO.builder().total(total).build();

        given(customerRepository.findCustomerByCustID(9999L)).willReturn(customer);
        given(iPointsService.calcPointsTotalFromTo(anyLong(), any(), any())).willReturn(responseDTO);


        ResponseEntity<ApiResponseDTO> res = customerController.calcPointsRange(customer.getCustID(), the_date.toString(), the_date.toString());
        assertEquals(res.getStatusCode().value(), 200);
        assertEquals(res.getBody().getTotal(), total);

    }

    @Test
    public void shouldReturnTotalPointsForRangeShouldThrow() throws Exception {

        int total = 100;
        Customer customer = new Customer();
        customer.setCustID(9999L);

        given(customerRepository.findCustomerByCustID(9999L)).willReturn(customer);
        given(iPointsService.calcPointsTotalFromTo(anyLong(), any(), any())).willThrow(new NoRecordFoundException(CUSTOMER_NOT_EXISTS));

        NoRecordFoundException thrown = assertThrows(
                NoRecordFoundException.class,
                () ->customerController.calcPointsRange(customer.getCustID(), the_date.toString(), the_date.toString()),
                "Expected calcPointsRange response to throw, but it didn't"
        );


        assertTrue(thrown.getMessage().contains(CUSTOMER_NOT_EXISTS));

    }





}
