package com.example.orderservice.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputOrder {
  @NotNull
  @NotBlank
  @Size(max=36)
  private String userAccountId;

  @Valid
  @NotNull
  @Size(max=500)
  private List<IOOrderItem> orderItems;
}

