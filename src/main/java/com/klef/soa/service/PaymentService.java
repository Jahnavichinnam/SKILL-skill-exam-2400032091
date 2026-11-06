package com.klef.soa.service;

import java.util.List;

import com.klef.soa.entity.Payment;

public interface PaymentService
{
    Payment addPayment(Payment p);
    Payment updatePayment(Payment p);
    List<Payment> displayAllPayments();
    Payment displayPaymentById(Long paymentId);
    String deletePaymentById(Long paymentId);
    List<Payment> displayPaymentsByName(String name);
    List<Payment> displayPaymentsByType(String type);
}