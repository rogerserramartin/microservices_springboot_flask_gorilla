package com.rogerserra.customer.service;

import com.rogerserra.customer.model.Customer;
import com.rogerserra.customer.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository; // no need for redundant testing, we already know that it works
    private AutoCloseable autoCloseable;

    @InjectMocks
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close(); //cerramos el recurso despues del test
    }

    @Test
    @DisplayName("Register Customer Test")
    void canRegisterCustomer() {
        // GIVEN
        Customer customer = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        // WHEN
        underTest.registerCustomer(customer);
        // THEN
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(argumentCaptor.capture());
        Customer captured = argumentCaptor.getValue();
        Assertions.assertThat(captured.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    @DisplayName("Update Customer by ID Test")
    void canUpdateCustomerById() {
        // GIVEN
        Customer customer = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        underTest.registerCustomer(customer);

        // WHEN
        underTest.updateCustomerById(1L, customer);

        // THEN
        verify(customerRepository).save(customer);

    }

    @Test
    @DisplayName("Get All Customers Test")
    void canGetAllCustomers() {
        // GIVEN
        Customer jack = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        Customer noctis = Customer.builder()
                .firstName("Noctis")
                .lastName("Lucis")
                .city("Insomnia")
                .email("nlucis@insomnia.com")
                .birthDate(Date.valueOf("1997-08-11"))
                .isVip(true)
                .build();
        Customer bowser = Customer.builder()
                .firstName("Bowser")
                .lastName("Koopa")
                .city("Mushroom Kingdom")
                .email("bowser@bowser.com")
                .birthDate(Date.valueOf("1988-09-19"))
                .isVip(false)
                .build();
        underTest.registerCustomer(jack);
        underTest.registerCustomer(noctis);
        underTest.registerCustomer(bowser);

        // WHEN
        List<Customer> customers = underTest.getAllCustomers();
        // THEN
        verify(customerRepository).findAll();
    }

    @Test
    @DisplayName("Get All VIP Customers Test")
    void canGetVipCustomers() {
        // GIVEN
        Customer jack = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        underTest.registerCustomer(jack);

        // WHEN
        List<Customer> customers = underTest.getVipCustomers();

        // THEN
        verify(customerRepository).getVipCustomers();
    }

    @Test
    @DisplayName("Get Customer by Id Test")
    void canGetCustomerById() {
        // GIVEN
        Customer jack = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        underTest.registerCustomer(jack);

        // WHEN
        Customer dbCustomer = underTest.getCustomerById(1L);

        // THEN
        verify(customerRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Customer by Email Test")
    void canGetCustomerByEmail() {
        // GIVEN
        Customer jack = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        underTest.registerCustomer(jack);

        // WHEN
        Customer dbCustomer = underTest.getCustomerByEmail("jgarland@killchaos.com");

        // THEN
        verify(customerRepository).findCustomerByEmail("jgarland@killchaos.com");
    }

    @Test
    @DisplayName("Delete Customer by ID Test")
    void deleteCustomerById() {
        // GIVEN
        Customer jack = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        underTest.registerCustomer(jack);

        // WHEN
        underTest.deleteCustomerById(1L);

        // THEN
        verify(customerRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete Customer by ID Email")
    void deleteCustomerByEmail() {
        // GIVEN
        Customer jack = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        underTest.registerCustomer(jack);

        // WHEN
        underTest.deleteCustomerByEmail("jgarland@killchaos.com");

        // THEN
        verify(customerRepository).deleteCustomerByEmail("jgarland@killchaos.com");
    }
}