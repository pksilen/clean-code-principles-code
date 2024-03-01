package com.example.orderservice.entities;

import com.example.orderservice.dtos.InputOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

  @OneToMany(mappedBy="order")
  private List<OrderItem> orderItems;

  public static Order from(final InputOrder inputOrder) {
    return new ModelMapper().map(inputOrder, Order.class);
  }

  public static Order from(
    final InputOrder inputOrder,
    final Long id
  ) {
    final var order = new ModelMapper().map(inputOrder, Order.class);
    order.setId(id);
    return order;
  }
}
