package com.example.orderservice.entities;

import com.example.orderservice.dtos.InputOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;


@Entity
// Table name must be quoted because order is a reserved
// word in MySQL
@Table(name = "\"order\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  @Id
  @jakarta.persistence.Id
  private String id;
  private String userAccountId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems;

  public static Order from(final InputOrder inputOrder) {
    final var id = UUID.randomUUID().toString();
    final var order = new Order(id, inputOrder.getUserAccountId(), null);
    order.setOrderItems(createOrderItems(inputOrder, order));
    return order;
  }

  public static Order from(
    final InputOrder inputOrder,
    final String id
  ) {
    final var order = new Order(id, inputOrder.getUserAccountId(), null);
    order.setOrderItems(createOrderItems(inputOrder, order));
    return order;
  }

  private static List<OrderItem> createOrderItems(
    final InputOrder inputOrder,
    final Order order
  ) {
    return inputOrder.getOrderItems().stream().map(inputOrderItem -> {
      final var id = inputOrderItem.getId();

      return new OrderItem(
        id == null ? UUID.randomUUID().toString() : id,
        inputOrderItem.getSalesItemId(),
        inputOrderItem.getQuantity(),
        order
      );
    }).toList();
  }
}
