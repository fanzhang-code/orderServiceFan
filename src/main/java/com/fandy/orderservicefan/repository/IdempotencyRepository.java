package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.IdempotencyRecord;
import com.fandy.orderservicefan.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface IdempotencyRepository extends CrudRepository<IdempotencyRecord, String> {
}
