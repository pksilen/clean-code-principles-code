package com.example.orderservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputOrderItem {
  private Long id;
  private Long salesItemId;
  private Integer quantity;
}
