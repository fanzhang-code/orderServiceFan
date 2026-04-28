package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.IdempotencyRecord;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.enhanced.dynamodb.Expression;

import java.util.Optional;

@Repository
public class IdempotencyRepository {

    private final DynamoDbTable<IdempotencyRecord> table;

    public IdempotencyRepository(DynamoDbTable<IdempotencyRecord> idempotencyRecordTable) {
        this.table = idempotencyRecordTable;
    }

    public boolean tryInsert(IdempotencyRecord record) {
        try {
            table.putItem(r -> r
                    .item(record)
                    .conditionExpression(Expression.builder()
                            .expression("attribute_not_exists(idempotencyKey)")
                            .build())
            );
            return true;
        } catch (ConditionalCheckFailedException e) {
            return false;
        }
    }

    public Optional<IdempotencyRecord> findById(String idempotencyKey) {
        IdempotencyRecord record = table.getItem(r -> r
                .key(k -> k.partitionValue(idempotencyKey))
        );

        return Optional.ofNullable(record);
    }

    public void save(IdempotencyRecord record) {
        table.putItem(record);
    }
}
