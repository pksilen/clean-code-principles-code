package com.example.orderservice.entities;

import com.example.orderservice.dtos.InputOrder;
import com.example.orderservice.dtos.OutputOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;


// Domain entity than can contain business logic
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private String id;
  private String userAccountId;
  private List<OrderItem> orderItems;

  // Domain entity factory method
  //
  // You could instantiate different types of domain entities here
  // Suppose we had a 'type' field in the input order.
  // Based on that field's value, we could create either
  // `BusinessOrder` or `ConsumerOrder` domain entity instance.
  // `BusinessOrder` or `ConsumerOrder` classes could derive from
  // a common `Order` domain entity class
  public static Order from(final InputOrder inputOrder) {
    // Perform validation of dynamic business rules here
    // If you have a lot of validations, put them in
    // separate class(es)
    
    final var order = new ModelMapper().map(inputOrder, Order.class);

    // Generating entity id on server side is good practice
    // for high security and distributed databases
    // Having all ids as string allows represents ids
    // consistently regardless of database engine and programming
    // languages used
    order.setId(UUID.randomUUID().toString());

    return order;
  }

  public static Order from(
    final InputOrder inputOrder,
    final String id
  ) {
    final var order = new ModelMapper().map(inputOrder, Order.class);
    order.setId(id);
    return order;
  }

  public OutputOrder toOutput() {
    return new ModelMapper().map(this, OutputOrder.class);
  }

  // Implement business logic as additional methods here...
}
