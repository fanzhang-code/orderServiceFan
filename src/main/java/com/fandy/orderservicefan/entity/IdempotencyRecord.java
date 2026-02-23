package com.fandy.orderservicefan.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("idempotency_records")
public class IdempotencyRecord {

    @Id
    @Column("idempotencyKey")
    private String idempotencyKey;
    @Column("fingerprint")
    private String fingerprint;
    @Column("statusCode")
    private String statusCode;
    @Column("responseBody")
    private String responseBody;
    @Column("createTime")
    private String createTime;

    public IdempotencyRecord() {}

    public IdempotencyRecord(String idempotencyKey, String fingerprint, String statusCode, String responseBody, String createTime) {
        this.idempotencyKey = idempotencyKey;
        this.fingerprint = fingerprint;
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.createTime = createTime;
    }

    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }

    public String getFingerprint() { return fingerprint; }
    public void setFingerprint(String fingerprint) { this.fingerprint = fingerprint; }

    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }

    public String getResponseBody() { return responseBody; }
    public void setResponseBody(String responseBody) { this.responseBody = responseBody; }

    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }


}
