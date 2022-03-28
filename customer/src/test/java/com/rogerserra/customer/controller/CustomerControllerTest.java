package com.rogerserra.customer.controller;

import com.rogerserra.customer.model.Customer;
import com.rogerserra.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    private Customer customer;
    private Customer customerId;
    private List<Customer> customerList;

    @BeforeEach
    void setUp(){
        customer = Customer.builder()
                .firstName("Noctis")
                .lastName("Lucis")
                .city("Insomnia")
                .email("nlucis@insomnia.com")
                .birthDate(Date.valueOf("1997-08-11"))
                .isVip(true)
                .build();
        customerId = Customer.builder()
                .ID(1L)
                .firstName("Noctis")
                .lastName("Lucis")
                .city("Insomnia")
                .email("nlucis@insomnia.com")
                .birthDate(Date.valueOf("1997-08-11"))
                .isVip(true)
                .build();
        customerList = new ArrayList<>();
        customerList.add(customerId);
    }

    @Test
    @DisplayName("Get all customers Test")
    void canGetCustomers() {
        when(customerService.getAllCustomers())
                .thenReturn(customerList);
        try {
            this.mockMvc.perform(get("/api/v1/customers")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get customer by Id Test")
    void canGetCustomerById() {
        when(customerService.getCustomerById(1L))
                .thenReturn(customerId);
        try {
            this.mockMvc.perform(get("/api/v1/customers/customer/1")
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.firstName")
                         .value(customerId.getFirstName())); // this needs to match with the entity variable/attribute name, not @column name
                         // it's also the json's property name
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Register customer Test")
    void canRegisterCustomer() {

        // WHEN
        when(customerService.registerCustomer(customer)).thenReturn(customerId);

        // THEN
        try {
            this.mockMvc.perform(post("/api/v1/customers/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "firstName": "Noctis",
                                "lastName": "Lucis",
                                "city": "Insomnia",
                                "email": "nlucis@insomnia.com",
                                "birthDate": "1997-08-11",
                                "isVip": true
                            }
                            """)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}