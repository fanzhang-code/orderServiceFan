package com.fandy.orderservicefan.services;

import com.fandy.orderservicefan.entity.IdempotencyRecord;
import com.fandy.orderservicefan.entity.Ledger;
import com.fandy.orderservicefan.entity.Order;
import com.fandy.orderservicefan.exceptions.ConflictException;
import com.fandy.orderservicefan.exceptions.InProgressException;
import com.fandy.orderservicefan.repository.IdempotencyRepository;
import com.fandy.orderservicefan.repository.LedgerRepository;
import com.fandy.orderservicefan.repository.OrderRepository;
import com.fandy.orderservicefan.requests.CreateOrderRequest;
import com.fandy.orderservicefan.responses.CreateOrderResponse;
import com.fandy.orderservicefan.utils.FingerprintUtil;
import com.fandy.orderservicefan.utils.JsonUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final LedgerRepository ledgerRepository;
    private final IdempotencyRepository idempotencyRepository;

    public OrderService(OrderRepository orderRepository, LedgerRepository ledgerRepository, IdempotencyRepository idempotencyRepository) {
        this.orderRepository = orderRepository;
        this.ledgerRepository = ledgerRepository;
        this.idempotencyRepository = idempotencyRepository;
    }

    //include a failure trigger
    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest, String idempotencyKey, boolean failureTrigger){
        CreateOrderResponse response = createOrder(createOrderRequest, idempotencyKey);

        if(failureTrigger){
            throw new RuntimeException("failure after commit");
        }

        return response;
    }

    @Transactional
    protected CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest, String idempotencyKey){
        //cal fingerprint
        String fingerprint = FingerprintUtil.makeFingerprint(createOrderRequest);

        //create idempotency record
        IdempotencyRecord record = new IdempotencyRecord(idempotencyKey, fingerprint, null, null,
                Instant.now().toString());
        try {
            idempotencyRepository.save(record);
        } catch (DuplicateKeyException e) {
            //duplicate request
            IdempotencyRecord preRecord = idempotencyRepository.findById(idempotencyKey).orElse(null);
            if(preRecord == null){
                throw new RuntimeException("duplicate request but idempotency record not found");
            }
            //check payload
            if(!preRecord.getFingerprint().equals(fingerprint)){
                throw new ConflictException("payload mismatch");
            }else{
                if(preRecord.getStatusCode() != null && preRecord.getResponseBody() != null){
                    return JsonUtils.fromJson(preRecord.getResponseBody(), CreateOrderResponse.class);
                }else{
                    throw new InProgressException("Request already in progress. Please retry it later.");
                }
            }
        }

        //update order table
        String orderId = UUID.randomUUID().toString();
        Order newOrder = new Order(orderId, createOrderRequest.getCustomerId(),
                createOrderRequest.getItemId(), createOrderRequest.getQuantity(), "created");
        orderRepository.save(newOrder);

        //update ledger table
        Ledger newLedger = new Ledger(UUID.randomUUID().toString(), createOrderRequest.getCustomerId(), orderId,
                createOrderRequest.getQuantity(), "CHARGE", Instant.now().toString());
        ledgerRepository.save(newLedger);

        //create response
        CreateOrderResponse response = new CreateOrderResponse(orderId, "created", "order successfully created");
        record.setStatusCode("201");
        record.setResponseBody(JsonUtils.toJson(response));
        idempotencyRepository.save(record);

        return response;
    }
}
