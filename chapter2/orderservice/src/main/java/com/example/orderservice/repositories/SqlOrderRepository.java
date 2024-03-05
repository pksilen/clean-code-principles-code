package com.example.orderservice.repositories;

import com.example.orderservice.entities.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Qualifier("sql")
@Repository
public interface SqlOrderRepository extends CrudRepository<Order, String>, OrderRepository {
}
