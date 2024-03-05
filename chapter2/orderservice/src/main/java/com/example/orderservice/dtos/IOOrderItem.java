package com.example.orderservice.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IOOrderItem {
  @NotNull
  @NotBlank
  @Size(max=36)
  private String id;

  @NotNull
  @NotBlank
  @Size(max=36)
  private String salesItemId;

  @NotNull
  @Min(1)
  private Integer quantity;
}
