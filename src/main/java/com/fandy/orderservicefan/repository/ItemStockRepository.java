package com.fandy.orderservicefan.repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;

@Repository
public class ItemStockRepository {

    private final DynamoDbClient dynamoDbClient;

    public ItemStockRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void reserveStock(String itemId, int quantity) {
        try {
            dynamoDbClient.updateItem(UpdateItemRequest.builder()
                    .tableName("Items")
                    .key(Map.of(
                            "itemId", AttributeValue.builder().s(itemId).build()
                    ))
                    .updateExpression("SET availableQuantity = availableQuantity - :quantity")
                    .conditionExpression("attribute_exists(itemId) AND availableQuantity >= :quantity")
                    .expressionAttributeValues(Map.of(
                            ":quantity", AttributeValue.builder().n(String.valueOf(quantity)).build()
                    ))
                    .build());
        } catch (ConditionalCheckFailedException e) {
            throw new RuntimeException("Not enough stock or item does not exist: " + itemId);
        }
    }
}