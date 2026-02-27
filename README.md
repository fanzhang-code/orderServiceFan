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

## How to deploy

1. Run `.\mvnw.cmd clean package -DskipTests` in local Intellij to build the package
2. In powershell, use command: `scp -i C:\Users\<userName>\order-service-key.pem LocalPath\orderServiceFan-0.0.1-SNAPSHOT.jar ec2-user@3.129.12.251:/home/ec2-user/` to copy it to the EC2 instance
3. Connect to the EC2 instance, run the server using: `nohup java -jar orderServiceFan-0.0.1-SNAPSHOT.jar > app.log 2>&1 &`
4. `tail -f app.log`  to show logs
5. To access the database on EC2, use `sqlite3 orders.db` 

## How to test

For MAC:

1. Create order:
````   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-123" \
  -d '{"customer_id":"cust1","item_id":"item1","quantity":1}'
````
2. Avoid duplicate order (create order with same idempotency key)
````   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-123" \
  -d '{"customer_id":"cust1","item_id":"item1","quantity":1}'
````
3. Same key, different payload
````   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-123" \
  -d '{"customer_id":"cust1","item_id":"item1","quantity":5}'
````
4. Test failure after commit
````   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-fail-1" \
  -H "X-Debug-Fail-After-Commit: true" \
  -d '{"customer_id":"cust2","item_id":"item2","quantity":1}'
````
5. Retry after simulated failure
````    
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-fail-1" \
  -d '{"customer_id":"cust2","item_id":"item2","quantity":1}'
````
6. Fetch order details
````    
curl http://3.129.12.251:8080/orders/<order_id>
````
For Windows PowerShell:

1. Create order:
````
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-123" \
  -d '{\"customer_id\":\"cust1\",\"item_id\":\"item1\",\"quantity\":1}'
````

2. Avoid duplicate order (create order with same idempotency key)
````   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-123" \
  -d '{\"customer_id\":\"cust1\",\"item_id\":\"item1\",\"quantity\":1}'
````
3. Same key, different payload
````   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-123" \
  -d '{\"customer_id\":\"cust1\",\"item_id\":\"item1\",\"quantity\":5}'
````
4. Test failure after commit
````   
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-fail-1" \
  -H "X-Debug-Fail-After-Commit: true" \
  -d '{\"customer_id\":\"cust2\",\"item_id\":\"item2\",\"quantity\":1}'
````
5. Retry after simulated failure
````    
curl -i -X POST http://3.129.12.251:8080/orders \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: test-fail-1" \
  -d '{\"customer_id\":\"cust2\",\"item_id\":\"item2\",\"quantity\":1}'
````
6. Fetch order details
````    
curl http://3.129.12.251:8080/orders/<order_id>
````
