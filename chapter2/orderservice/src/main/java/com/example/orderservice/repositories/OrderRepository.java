package com.example.orderservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface OrderRepository {
  <S extends DbOrder> S save(final S dbOrder);

  Optional<DbOrder> findById(final String id);

  boolean existsById(final String id);

  Page<DbOrder> findByUserAccountId(final String userAccountId, Pageable pageable);

  void deleteById(final String id);
}
