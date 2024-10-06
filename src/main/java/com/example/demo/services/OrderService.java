package com.example.demo.services;

import com.example.demo.enums.OrderSide;
import com.example.demo.enums.OrderStatus;
import com.example.demo.models.Asset;
import com.example.demo.models.Customer;
import com.example.demo.models.Order;
import com.example.demo.repositories.AssetRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Order createOrder(Long customerId, String assetName, OrderSide orderSide, Double size, Double price) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (orderSide == OrderSide.BUY) {
            double totalCost = size * price;
            if (customer.getBalance() < totalCost) {
                throw new RuntimeException("Insufficient balance");
            }
            customer.setBalance(customer.getBalance() - totalCost);
        } else {
            Asset asset = assetRepository.findByCustomerAndAssetName(customer, assetName)
                    .orElseThrow(() -> new RuntimeException("Asset not found"));
            if (asset.getUsableSize() < size) {
                throw new RuntimeException("Insufficient asset size");
            }
            asset.setUsableSize(asset.getUsableSize() - size);
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setAssetName(assetName);
        order.setOrderSide(orderSide);
        order.setSize(size);
        order.setPrice(price);
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    public List<Order> listOrders(Long customerId, LocalDate startDate, LocalDate endDate) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return orderRepository.findByCustomerAndCreateDateBetween(customer, startDate, endDate);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be canceled");
        }

        if (order.getOrderSide() == OrderSide.BUY) {
            double totalCost = order.getSize() * order.getPrice();
            order.getCustomer().setBalance(order.getCustomer().getBalance() + totalCost);
        } else {
            Asset asset = assetRepository.findByCustomerAndAssetName(order.getCustomer(), order.getAssetName())
                    .orElseThrow(() -> new RuntimeException("Asset not found"));
            asset.setUsableSize(asset.getUsableSize() + order.getSize());
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
