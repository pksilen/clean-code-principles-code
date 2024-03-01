package com.example.orderservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputOrder {
  private Long userAccountId;
  private List<InputOrderItem> orderItems;
}

