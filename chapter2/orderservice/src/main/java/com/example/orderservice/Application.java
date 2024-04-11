package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
  // Define used database: "mongodb", "jdbc" or "sql"
  public static final String DATABASE_TYPE = "sql";

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
