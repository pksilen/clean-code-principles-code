package com.example.orderservice.controllers;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.entities.Order;
import com.example.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
public class RestOrderController {
  private final OrderService orderService;

  @Autowired
  public RestOrderController(final OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public final Order createOrder(
    @RequestBody final InputOrder inputOrder
  ) {
    return orderService.createOrder(inputOrder);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public final Order getOrderById(
    @PathVariable("id") final Long id
  ) {
    return orderService.getOrderById(id);
  }

  @GetMapping(params = "userAccountId")
  @ResponseStatus(HttpStatus.OK)
  public final Iterable<Order> getOrdersByUserAccountId(
    @RequestParam("userAccountId") final Long userAccountId
  ) {
    return orderService.getOrdersByUserAccountId(userAccountId);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public final void updateOrder(
    @PathVariable final Long id,
    @RequestBody final InputOrder inputOrder
  ) {
    orderService.updateOrder(id, inputOrder);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public final void deleteOrder(
    @PathVariable final Long id
  ) {
    orderService.deleteOrderById(id);
  }
}