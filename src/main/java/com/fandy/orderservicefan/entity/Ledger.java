package com.fandy.orderservicefan.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;


import java.time.OffsetDateTime;

@Entity
@Table(name = "ledger")
public class Ledger {

    @Id
    @Column(name ="ledger_id")
    private String ledgerId;
    @Column(name ="customer_id")
    private String customerId;
    @Column(name ="order_id")
    private String orderId;
    @Column(name ="transfer_amount")//foreign key
    private Integer transferAmount;
    @Column(name ="operation_type")
    private String operationType;  // CHARGE/REFUND/...
    @Column(name ="create_time")
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

    public String getLedgerId() { return ledgerId; }
    public void setLedgerId(String LedgerId) { this.ledgerId = LedgerId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public Integer getTransferAmount() { return transferAmount; }
    public void setTransferAmount(Integer transferAmount) { this.transferAmount = transferAmount; }

    public String getOperationType() { return operationType; }
    public void setOperationType(String operationType) { this.operationType = operationType; }

    public OffsetDateTime getCreateTime() { return createTime; }
    public void setCreateTime(OffsetDateTime createTime) { this.createTime = createTime; }

}