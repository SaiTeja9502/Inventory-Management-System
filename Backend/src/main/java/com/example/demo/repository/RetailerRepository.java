package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Retailer;

@Repository
public interface RetailerRepository extends MongoRepository<Retailer, String>{

}
