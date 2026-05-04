package com.fandy.orderservicefan.controllers;

import com.fandy.orderservicefan.requests.CreateItemRequest;
import com.fandy.orderservicefan.responses.ItemResponse;
import com.fandy.orderservicefan.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody CreateItemRequest request) {
        ItemResponse response = itemService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable String itemId) {
        ItemResponse response = itemService.getItem(itemId);
        return ResponseEntity.ok(response);
    }
}
