package com.example.orderservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
  @Id
  private String id;

  private String salesItemId;
  private Integer quantity;

  @Transient
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "orderid", nullable = false)
  private Order order;
}
