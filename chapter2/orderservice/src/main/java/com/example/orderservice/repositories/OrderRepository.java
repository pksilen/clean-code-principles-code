package com.example.orderservice.repositories;

import com.example.orderservice.entities.Order;

import java.util.Optional;

public interface OrderRepository {
  <S extends Order> S save(final S order);
  Optional<Order> findById(final Long id);
  boolean existsById(final Long id);
  Iterable<Order> findByUserAccountId(final Long userAccountId);
  void deleteById(final Long id);
}
