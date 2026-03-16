package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.IdempotencyRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IdempotencyRepository extends JpaRepository<IdempotencyRecord, String> {
}
