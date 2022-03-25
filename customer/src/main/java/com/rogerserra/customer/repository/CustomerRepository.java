package com.rogerserra.customer.repository;

import com.rogerserra.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// ctrl + shift + t -> create test class
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM customer c WHERE c.email = :email", nativeQuery = true)
    void deleteCustomerByEmail(@Param("email") String email);

    //@Query ("select c from customer c where c.is_vip = 1") // both are the same
    @Query (value = "SELECT * FROM customer WHERE is_vip = 1 ", nativeQuery = true) // 1 is the same as True
    List<Optional<Customer>> getVipCustomers();


}
