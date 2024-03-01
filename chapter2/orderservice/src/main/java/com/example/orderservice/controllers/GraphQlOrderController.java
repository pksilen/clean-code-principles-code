package com.example.orderservice.controllers;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.entities.Order;
import com.example.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;


// @Controller
public class GraphQlOrderController {
  private final OrderService orderService;

  // @Autowired
  public GraphQlOrderController(final OrderService orderService) {
    this.orderService = orderService;
  }

  @MutationMapping
  public final Order createOrder(
    @Argument final InputOrder inputOrder
  ) {
    return orderService.createOrder(inputOrder);
  }
}
