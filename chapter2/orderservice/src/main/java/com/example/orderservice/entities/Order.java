package com.example.orderservice.entities;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private String id;
  private String userAccountId;
  private List<OrderItem> orderItems;

  public static Order from(final InputOrder inputOrder) {
    final var order = new ModelMapper().map(inputOrder, Order.class);
    order.setId(UUID.randomUUID().toString());
    return order;
  }

  public static Order from(
    final InputOrder inputOrder,
    final String id
  ) {
    final var order = new ModelMapper().map(inputOrder, Order.class);
    order.setId(id);
    return order;
  }

  public OutputOrder toOutput() {
    return new ModelMapper().map(this, OutputOrder.class);
  }
}
