package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
	
}
