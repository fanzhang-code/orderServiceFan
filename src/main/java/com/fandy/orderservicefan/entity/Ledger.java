package com.fandy.orderservicefan.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ledger")
public class Ledger {

    @Id
    @Column("ledgerId")
    private String ledgerId;
    @Column("customerId")
    private String customerId;
    @Column("orderId")
    private String orderId;
    @Column("transferAmount")//foreign key
    private Integer transferAmount;
    @Column("operationType")
    private String operationType;  // CHARGE/REFUND/...
    @Column("createTime")
    private String createTime;

    public Ledger() {}

    public Ledger(String ledgerId, String customerId, String orderId,
                 Integer transferAmount, String operationType, String createTime) {
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

    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }

}