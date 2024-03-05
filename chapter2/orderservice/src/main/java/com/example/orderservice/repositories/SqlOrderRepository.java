package com.example.orderservice.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Qualifier("sql")
@Repository
public interface SqlOrderRepository extends CrudRepository<DbOrder, String>, OrderRepository {
}
