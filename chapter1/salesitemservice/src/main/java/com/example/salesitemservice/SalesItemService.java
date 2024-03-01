package com.example.demo;

public interface SalesItemService {
    SalesItem createSalesItem(SalesItemArg salesItemArg);
    SalesItem getSalesItemById(Long id);

    Iterable<SalesItem> getSalesItemsByUserAccountId(
            Long userAccountId
    );

    Iterable<SalesItem> getSalesItems();

    void updateSalesItem(Long id, SalesItemArg salesItemArg);
    void deleteSalesItemById(Long id);
    void deleteSalesItems();
}
