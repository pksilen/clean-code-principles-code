package com.example.orderservice.repositories;

import com.example.orderservice.entities.Order;

import java.util.Optional;


public interface OrderRepository {
  <S extends Order> S save(final S order);
  Optional<Order> findById(final String id);
  boolean existsById(final String id);
  Iterable<Order> findByUserAccountId(final String userAccountId);
  void deleteById(final String id);
}
