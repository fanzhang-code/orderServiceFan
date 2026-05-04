package com.fandy.orderservicefan.services;

import com.fandy.orderservicefan.entity.Item;
import com.fandy.orderservicefan.repository.ItemRepository;
import com.fandy.orderservicefan.requests.CreateItemRequest;
import com.fandy.orderservicefan.responses.ItemResponse;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private static final Logger log = LoggerFactory.getLogger(ItemService.class);

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemResponse createItem(CreateItemRequest request) {
        Item item = new Item(
                request.getItemId(),
                request.getName(),
                request.getPrice(),
                request.getAvailableQuantity()
        );

        itemRepository.save(item);
        log.info("event=item_created item_id={} name={} price={} quantity={}",
                item.getItemId(),
                item.getName(),
                item.getPrice(),
                item.getAvailableQuantity());

        return toResponse(item);
    }

    public ItemResponse getItem(String itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found: " + itemId));
        log.info("event=item_found item_id={} quantity={}",
                item.getItemId(),
                item.getAvailableQuantity());

        return toResponse(item);
    }

    private ItemResponse toResponse(Item item) {
        return new ItemResponse(
                item.getItemId(),
                item.getName(),
                item.getPrice(),
                item.getAvailableQuantity()
        );
    }
}
