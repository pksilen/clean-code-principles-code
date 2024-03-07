package com.example.orderservice.controllers.graphql;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.services.application.OrderService;
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
  public final OutputOrder orderById(
    @Argument final String id
  ) {
    return orderService.getOrderById(id);
  }
}
