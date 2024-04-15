package com.example.orderservice.controllers.rest;

import com.example.orderservice.common.aspects.auditlogging.AuditLog;
import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
public class RestOrderController {
  private OrderService orderService;

  @Autowired
  public RestOrderController(final OrderService orderService) {
    this.orderService = orderService;
  }

  // Controller methods should not contain any business logic
  // Controller method should delegate to application services (use cases)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OutputOrder createOrder(
    @Valid @RequestBody final InputOrder inputOrder
  ) {
    return orderService.createOrder(inputOrder);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public OutputOrder getOrderById(
    @PathVariable("id") final String id
  ) {
    return orderService.getOrderById(id);
  }

  @GetMapping(params = "userAccountId")
  @ResponseStatus(HttpStatus.OK)
  public Iterable<OutputOrder> getOrdersByUserAccountId(
    @RequestParam("userAccountId") final String userAccountId
  ) {
    return orderService.getOrdersByUserAccountId(userAccountId);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateOrder(
    @PathVariable final String id,
    @Valid @RequestBody final InputOrder inputOrder
  ) {
    orderService.updateOrder(id, inputOrder);
  }

  @AuditLog
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(
    @PathVariable final String id,
    final HttpServletRequest request
  ) {
    orderService.deleteOrderById(id);
  }
}
