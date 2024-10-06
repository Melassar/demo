package com.example.demo.dto;

import com.example.demo.enums.OrderSide;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDTO {
    private Long customerId;
    private String assetName;
    private OrderSide orderSide;
    private Double size;
    private Double price;

}
