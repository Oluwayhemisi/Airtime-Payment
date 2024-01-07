package com.example.airtime.payments.repository;

import com.example.airtime.payments.entity.AirtimePayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirtimeRepository extends JpaRepository<AirtimePayment, Long> {
}
