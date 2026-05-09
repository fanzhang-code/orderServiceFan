## Core Architecture

# **Now the code has been changed for the final project. To see the detailed readme for the final project, you can check https://github.com/ryanchowdev/checkout-service-lambda/blob/main/README.md**

Migrated OrderService in HW2 to use Postgres.

Local: Docker Compose (API + Postgres Container)

Cloud: Client → Application Load Balancer (ALB) → ECS → RDS Postgres

## Local Setup & Execution

Prerequisites: Docker and Docker Compose installed.

Run Instructions

Environment: Ensure a .env file exists in the root (excluded from git) with parameters: POSTGRES_DB, POSTGRES_USER and POSTGRES_PASSWORD.

Boot System:

docker compose up -d --build

Health check:

```
curl.exe -i http://localhost:8080/health
```

Example request (Windows PowerShell):

- Create new order

```
curl.exe -i -X POST http://localhost:8080/orders `
   -H "Content-Type: application/json" `
   -H "Idempotency-Key: fandy-345" `
   -d '{\"customer_id\":\"test1\",\"item_id\":\"testitem1\",\"quantity\":4}'
```

- Get order info


```
curl.exe -i -X GET http://localhost:8080/orders/<orderId>
```

Check local Persistence:

Data remains after docker compose restart api

Database Migrations: 

Migrations are handled via Flyway and execute automatically on startup to establish the schema.

## AWS Deployment Configuration

ECS deployment config(Json) saved in /deployment

Use SSM Parameter Store and injected into the ECS Task Definition: /order-service/DB_PASSWORD

Public ALB URL: http://order-service-alb-2118743509.us-east-2.elb.amazonaws.com

ECS Service Name: order-service-api

Database Type: Managed RDS Postgres (db.t3.micro)

Instance Type: ECS Fargate (0.5 vCPU, 1GB RAM)

### Engineering Features

Health Check: GET /health validates both application readiness and RDS connectivity.

Secrets: DB password is provided via AWS SSM Parameter Store and injected into the ECS Task Definition using valueFrom.

Observability: CloudWatch Logs are enabled for the ECS task.

AWS health check:

```
curl.exe -i http://order-service-alb-2118743509.us-east-2.elb.amazonaws.com/health
```

Create new order:

```
curl.exe -i -X POST http://order-service-alb-2118743509.us-east-2.elb.amazonaws.com/orders `
   -H "Content-Type: application/json" `
   -H "Idempotency-Key: fandy-1345" `
   -d '{\"customer_id\":\"test1\",\"item_id\":\"testitem1\",\"quantity\":13}'
```

Get order info:

```
curl.exe -i -X GET http://order-service-alb-2118743509.us-east-2.elb.amazonaws.com/orders/<orderId>
```
### Load Test Results(Using k6):

Run loadtest.js (in root folder)

- Test Configuration

Concurrency: 10 Virtual Users

Duration: 30 Seconds

Rate: 9.17 Requests Per Second (RPS)

Total Requests: 280

- Performance Metrics

| Metric         | Value            |
|----------------|------------------|
| Avg Latency    | 81.73 ms         |
| p(90) Latency  | 86.95 ms         |
| p(95) Latency  | 90.62 ms         |
| Max Latency    | 130.92 ms        |
| Success Rate   | 100% (280/280)   |

Bottleneck: The main bottleneck is the geographical distance between the my location and the datacenters in AWS region (us-east-2), accounting for the ~80ms network latency.
