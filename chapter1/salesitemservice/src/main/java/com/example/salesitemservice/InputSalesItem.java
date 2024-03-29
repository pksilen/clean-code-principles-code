package com.example.salesitemservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputSalesItem {
  private Long userAccountId;
  private String name;
  private Integer price;
}
