package com.example.orderservice.controllers.graphql;

import com.example.orderservice.common.aspects.auditlogging.AuditLog;
import com.example.orderservice.common.aspects.authorization.annotations.AllowForUserWithOneOfRoles;
import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
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
  public OutputOrder createOrder(
    @Valid @Argument final InputOrder inputOrder
  ) {
    return orderService.createOrder(inputOrder);
  }

  @QueryMapping
  public Iterable<OutputOrder> ordersByUserAccountId(
    @Argument final String userAccountId
  ) {
    return orderService.getOrdersByUserAccountId(userAccountId);
  }

  @QueryMapping
  public OutputOrder orderById(
    @Argument final String id
  ) {
    return orderService.getOrderById(id);
  }

  @MutationMapping
  public IdResponse updateOrder(
    @Argument final String id,
    @Argument final InputOrder inputOrder
  ) {
    orderService.updateOrder(id, inputOrder);
    return new IdResponse(id);
  }

  @AllowForUserWithOneOfRoles({"admin"})
  @AuditLog
  @MutationMapping
  public IdResponse deleteOrder(
    @ContextValue final String authHeader,
    @ContextValue final String auditLogMessage,
    @Argument final String id
  ) {
    orderService.deleteOrderById(id);
    return new IdResponse(id);
  }
}
