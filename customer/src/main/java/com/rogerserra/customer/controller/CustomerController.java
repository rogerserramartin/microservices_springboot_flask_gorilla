package com.rogerserra.customer.controller;

import com.rogerserra.customer.model.Customer;
import com.rogerserra.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Slf4j
@EnableWebMvc
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "customer/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Long customerId){
        return customerService.getCustomerById(customerId);
    }

    @PostMapping(path = "/customer")
    public void registerCustomer(@RequestBody Customer customer){
        customerService.registerCustomer(customer);
        log.info("New customer registration {}", customer); // json
    }


}
