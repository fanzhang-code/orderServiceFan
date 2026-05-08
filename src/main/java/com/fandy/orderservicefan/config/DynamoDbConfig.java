package com.fandy.orderservicefan.config;

import com.fandy.orderservicefan.entity.IdempotencyRecord;
import com.fandy.orderservicefan.entity.Item;
import com.fandy.orderservicefan.entity.Ledger;
import com.fandy.orderservicefan.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;

@Configuration
public class DynamoDbConfig {
    @Value("${aws.dynamodb.endpoint}")
    private String dynamoDbEndpoint;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public DynamoDbClient dynamoDbClient() {

        DynamoDbClientBuilder builder = DynamoDbClient.builder()
                .region(Region.of(awsRegion));

        if (dynamoDbEndpoint != null && !dynamoDbEndpoint.isBlank()) {
            builder.endpointOverride(URI.create(dynamoDbEndpoint));

            builder.credentialsProvider(
                    StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("dummy", "dummy")
                    )
            );
        }

        return builder.build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public DynamoDbTable<IdempotencyRecord> idempotencyRecordTable(
            DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table(
                "IdempotencyRecords",
                TableSchema.fromBean(IdempotencyRecord.class)
        );
    }

    @Bean
    public DynamoDbTable<Order> orderTable(
            DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table(
                "Orders",
                TableSchema.fromBean(Order.class)
        );
    }

    @Bean
    public DynamoDbTable<Ledger> ledgerTable(
            DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table(
                "Ledger",
                TableSchema.fromBean(Ledger.class)
        );
    }

    @Bean
    public DynamoDbTable<Item> itemTable(
            DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table(
                "Items",
                TableSchema.fromBean(Item.class)
        );
    }
}