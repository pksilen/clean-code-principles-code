package com.example.orderservice.services;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.entities.Order;

public interface OrderService {
  Order createOrder(final InputOrder inputOrder);
  Order getOrderById(final Long id);
  Iterable<Order> getOrdersByUserAccountId(final Long userAccountId);
  void updateOrder(Long id, final InputOrder inputOrder);
  void deleteOrderById(Long id);
}