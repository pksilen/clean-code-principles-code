package com.example.orderservice.controllers;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.repositories.DbOrder;
import com.example.orderservice.services.OrderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;


// @Controller
public class GraphQlOrderController {
  private final OrderService orderService;

  // @Autowired
  public GraphQlOrderController(final OrderService orderService) {
    this.orderService = orderService;
  }

  @MutationMapping
  public final OutputOrder createOrder(
    @Argument final InputOrder inputOrder
  ) {
    return orderService.createOrder(inputOrder);
  }
}
