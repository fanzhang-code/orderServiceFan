package com.fandy.orderservicefan.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;

@Entity
@Table(name = "idempotency_records")
public class IdempotencyRecord {

    @Id
    @Column(name = "idempotency_key")
    private String idempotencyKey;
    @Column(name = "fingerprint")
    private String fingerprint;
    @Column(name = "status_code")
    private String statusCode;
    @Column(name = "response_body")
    private String responseBody;
    @Column(name = "create_time")
    private OffsetDateTime createTime;

    public IdempotencyRecord() {}

    public IdempotencyRecord(String idempotencyKey, String fingerprint, String statusCode, String responseBody, OffsetDateTime createTime) {
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

    public OffsetDateTime getCreateTime() { return createTime; }
    public void setCreateTime(OffsetDateTime createTime) { this.createTime = createTime; }


}
