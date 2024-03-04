package com.example.orderservice.entities;

import com.example.orderservice.dtos.InputOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
// Table name must be quoted because order is a reserved
// word in MySQL
@Table(name = "\"order\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userAccountId;

  @OneToMany(mappedBy="order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems;

  public static Order from(final InputOrder inputOrder) {
    final var order = new Order(null, inputOrder.getUserAccountId(), null);
    order.setOrderItems(createOrderItems(inputOrder, order));
    return order;
  }

  public static Order from(
    final InputOrder inputOrder,
    final Long id
  ) {
    final var order = new Order(id, inputOrder.getUserAccountId(), null);
    order.setOrderItems(createOrderItems(inputOrder, order));
    return order;
  }

  private static List<OrderItem> createOrderItems(
    final InputOrder inputOrder,
    final Order order
  ) {
    return inputOrder.getOrderItems().stream().map(inputOrderItem -> new OrderItem(
      null,
      inputOrderItem.getSalesItemId(),
      inputOrderItem.getQuantity(),
      order
    )).toList();
  }
}
