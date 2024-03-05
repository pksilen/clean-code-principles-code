package com.example.orderservice.repositories;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.entities.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
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
public class DbOrder {
  @Id
  @jakarta.persistence.Id
  private String id;
  private String userAccountId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DbOrderItem> orderItems;

  public static DbOrder from(final Order order) {
    final var dbOrder = new DbOrder(order.getId(), order.getUserAccountId(), null);
    dbOrder.setOrderItems(createDbOrderItems(order, dbOrder));
    return dbOrder;
  }

  public static DbOrder from(final Order order, final String id) {
    final var dbOrder = new DbOrder(id, order.getUserAccountId(), null);
    dbOrder.setOrderItems(createDbOrderItems(order, dbOrder));
    return dbOrder;
  }

  public Order toDomainEntity() {
    return new ModelMapper().map(this, Order.class);
  }

  private static List<DbOrderItem> createDbOrderItems(
    final Order order,
    final DbOrder dbOrder
  ) {
    return order.getOrderItems().stream().map(orderItem -> new DbOrderItem(
      orderItem.getId(),
      orderItem.getSalesItemId(),
      orderItem.getQuantity(),
      dbOrder
    )).toList();
  }
}
