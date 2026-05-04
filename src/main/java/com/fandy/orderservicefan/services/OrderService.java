package com.fandy.orderservicefan.services;

import com.fandy.orderservicefan.entity.IdempotencyRecord;
import com.fandy.orderservicefan.entity.Ledger;
import com.fandy.orderservicefan.entity.Order;
import com.fandy.orderservicefan.exceptions.AfterCommitFailureException;
import com.fandy.orderservicefan.exceptions.ConflictException;
import com.fandy.orderservicefan.exceptions.InProgressException;
import com.fandy.orderservicefan.repository.IdempotencyRepository;
import com.fandy.orderservicefan.repository.ItemStockRepository;
import com.fandy.orderservicefan.repository.LedgerRepository;
import com.fandy.orderservicefan.repository.OrderRepository;
import com.fandy.orderservicefan.requests.CreateOrderRequest;
import com.fandy.orderservicefan.responses.CreateOrderResponse;
import com.fandy.orderservicefan.responses.GetOrderResponse;
import com.fandy.orderservicefan.utils.FingerprintUtil;
import com.fandy.orderservicefan.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final LedgerRepository ledgerRepository;
    private final IdempotencyRepository idempotencyRepository;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final ItemStockRepository itemStockRepository;

    public OrderService(OrderRepository orderRepository, LedgerRepository ledgerRepository, IdempotencyRepository idempotencyRepository
    , ItemStockRepository itemStockRepository) {
        this.orderRepository = orderRepository;
        this.ledgerRepository = ledgerRepository;
        this.idempotencyRepository = idempotencyRepository;
        this.itemStockRepository = itemStockRepository;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest, String idempotencyKey, boolean failureTrigger){
        //cal fingerprint
        String fingerprint = FingerprintUtil.makeFingerprint(createOrderRequest);

        //create idempotency record
        IdempotencyRecord record = new IdempotencyRecord(idempotencyKey, fingerprint, null, null,
                java.time.OffsetDateTime.now());
        try {

            boolean inserted = idempotencyRepository.tryInsert(record);
            if (!inserted) {
                log.info("event=duplicate request with same idempotency key");
                //duplicate request
                IdempotencyRecord preRecord = idempotencyRepository.findById(idempotencyKey).orElse(null);
                if (preRecord == null) {
                    throw new RuntimeException("duplicate request but idempotency record not found");
                }
                //check payload
                if (!preRecord.getFingerprint().equals(fingerprint)) {
                    throw new ConflictException("payload mismatch");
                } else {
                    if (preRecord.getStatusCode() != null && preRecord.getResponseBody() != null) {
                        return JsonUtils.fromJson(preRecord.getResponseBody(), CreateOrderResponse.class);
                    } else {
                        throw new InProgressException("Request already in progress. Please retry it later.");
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }

        log.info("event=reserve_stock_attempt item_id={} quantity={}",
                createOrderRequest.getItemId(), createOrderRequest.getQuantity());

        //check availability
        try {
        itemStockRepository.reserveStock(
                createOrderRequest.getItemId(),
                createOrderRequest.getQuantity()
            );
        } catch (RuntimeException e) {
            CreateOrderResponse response = new CreateOrderResponse(
                    null,
                    "failed",
                    e.getMessage()
            );

            record.setStatusCode("409");
            record.setResponseBody(JsonUtils.toJson(response));
            idempotencyRepository.save(record);

            throw e;
        }
        log.info("event=reserve_stock_success item_id={} quantity={}",
                createOrderRequest.getItemId(), createOrderRequest.getQuantity());

        //update order table
        String orderId = UUID.randomUUID().toString();
        Order newOrder = new Order(orderId, createOrderRequest.getCustomerId(),
                createOrderRequest.getItemId(), createOrderRequest.getQuantity(), "created");
        orderRepository.save(newOrder);

        //update ledger table
        Ledger newLedger = new Ledger(UUID.randomUUID().toString(), createOrderRequest.getCustomerId(), orderId,
                createOrderRequest.getQuantity(), "CHARGE", java.time.OffsetDateTime.now());
        ledgerRepository.save(newLedger);

        //create response
        CreateOrderResponse response = new CreateOrderResponse(orderId, "created", "order successfully created");
        record.setStatusCode("201");
        record.setResponseBody(JsonUtils.toJson(response));
        idempotencyRepository.save(record);
        log.info("event=order_created order_id={} customer_id={} item_id={} quantity={}",
                orderId, newOrder.getCustomerId(), newOrder.getItemId(), newOrder.getQuantity());

        //include a failure trigger
        if(failureTrigger){
            throw new AfterCommitFailureException("failure after commit");
        }

        return response;
    }

    public GetOrderResponse getOrderDetail(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order not found: " + orderId));
        return new GetOrderResponse(order.getOrderId(), order.getCustomerId(), order.getItemId(), order.getQuantity(),
                order.getStatus());
    }

}
