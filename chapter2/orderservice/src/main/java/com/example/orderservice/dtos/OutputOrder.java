package com.example.orderservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputOrder {
  private String id;
  private String userAccountId;
  private List<IOOrderItem> orderItems;
}
