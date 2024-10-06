package com.example.demo.controllers;

import com.example.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Void> depositMoney(@RequestParam Long customerId, @RequestParam Double amount) {
        transactionService.depositMoney(customerId, amount);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdrawMoney(@RequestParam Long customerId,
                                              @RequestParam Double amount,
                                              @RequestParam String iban) {
        transactionService.withdrawMoney(customerId, amount, iban);
        return ResponseEntity.noContent().build();
    }
}
