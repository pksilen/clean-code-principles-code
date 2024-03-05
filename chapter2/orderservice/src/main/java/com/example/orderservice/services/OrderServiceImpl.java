package com.example.orderservice.services;

import com.example.orderservice.Application;
import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.entities.Order;
import com.example.orderservice.errors.EntityNotFoundError;
import com.example.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
  private static final String ORDER = "Order";
  private final OrderRepository orderRepository;

  @Autowired
  public OrderServiceImpl(
    @Qualifier(Application.DATABASE_TYPE) final OrderRepository orderRepository
  ) {
    this.orderRepository = orderRepository;
  }

  @Override
  public final Order createOrder(final InputOrder inputOrder) {
    final var order = Order.from(inputOrder);
    return orderRepository.save(order);
  }

  @Override
  public final Order getOrderById(final String id) {
    return orderRepository.findById(id)
      .orElseThrow(() ->
        new EntityNotFoundError(ORDER, id));
  }

  @Override
  public Iterable<Order> getOrdersByUserAccountId(final String userAccountId) {
    return orderRepository.findByUserAccountId(userAccountId);
  }

  @Override
  public void updateOrder(final String id, InputOrder inputOrder) {
    if (orderRepository.existsById(id)) {
      final var order = Order.from(inputOrder, id);
      orderRepository.save(order);
    } else {
      throw new EntityNotFoundError(ORDER, id);
    }
  }

  @Override
  public void deleteOrderById(final String id) {
    if (orderRepository.existsById(id)) {
      orderRepository.deleteById(id);
    }
  }
}
