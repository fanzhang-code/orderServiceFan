package com.fandy.orderservicefan.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@Component
public class DynamoDbTableInitializer implements CommandLineRunner {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDbTableInitializer(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public void run(String... args) {
        createTableIfNotExists("Orders", "orderId");
        createTableIfNotExists("Ledger", "ledgerId");
        createTableIfNotExists("IdempotencyRecords", "idempotencyKey");
        createTableIfNotExists("Items", "itemId");
    }

    private void createTableIfNotExists(String tableName, String partitionKeyName) {
        try {
            dynamoDbClient.describeTable(
                    DescribeTableRequest.builder()
                            .tableName(tableName)
                            .build()
            );

            System.out.println("DynamoDB table already exists: " + tableName);

        } catch (ResourceNotFoundException e) {
            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(KeySchemaElement.builder()
                            .attributeName(partitionKeyName)
                            .keyType(KeyType.HASH)
                            .build())
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName(partitionKeyName)
                            .attributeType(ScalarAttributeType.S)
                            .build())
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build();

            dynamoDbClient.createTable(request);

            dynamoDbClient.waiter()
                    .waitUntilTableExists(DescribeTableRequest.builder()
                            .tableName(tableName)
                            .build());

            System.out.println("Created DynamoDB table: " + tableName);
        }
    }
}
