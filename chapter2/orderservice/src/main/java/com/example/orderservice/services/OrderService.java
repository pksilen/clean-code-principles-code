package com.example.orderservice.services;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.repositories.DbOrder;

public interface OrderService {
  OutputOrder createOrder(final InputOrder inputOrder);
  OutputOrder getOrderById(final String id);
  Iterable<OutputOrder> getOrdersByUserAccountId(final String userAccountId);
  void updateOrder(final String id, final InputOrder inputOrder);
  void deleteOrderById(final String id);
}