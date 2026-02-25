package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.Ledger;
import com.fandy.orderservicefan.entity.Order;
import org.komamitsu.spring.data.sqlite.SqliteRepository;
import org.springframework.data.repository.CrudRepository;

public interface LedgerRepository extends SqliteRepository<Ledger, String> {
}
