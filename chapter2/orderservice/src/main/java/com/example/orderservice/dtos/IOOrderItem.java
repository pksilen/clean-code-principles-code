package com.example.orderservice.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class IOOrderItem {
  private String id;
  private String salesItemId;

  @Min(1)
  private Integer quantity;
}
