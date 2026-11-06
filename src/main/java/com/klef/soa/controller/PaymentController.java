package com.klef.soa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.klef.soa.entity.Payment;
import com.klef.soa.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController
{
    @Autowired
    private PaymentService service;

    @GetMapping("/")
    public String demo()
    {
        return "Spring Boot Project";
    }

    @PostMapping("/add")
    public ResponseEntity<Payment> addPayment(@RequestBody Payment p)
    {
        Payment payment = service.addPayment(p);
        return ResponseEntity.status(201).body(payment);
    }

    @GetMapping("/displayall")
    public ResponseEntity<List<Payment>> displayAllPayments()
    {
        return ResponseEntity.ok(service.displayAllPayments());
    }

    @GetMapping("/display")
    public ResponseEntity<?> displayPaymentById(@RequestParam Long paymentId)
    {
        Payment payment = service.displayPaymentById(paymentId);

        if(payment != null) 
        {
            return ResponseEntity.ok(payment);
        }
        else
        {
        return ResponseEntity.status(404).body("Payment ID Not Found");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePayment(@RequestBody Payment payment)
    {
        Payment p = service.updatePayment(payment);

        if(p != null)
        {
            return ResponseEntity.ok(p);
        }
        else
        {
        return ResponseEntity.status(404).body("Payment ID Not Found");
        }
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long paymentId)
    {
        return ResponseEntity.ok(service.deletePaymentById(paymentId));
    }

    @GetMapping("/displaybyname/{name}")
    public ResponseEntity<List<Payment>> displayByName(@PathVariable String name)
    {
        return ResponseEntity.ok(service.displayPaymentsByName(name));
    }

    @GetMapping("/displaybytype/{type}")
    public ResponseEntity<List<Payment>> displayByType(@PathVariable String type)
    {
        return ResponseEntity.ok(service.displayPaymentsByType(type));
    }
}