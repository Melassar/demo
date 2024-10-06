package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private CustomerRepository customerRepository;

    public void depositMoney(Long customerId, Double amount) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setBalance(customer.getBalance() + amount);
        customerRepository.save(customer);
    }

    public void withdrawMoney(Long customerId, Double amount, String iban) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customer.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        customer.setBalance(customer.getBalance() - amount);
        customerRepository.save(customer);
    }
}
