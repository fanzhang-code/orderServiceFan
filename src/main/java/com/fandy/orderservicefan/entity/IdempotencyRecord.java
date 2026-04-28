package com.fandy.orderservicefan.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.OffsetDateTime;

@DynamoDbBean
public class IdempotencyRecord {

    private String idempotencyKey;
    private String fingerprint;
    private String statusCode;
    private String responseBody;
    private OffsetDateTime createTime;

    public IdempotencyRecord() {}

    public IdempotencyRecord(String idempotencyKey, String fingerprint, String statusCode,
                             String responseBody, OffsetDateTime createTime) {
        this.idempotencyKey = idempotencyKey;
        this.fingerprint = fingerprint;
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.createTime = createTime;
    }

    @DynamoDbPartitionKey
    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }
}
