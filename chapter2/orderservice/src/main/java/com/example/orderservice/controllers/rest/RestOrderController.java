package com.example.orderservice.controllers.rest;

import com.example.orderservice.common.aspects.auditlogging.AuditLog;
import com.example.orderservice.common.aspects.authorization.annotations.AllowForUserWithOneOfRoles;
import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    @RequestParam("userAccountId") final String userAccountId,
    @RequestParam(value = "page", defaultValue = "1") @Min(1) int page
  ) {
    return orderService.getOrdersByUserAccountId(userAccountId, page);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateOrder(
    @PathVariable final String id,
    @Valid @RequestBody final InputOrder inputOrder
  ) {
    orderService.updateOrder(id, inputOrder);
  }

  @AllowForUserWithOneOfRoles({"admin"})
  @AuditLog
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(
    @PathVariable final String id,
    final HttpServletRequest request,
    @RequestHeader(value = "Authorization", required = false) final String authHeader
  ) {
    orderService.deleteOrderById(id);
  }
}
