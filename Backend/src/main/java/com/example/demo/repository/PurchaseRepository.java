package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Purchase;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase, String> {
	
}
