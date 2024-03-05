package com.example.orderservice.repositories;

import com.example.orderservice.entities.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Qualifier("mongodb")
@Repository
public interface MongoDbOrderRepository extends MongoRepository<Order, String>, OrderRepository {
}
