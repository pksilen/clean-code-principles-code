package com.example.orderservice.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Qualifier("mongodb")
@Repository
public interface MongoDbOrderRepository extends MongoRepository<DbOrder, String>, OrderRepository {
}
