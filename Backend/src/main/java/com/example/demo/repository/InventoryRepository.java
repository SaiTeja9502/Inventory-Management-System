package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Inventory;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String>{

}
