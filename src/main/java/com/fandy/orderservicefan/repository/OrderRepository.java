package com.fandy.orderservicefan.repository;

import com.fandy.orderservicefan.entity.Order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {
}
