package com.example.orderservice.services;

import com.example.orderservice.Application;
import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.entities.Order;
import com.example.orderservice.repositories.DbOrder;
import com.example.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class ShoppingCartEmptyingOrderService implements OrderService {
  private final OrderRepository orderRepository;
  private final ShoppingCartService shoppingCartService;

  @Autowired
  public ShoppingCartEmptyingOrderService(
    @Qualifier(Application.DATABASE_TYPE) final OrderRepository orderRepository,
    final ShoppingCartService shoppingCartService
  ) {
    this.orderRepository = orderRepository;
    this.shoppingCartService = shoppingCartService;
  }

  @Override
  public final OutputOrder createOrder(final InputOrder inputOrder) {
    final var order = Order.from(inputOrder);

    // Instead of calling the shopping cart domain service here, you should consider
    // calling it from the Order.from factory method, so you don't have to use a
    // getter for userAccountId and you can make your code more object-oriented
    shoppingCartService.emptyCart(order.getUserAccountId());
    final var dbOrder = DbOrder.from(order);
    orderRepository.save(dbOrder);
    return order.toOutput();
  }

  @Override
  public OutputOrder getOrderById(String id) {
    // Not implemented
    return null;
  }

  @Override
  public Iterable<OutputOrder> getOrdersByUserAccountId(final String userAccountId, final int page) {
    // Not implemented
    return null;
  }

  @Override
  public void updateOrder(String id, InputOrder inputOrder) {
    // Not implemented
  }

  @Override
  public void deleteOrderById(String id) {
    // Not implemented
  }
}