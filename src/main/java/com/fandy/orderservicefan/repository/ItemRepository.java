package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.Item;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Optional;

@Repository
public class ItemRepository {

    private final DynamoDbTable<Item> itemTable;

    public ItemRepository(DynamoDbTable<Item> itemTable) {
        this.itemTable = itemTable;
    }

    public Item save(Item item) {
        itemTable.putItem(item);
        return item;
    }

    public Optional<Item> findById(String itemId) {
        Item item = itemTable.getItem(
                Key.builder()
                        .partitionValue(itemId)
                        .build()
        );

        return Optional.ofNullable(item);
    }
}