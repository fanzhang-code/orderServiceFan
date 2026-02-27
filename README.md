## AWS EC2 Configuration

Instance type: t2.micro

Operating System: Amazon Linux

Region: us-east-2b

## Security Group Configuration

| Type       | Port | Source    | 
| ---------- | ---- | --------- | 
| SSH        | 22   | 0.0.0.0/0 |
| Custom TCP | 8080 | 0.0.0.0/0 |

IP address: 3.129.12.251

## How to test

1. Create order:
   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-123" \
  -d '{"customer_id":"cust1","item_id":"item1","quantity":1}'

3. Avoid duplicate order (create order with same idempotency key)
   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-123" \
  -d '{"customer_id":"cust1","item_id":"item1","quantity":1}'

5. Same key, different payload
   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-123" \
  -d '{"customer_id":"cust1","item_id":"item1","quantity":5}'

7. Test failure after commit
   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-fail-1" \
  -H "X-Debug-Fail-After-Commit: true" \
  -d '{"customer_id":"cust2","item_id":"item2","quantity":1}'

9. Retry after simulated failure
    
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-fail-1" \
  -d '{"customer_id":"cust2","item_id":"item2","quantity":1}'

10. Fetch order details
    
curl http://3.129.12.251:8080/orders/<order_id>
