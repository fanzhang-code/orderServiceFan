package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.IdempotencyRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface IdempotencyRepository extends JpaRepository<IdempotencyRecord, String> {
    @Modifying
    @Query(value = """
        INSERT INTO idempotency_records
            (idempotency_key, fingerprint, status_code, response_body, create_time)
        VALUES
            (:idempotencyKey, :fingerprint, :statusCode, :responseBody, :createTime)
        ON CONFLICT (idempotency_key) DO NOTHING
        """, nativeQuery = true)
    int tryInsert(
            @Param("idempotencyKey") String idempotencyKey,
            @Param("fingerprint") String fingerprint,
            @Param("statusCode") String statusCode,
            @Param("responseBody") String responseBody,
            @Param("createTime") OffsetDateTime createTime
    );
}
