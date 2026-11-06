package com.klef.soa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.soa.entity.Payment;
import com.klef.soa.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService
{
    @Autowired
    private PaymentRepository repo;

    @Override
    public Payment addPayment(Payment p)
    {
        return repo.save(p);
    }

    @Override
    public Payment updatePayment(Payment p)
    {
        Optional<Payment> optional = repo.findById(p.getPaymentId());

        if(optional.isPresent())
        {
            Payment payment = optional.get();

            payment.setName(p.getName());
            payment.setType(p.getType());
            payment.setStatus(p.getStatus());
            payment.setLocation(p.getLocation());

            return repo.save(payment);
        }

        return null;
    }

    @Override
    public List<Payment> displayAllPayments()
    {
        return repo.findAll();
    }

    @Override
    public Payment displayPaymentById(Long paymentId)
    {
        return repo.findById(paymentId).orElse(null);
    }

    @Override
    public String deletePaymentById(Long paymentId)
    {
        if(repo.existsById(paymentId))
        {
            repo.deleteById(paymentId);
            return "Payment Deleted Successfully";
        }

        return "Payment ID Not Found";
    }

    @Override
    public List<Payment> displayPaymentsByName(String name)
    {
        return repo.findByName(name);
    }

    @Override
    public List<Payment> displayPaymentsByType(String type)
    {
        return repo.findByType(type);
    }
}