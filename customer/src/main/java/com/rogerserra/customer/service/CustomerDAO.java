package com.rogerserra.customer.service;

import com.rogerserra.customer.model.Customer;

import java.util.List;

public interface CustomerDAO {
    // Optionals are just for Repositry Layer, we use the MODEL/ENTITY class as the type in the service layer

    // CREATE - UPDATE
    Customer registerCustomer(Customer customer);
    void updateCustomerById(Long id, Customer customer);

    // READ
    List<Customer> getAllCustomers();
    List<Customer> getVipCustomers();
    Customer getCustomerById(Long id);
    Customer getCustomerByEmail(String email);

    // DELETE
    void deleteCustomerById(Long id);
    void deleteCustomerByEmail(String email);


}
