package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;
import com.example.demo.model.Purchase;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PurchaseRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public PurchaseRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Purchase> findByStatusInitiated() {
    	return mongoTemplate.find(Query.query(Criteria.where("status").in(Purchase.Status.INITIATED, Purchase.Status.QUOTATIONS_AVAILABLE)), Purchase.class);
//        return mongoTemplate.find(Query.query(Criteria.where("status").in(Purchase.Status.INITIATED, Purchase.Status.QUOTATIONS_AVAILABLE)), Purchase.class);
    }
    
    public List<Purchase> findPurchasesByProducts(List<Product> products) {
        List<String> productIds = products.stream().map(Product::getProductID).collect(Collectors.toList());

        Criteria criteria = Criteria.where("productID").in(productIds);
        Query query = new Query(criteria);

        return mongoTemplate.find(query, Purchase.class);
    }
}

