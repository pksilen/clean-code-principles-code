package com.example.orderservice.repositories;

import com.example.orderservice.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlOrderRepository extends CrudRepository<Order, Long>, OrderRepository {
}
