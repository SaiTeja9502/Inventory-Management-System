package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Quotation;

@Repository
public class QuotationRepositoryImpl {
	private final MongoTemplate mongoTemplate;

    @Autowired
    public QuotationRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    public List<Quotation> findQuotationsByPurchaseId(String purchaseId) {
        // Create a Query to find quotations by purchaseId
        Query query = Query.query(Criteria.where("purchaseID").is(purchaseId));

        // Use MongoTemplate to execute the query
        return mongoTemplate.find(query, Quotation.class);
    }
    
    public List<Quotation> findQuotationsBySupplierId(String supplierId) {
        // Create a Query to find quotations by supplierId
        Query query = Query.query(Criteria.where("supplierId").is(supplierId));

        // Use MongoTemplate to execute the query
        return mongoTemplate.find(query, Quotation.class);
    }

}
