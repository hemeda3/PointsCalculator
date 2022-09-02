package com.mapledoum.pointscalc.controller;;


import com.mapledoum.pointscalc.entity.Customer;
import com.mapledoum.pointscalc.exceptions.NoRecordFoundException;
import com.mapledoum.pointscalc.model.ApiResponseDTO;
import com.mapledoum.pointscalc.repository.CustomerRepository;
import com.mapledoum.pointscalc.repository.TransactionRepository;
import com.mapledoum.pointscalc.service.IPointsService;
import com.mapledoum.pointscalc.utils.PointsUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Optional;

import static com.mapledoum.pointscalc.utils.PointsUtils.CUSTOMER_NOT_EXISTS;
import static com.mapledoum.pointscalc.utils.PointsUtils.NO_RECORD_FOUND;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private IPointsService iPointsService;

    @MockBean
    private TransactionRepository transactionRepository;



    @TestConfiguration
    static class AdditionalConfig {
        @Bean
        public PointsUtils pointsUtils() {
            return new PointsUtils();
        }
    }
    @Test
    public void shouldReturnTotalPointsForSingleMonth() throws Exception {

        int total = 100;
        Customer customer = new Customer();
        customer.setCustID(9999L);
        ApiResponseDTO responseDTO = ApiResponseDTO.builder().total(total).build();

        given(iPointsService.calcPointsForMonth(anyLong(), any())).willReturn(responseDTO);

        mockMvc.perform(get("/customers/9999/points/2020-04-12"))
                .andExpect(jsonPath("$.total").value(total))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldReturnTotalPointsForRange() throws Exception {

        int total = 100;
        Customer customer = new Customer();
        customer.setCustID(9999L);
        ApiResponseDTO responseDTO = ApiResponseDTO.builder().total(total).build();

        given(customerRepository.findCustomerByCustID(9999L)).willReturn(customer);
        given(iPointsService.calcPointsTotalFromTo(anyLong(), any(), any())).willReturn(responseDTO);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from","2020-04-12");
        params.add("to","2020-04-12");

        mockMvc.perform(get("/customers/9999/points").queryParams(params))
                .andExpect(jsonPath("$.total").value(total))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    public void shouldReturnTotalPointsForRangeThrow() throws Exception {

        given(iPointsService.calcPointsTotalFromTo(anyLong(), any(), any())).willThrow(new NoRecordFoundException(CUSTOMER_NOT_EXISTS));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from","2020-04-12");
        params.add("to","2020-04-12");

        mockMvc.perform(get("/customers/9999/points").queryParams(params))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(NO_RECORD_FOUND));

    }


}
