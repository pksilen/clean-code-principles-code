package com.example.orderservice.services.application;

import com.example.orderservice.Application;
import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.entities.Order;
import com.example.orderservice.repositories.DbOrder;
import com.example.orderservice.errors.EntityNotFoundError;
import com.example.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Primary
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
  public final OutputOrder createOrder(final InputOrder inputOrder) {
    // Input DTO is converted to valid domain entity
    final var order = Order.from(inputOrder);
    
    // If your model had additional business logic, you
    // could perform it here using domain entity and/or
    // domain service methods
    // Do not inline all the business logic code here, but
    // create separate methods either in domain entity or
    // domain service classes.
    // This example does not have any additional business
    // logic

    // Creates database entity which can be different from domain entity
    final var dbOrder = DbOrder.from(order);

    orderRepository.save(dbOrder);

    // Returns output DTO which can differ from the domain entity, e.g.
    // domain entity might contain fields not wanted to be delivered to clients
    // Output DTO class contains validations which can be enabled in controllers
    // This can be useful to prevent disclosure of sensitive data upon a successful
    // injection attack
    return order.toOutput();
  }

  @Override
  public final OutputOrder getOrderById(final String id) {
    final var dbOrder = orderRepository.findById(id)
      .orElseThrow(() ->
        new EntityNotFoundError(ORDER, id));

    return dbOrder.toDomainEntity().toOutput();
  }

  @Override
  public Iterable<OutputOrder> getOrdersByUserAccountId(final String userAccountId) {
    final var dbOrders = orderRepository.findByUserAccountId(userAccountId);
    return StreamSupport.stream(dbOrders.spliterator(), false)
      .map(dbOrder -> dbOrder.toDomainEntity().toOutput()).toList();
  }

  @Override
  public void updateOrder(final String id, InputOrder inputOrder) {
    if (orderRepository.existsById(id)) {
      final var order = Order.from(inputOrder);
      final var dbOrder = DbOrder.from(order, id);
      orderRepository.save(dbOrder);
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
