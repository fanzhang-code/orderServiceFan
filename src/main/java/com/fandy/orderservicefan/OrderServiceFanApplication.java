package com.fandy.orderservicefan;


import org.komamitsu.spring.data.sqlite.EnableSqliteRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSqliteRepositories
public class OrderServiceFanApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceFanApplication.class, args);
    }

}
