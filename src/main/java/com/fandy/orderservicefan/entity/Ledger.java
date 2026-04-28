package com.fandy.orderservicefan.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.OffsetDateTime;

@DynamoDbBean
public class Ledger {

    private String ledgerId;
    private String customerId;
    private String orderId;
    private Integer transferAmount;
    private String operationType;
    private OffsetDateTime createTime;

    public Ledger() {}

    public Ledger(String ledgerId, String customerId, String orderId,
                  Integer transferAmount, String operationType, OffsetDateTime createTime) {
        this.ledgerId = ledgerId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.transferAmount = transferAmount;
        this.operationType = operationType;
        this.createTime = createTime;
    }

    @DynamoDbPartitionKey
    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }
}