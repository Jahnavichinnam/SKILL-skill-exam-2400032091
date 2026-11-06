package com.klef.soa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.soa.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>
{
    List<Payment> findByName(String name);

    List<Payment> findByType(String type);
}