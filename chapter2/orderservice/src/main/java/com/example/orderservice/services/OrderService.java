package com.example.orderservice.services;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.entities.Order;

public interface OrderService {
  Order createOrder(final InputOrder inputOrder);
  Order getOrderById(final String id);
  Iterable<Order> getOrdersByUserAccountId(final String userAccountId);
  void updateOrder(final String id, final InputOrder inputOrder);
  void deleteOrderById(final String id);
}