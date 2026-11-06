
package com.klef.soa.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.klef.soa.entity.Payment;
import com.klef.soa.repository.PaymentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE
)
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository repository;

    private Long existingPaymentId;

    // Prepare test data before every test
    @BeforeEach
    void setUp() {

        repository.deleteAll();

        Payment p1 = new Payment();
        p1.setName("Google Pay");
        p1.setType("UPI");
        p1.setStatus("SUCCESS");
        p1.setLocation("India");

        Payment p2 = new Payment();
        p2.setName("PhonePe");
        p2.setType("UPI");
        p2.setStatus("SUCCESS");
        p2.setLocation("Hyderabad");

        Payment p3 = new Payment();
        p3.setName("Paytm");
        p3.setType("Wallet");
        p3.setStatus("PENDING");
        p3.setLocation("Vijayawada");

        List<Payment> payments =
                repository.saveAll(List.of(p1, p2, p3));

        repository.flush();

        existingPaymentId = payments.get(0).getPaymentId();
    }

    // TEST CASE 1: FIND ALL
    @Test
    void testFindAllPayments() {

        List<Payment> payments = repository.findAll();

        assertNotNull(payments);
        assertEquals(3, payments.size());

        System.out.println("Total Payments: " + payments.size());
    }

    // TEST CASE 2: FIND BY ID
    @Test
    void testFindPaymentById() {

        Optional<Payment> payment =
                repository.findById(existingPaymentId);

        assertTrue(payment.isPresent());

        assertEquals(
                existingPaymentId,
                payment.get().getPaymentId()
        );

        System.out.println("Payment ID: "
                + payment.get().getPaymentId());

        System.out.println("Payment Name: "
                + payment.get().getName());

        // Check non-existing ID
        Optional<Payment> invalidPayment =
                repository.findById(Long.MAX_VALUE);

        assertFalse(invalidPayment.isPresent());

        System.out.println("Invalid Payment ID not found");
    }

    // TEST CASE 3: FIND BY DERIVED QUERY
    @Test
    void testFindPaymentByName() {

        List<Payment> payments =
                repository.findByName("Google Pay");

        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        assertEquals(1, payments.size());

        assertEquals(
                "Google Pay",
                payments.get(0).getName()
        );

        // Test another derived query
        List<Payment> upiPayments =
                repository.findByType("UPI");

        assertEquals(2, upiPayments.size());

        System.out.println("Payment Name: "
                + payments.get(0).getName());

        System.out.println("UPI Payments: "
                + upiPayments.size());
    }

    // TEST CASE 4: COUNT
    @Test
    void testTotalPaymentCount() {

        long count = repository.count();

        assertEquals(3L, count);

        System.out.println("Total Payments in Database: "
                + count);
    }

    // TEST CASE 5: ENTITY DETAILS AND TYPE
    @Test
    void testPaymentDetails() {

        List<Payment> payments =
                repository.findByType("Wallet");

        assertNotNull(payments);
        assertEquals(1, payments.size());

        Payment payment = payments.get(0);

        assertNotNull(payment.getPaymentId());
        assertNotNull(payment.getName());
        assertNotNull(payment.getType());
        assertNotNull(payment.getStatus());
        assertNotNull(payment.getLocation());
        assertNotNull(payment.getCreatedAt());
        assertNotNull(payment.getUpdatedAt());

        assertEquals("Paytm", payment.getName());
        assertEquals("Wallet", payment.getType());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("Vijayawada", payment.getLocation());

        System.out.println("Payment ID: "
                + payment.getPaymentId());

        System.out.println("Name: "
                + payment.getName());

        System.out.println("Type: "
                + payment.getType());

        System.out.println("Status: "
                + payment.getStatus());

        System.out.println("Location: "
                + payment.getLocation());

        System.out.println("TC-5 PASSED");
    }
}
