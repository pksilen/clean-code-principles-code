package com.example.orderservice.repositories;

import java.util.Optional;


public interface OrderRepository {
  <S extends DbOrder> S save(final S dbOrder);

  Optional<DbOrder> findById(final String id);

  boolean existsById(final String id);

  Iterable<DbOrder> findByUserAccountId(final String userAccountId);

  void deleteById(final String id);
}
