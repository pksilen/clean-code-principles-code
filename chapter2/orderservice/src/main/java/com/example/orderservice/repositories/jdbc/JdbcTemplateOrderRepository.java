package com.example.orderservice.repositories.jdbc;


import com.example.orderservice.errors.DatabaseError;
import com.example.orderservice.repositories.DbOrder;
import com.example.orderservice.repositories.DbOrderItem;
import com.example.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Qualifier("jdbc")
@Service
public class JdbcTemplateOrderRepository implements OrderRepository {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public JdbcTemplateOrderRepository(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    tryCreateTablesIfNeeded();
  }

  public <S extends DbOrder> S save(final S dbOrder) {
    if (existsById(dbOrder.getId())) {
      update(dbOrder);
    } else {
      insert(dbOrder);
    }

    return dbOrder;
  }

  public Optional<DbOrder> findById(final String id) {
    final var idToDbOrder = new HashMap<String, DbOrder>();

    try {
      final List<DbOrder> dbOrders = jdbcTemplate.query(
        "SELECT o.id, o.userAccountId, " +
          "oi.id as orderItemId, oi.salesItemId as salesItemId, oi.quantity as quantity " +
          "FROM orders o LEFT JOIN orderitems oi ON oi.orderId = o.id WHERE o.id = ?",
        (resultSet, rowNum) -> createOrUpdateDbOrder(resultSet, idToDbOrder),
        id
      ).stream().distinct().toList();

      return dbOrders.size() == 1 ? Optional.of(dbOrders.get(0)) : Optional.empty();
    } catch (final DataAccessException error) {
      throw new DatabaseError(error);
    }
  }

  public boolean existsById(final String id) {
    final var dbOrder = findById(id);
    return dbOrder.isPresent();
  }

  public Page<DbOrder> findByUserAccountId(final String userAccountId, final Pageable pageable) {
    final var idToDbOrder = new HashMap<String, DbOrder>();

    try {
      final var dbOrders = jdbcTemplate.query(
        "SELECT o.id, o.userAccountId, " +
          "oi.id as orderItemId, oi.salesItemId as salesItemId, oi.quantity as quantity " +
          "FROM orders o LEFT JOIN orderitems oi ON oi.orderId = o.id WHERE o.userAccountId = ? " +
          "LIMIT " + pageable.getOffset() + "," + pageable.getPageSize(),
        (resultSet, rowNum) -> createOrUpdateDbOrder(resultSet, idToDbOrder),
        userAccountId
      ).stream().distinct().toList();

      return new PageImpl<>(dbOrders);
    } catch (final DataAccessException error) {
      throw new DatabaseError(error);
    }
  }

  public void deleteById(final String id) {
    try {
      jdbcTemplate.update("DELETE FROM orderitems WHERE orderId = ?", id);
      jdbcTemplate.update("DELETE FROM orders WHERE id = ?", id);
    } catch (final DataAccessException error) {
      throw new DatabaseError(error);
    }
  }

  private void tryCreateTablesIfNeeded() {
    final var createOrdersTableQuery = """
      CREATE TABLE IF NOT EXISTS orders (
        id VARCHAR(36) NOT NULL,
        userAccountId VARCHAR(36) NOT NULL,
        PRIMARY KEY (id)
      )
      """;

    jdbcTemplate.execute(createOrdersTableQuery);

    final var createOrderItemsTableQuery = """
        CREATE TABLE IF NOT EXISTS orderitems (
          id VARCHAR(36) NOT NULL,
          salesItemId VARCHAR(36) NOT NULL,
          quantity INTEGER NOT NULL,
          orderId VARCHAR(36) NOT NULL,
          PRIMARY KEY (id),
          FOREIGN KEY (orderId) REFERENCES orders(id)
        )
      """;

    jdbcTemplate.execute(createOrderItemsTableQuery);
  }

  private void update(final DbOrder dbOrder) {
    try {
      jdbcTemplate.update(
        "UPDATE orders SET userAccountId = ? WHERE id = ?",
        dbOrder.getUserAccountId(),
        dbOrder.getId()
      );

      jdbcTemplate.update("DELETE FROM orderitems where orderId = ?", dbOrder.getId());
      insertOrderItems(dbOrder.getId(), dbOrder.getOrderItems());
    } catch (final DataAccessException error) {
      throw new DatabaseError(error);
    }
  }

  private void insert(final DbOrder dbOrder) {
    try {
      jdbcTemplate.update(
        "INSERT INTO orders (id, userAccountId) VALUES (?, ?)",
        dbOrder.getId(),
        dbOrder.getUserAccountId()
      );

      insertOrderItems(dbOrder.getId(), dbOrder.getOrderItems());
    } catch (final DataAccessException error) {
      throw new DatabaseError(error);
    }
  }

  private void insertOrderItems(
    final String orderId, final List<DbOrderItem> orderItems
  ) {

    final var insertOrderItemStatement =
      "INSERT INTO orderitems (id, salesItemId, quantity, orderId) VALUES (?, ?, ?, ?)";

    for (final var orderItem : orderItems) {
      try {
        jdbcTemplate.update(insertOrderItemStatement, orderItem.getId(), orderItem.getSalesItemId(), orderItem.getQuantity(),
          orderId
        );
      } catch (final DataAccessException error) {
        throw new DatabaseError(error);
      }
    }
  }

  private DbOrder createOrUpdateDbOrder(final ResultSet resultSet, final Map<String, DbOrder> idToDbOrder) {
    try {
      final var dbOrderItem = new DbOrderItem(
        resultSet.getString("orderItemId"),
        resultSet.getString("salesItemId"),
        resultSet.getInt("quantity"),
        null
      );

      final var orderId = resultSet.getString("id");
      final var existingDbOrder = idToDbOrder.get(orderId);

      if (existingDbOrder == null) {
        final var dbOrder = new DbOrder(
          resultSet.getString("id"),
          resultSet.getString("userAccountId"),
          new ArrayList<>(List.of(dbOrderItem))
        );

        idToDbOrder.put(orderId, dbOrder);
      } else {
        existingDbOrder.getOrderItems().add(dbOrderItem);
      }

      return idToDbOrder.get(orderId);
    } catch (final SQLException error) {
      throw new DatabaseError(error);
    }
  }
}
