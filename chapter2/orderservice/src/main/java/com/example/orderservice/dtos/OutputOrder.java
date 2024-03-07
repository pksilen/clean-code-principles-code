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
@NoArgsConstructor
@AllArgsConstructor
public class OutputOrder {
  @NotNull
  @NotBlank
  @Size(max=36)
  private String id;

  @NotNull
  @NotBlank
  @Size(max=36)
  private String userAccountId;

  @Valid
  @NotNull
  @Size(max=500)
  private List<IOOrderItem> orderItems;
}