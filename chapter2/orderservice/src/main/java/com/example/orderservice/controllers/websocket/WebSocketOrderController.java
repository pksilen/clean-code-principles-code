package com.example.orderservice.controllers.websocket;

import com.example.orderservice.dtos.OutputOrder;
import com.example.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

// Partial implementation of WebSocket controller with one method
// WebSocket client example available at: https://github.com/pksilen/clean-code-principles-code/tree/main/chapter2/orderservice_websocket_client

@Controller
public class WebSocketOrderController {
  private final OrderService orderService;

  @Autowired
  public WebSocketOrderController(final OrderService orderService) {
    this.orderService = orderService;
  }

  @MessageMapping("/getOrderById")
  @SendToUser("/response")
  public OutputOrder getSalesItem(final Id idObject) {
    return orderService.getOrderById(idObject.getId());
  }

  @MessageExceptionHandler
  @SendToUser("/response")
  public String handleException(final Throwable exception) {
    return exception.getMessage();
  }
}
