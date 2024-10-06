package com.example.demo.repositories;

import com.example.demo.models.Customer;
import com.example.demo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerAndCreateDateBetween(Customer customer, LocalDate startDate, LocalDate endDate);

    List<Order> findByCustomer(Customer customer);
}