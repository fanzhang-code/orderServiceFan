package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.IdempotencyRecord;
import com.fandy.orderservicefan.entity.Order;
import org.komamitsu.spring.data.sqlite.SqliteRepository;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface IdempotencyRepository extends CrudRepository<IdempotencyRecord, String> {
    @Modifying
    @Query("""
      INSERT INTO idempotency_records (idempotencyKey, fingerprint, statusCode, responseBody, createTime)
      VALUES (:key, :fp, :status, :body, :created)
      ON CONFLICT(idempotencyKey) DO NOTHING
    """)
    int tryInsert(String key, String fp, String status, String body, String created);
}
