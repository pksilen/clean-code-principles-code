package com.example.orderservice.controllers.graphql;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
public class GraphQlOrderController {
  private final OrderService orderService;

  @Autowired
  public GraphQlOrderController(final OrderService orderService) {
    this.orderService = orderService;
  }

  @MutationMapping
  public final OutputOrder createOrder(
    @Valid @Argument final InputOrder inputOrder
  ) {
    return orderService.createOrder(inputOrder);
  }

  @QueryMapping
  public final Iterable<OutputOrder> ordersByUserAccountId(
    @Argument final String userAccountId
  ) {
    return orderService.getOrdersByUserAccountId(userAccountId);
  }

  @QueryMapping
  public final OutputOrder orderById(
    @Argument final String id
  ) {
    return orderService.getOrderById(id);
  }

  @MutationMapping
  public final IdResponse updateOrder(
    @Argument final String id,
    @Argument final InputOrder inputOrder
  ) {
    orderService.updateOrder(id, inputOrder);
    return new IdResponse(id);
  }

  @MutationMapping
  public final IdResponse deleteOrder(
    @Argument final String id
  ) {
    orderService.deleteOrderById(id);
    return new IdResponse(id);
  }
}
