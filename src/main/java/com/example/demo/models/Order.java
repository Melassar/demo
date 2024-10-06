package com.example.demo.models;

import com.example.demo.enums.OrderSide;
import com.example.demo.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    private String assetName;

    @Enumerated(EnumType.STRING)
    private OrderSide orderSide;

    private Double size;

    private Double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING; // PENDING, MATCHED, CANCELED

    private LocalDate createDate = LocalDate.now();

}

