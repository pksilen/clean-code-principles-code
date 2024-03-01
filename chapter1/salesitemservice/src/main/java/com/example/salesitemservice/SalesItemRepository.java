package com.example.salesitemservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesItemRepository extends
        CrudRepository<SalesItem, Long> {
    Iterable<SalesItem> findByUserAccountId(Long userAccountId);
}
