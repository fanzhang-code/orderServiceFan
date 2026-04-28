package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.Order;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Optional;

@Repository
public class OrderRepository {

    private final DynamoDbTable<Order> table;

    public OrderRepository(DynamoDbTable<Order> orderTable) {
        this.table = orderTable;
    }

    public void save(Order order) {
        table.putItem(order);
    }

    public Optional<Order> findById(String orderId) {
        Order order = table.getItem(r -> r.key(k -> k.partitionValue(orderId)));
        return Optional.ofNullable(order);
    }
}
