package com.example.orderservice.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


// Output DTOs declare the structure of client output
// and related validations.
// Output DTOs are created based on domain entities
// Output DTO can, for example, miss some fields that
// are present in domain entity
// Output DTOs can make your microservice more secure
// because in case of a successful injection attack,
// output DTOs limit what data can be exposed to clients
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
