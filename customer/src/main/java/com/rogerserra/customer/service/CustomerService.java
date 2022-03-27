package com.rogerserra.customer.service;

import com.rogerserra.customer.model.Customer;
import com.rogerserra.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService implements CustomerDAO {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public void registerCustomer(Customer customer) {
        this.customerRepository.save(customer);
    }

    @Override
    public void updateCustomerById(Long id, Customer customer) {
        Optional<Customer> foundById = this.customerRepository.findById(id);
        if(foundById.isPresent()){
            this.customerRepository.save(customer);
        } else {
            log.info("Customer with ID {} was not found.", id);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public List<Customer> getVipCustomers() {
        List<Optional<Customer>> customers = this.customerRepository.getVipCustomers();
        List<Customer> dbCustomers = new ArrayList<>();
        for(Optional<Customer> customer : customers){
            if(customer.isPresent()){
                Customer toAppend = customer.get(); // we don't need to assign each property 1 by 1
                dbCustomers.add(toAppend);
            }
        }
        return dbCustomers;
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(id);
        Customer customer = null;
        if(optionalCustomer.isPresent()){
            customer = optionalCustomer.get();
        } else {
            log.info("Customer with ID {} was not found", id);
        }
        return customer;
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Optional<Customer> customer = this.customerRepository.findCustomerByEmail(email);
        Customer dbCustomer = null;
        if(customer.isPresent()){
            dbCustomer = customer.get();
        }
        return dbCustomer;
    }

    @Override
    public void deleteCustomerById(Long id) {
        this.customerRepository.deleteById(id);
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        this.customerRepository.deleteCustomerByEmail(email);
    }
}
