package com.rogerserra.customer.repository;

import com.rogerserra.customer.model.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest // without this, @autowired won't work, and it's also required to test all JPA repositories
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @BeforeEach
    void init(TestInfo testInfo) {
        // TestInfo is used to inject information about the current test or container
        System.out.println("Start..." + testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll(); // I want a clean instance of the table each time
    }

    @Test
    @DisplayName("Find Customer By Email Test")
    void canFindCustomerByEmail() {
        // GIVEN
        Customer customer = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        underTest.save(customer);
        // WHEN
        Optional<Customer> existsByEmail = underTest.findCustomerByEmail("jgarland@killchaos.com");
        boolean found = existsByEmail.isPresent();
        // THEN
        assertThat(found).isTrue();
    }

    @Test
    @DisplayName("Delete Customer By Email Test")
    void canDeleteCustomerByEmail() {
        // GIVEN
        Customer customer = Customer.builder()
                .firstName("Jack")
                .lastName("Garland")
                .city("Cornelia")
                .email("jgarland@killchaos.com")
                .birthDate(Date.valueOf("1990-03-15"))
                .isVip(true)
                .build();
        underTest.save(customer);
        // WHEN
        underTest.deleteCustomerByEmail("jgarland@killchaos.com");
        Optional<Customer> existsByEmail = underTest.findCustomerByEmail("jgarland@killchaos.com");
        boolean found = existsByEmail.isPresent();
        // THEN
        assertThat(found).isFalse();
    }

    @Test
    @DisplayName("Get all VIP Customers Test")
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
        underTest.save(jack);
        underTest.save(noctis);
        underTest.save(bowser);
        // WHEN
        List<Optional<Customer>> vipUsers = underTest.getVipCustomers();
        int numberOfVips = vipUsers.size();
        // THEN
        assertThat(numberOfVips).isEqualTo(2);
    }

    // there was no need to test this, because default jpa methods are already ok
    @Test
    @DisplayName("Find Customer by ID Test")
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
        Customer saved = underTest.save(jack);
        // WHEN
        Optional<Customer> dbCustomer = underTest.findById(saved.getID());
        boolean exists = dbCustomer.isPresent();
        // THEN
        assertThat(exists).isTrue();
    }
}