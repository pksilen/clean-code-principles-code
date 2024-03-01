package com.example.salesitemservice;


public interface SalesItemService {
  SalesItem createSalesItem(InputSalesItem inputSalesItem);

  SalesItem getSalesItemById(Long id);

  Iterable<SalesItem> getSalesItemsByUserAccountId(Long userAccountId);

  Iterable<SalesItem> getSalesItems();

  void updateSalesItem(Long id, InputSalesItem inputSalesItem);

  void deleteSalesItemById(Long id);

  void deleteSalesItems();
}
