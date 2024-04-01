package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Message;

@Repository
public class MessageRepositoryImpl {
	private final MongoTemplate mongoTemplate;

    @Autowired
    public MessageRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    public List<Message> findUnreadMessagesForInventory() {
        Criteria criteria = Criteria.where("user").is(Message.UserType.INVENTORY).and("isRead").is(true);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Message.class);
    }

    public List<Message> findUnreadMessagesForUser(String userId) {
        Criteria criteria = Criteria.where("userId").is(userId).and("isRead").is(true);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Message.class);
    }
}
