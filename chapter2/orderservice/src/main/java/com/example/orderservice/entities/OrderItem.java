package com.example.orderservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
  private String id;
  private String salesItemId;
  private Integer quantity;
}
