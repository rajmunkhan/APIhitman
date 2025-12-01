# API Hitman

API Hitman is a lightweight two-microservice system designed to repeatedly hit user-provided URLs with controlled concurrency. Using Kafka for asynchronous processing, it allows users to simulate load, test performance, and collect metrics from any target endpoint.

---

## 🚀 Features

- Hit **any user-provided URL**
- Configure:
  - Total number of hits
  - Concurrency (parallel execution)
- Asynchronous and reliable processing using Kafka
- Consumer service executes the actual calls
- Runtime metrics collection:
  - Success count
  - Failure count
  - Average latency

---

## 🧩 System Architecture

Client → APIservice → Kafka Topic (hitURL) → URLconsume → Target URL


The system has two microservices:

---

## 1. APIservice

Public-facing microservice that accepts the user's request.

### Responsibilities

- Exposes REST endpoint:  
  **POST** `/v1/hit`
- Accepts configuration:
  - `url` — The target URL to hit  
  - `message` — Total number of times to hit the URL  
  - `concurrency` — Number of parallel requests  
- Publishes validated requests to Kafka topic `hitURL`

### Example Request

```json
{
  "url": "https://google.com",
  "message": 200,
  "concurrency": 10
}
```

## 2. URLconsume

Backend worker microservice that processes queued requests.

### Responsibilities

- Consumes messages from Kafka topic `hitURL`
- Executes actual HTTP requests
- Performs calls using the provided concurrency level
- Collects and logs metrics:
  - success count
  - failure count
  - average latency

---

## 📡 Workflow

1. Client sends payload to `/v1/hit`
2. APIservice pushes the request to Kafka
3. URLconsume reads the message
4. URLconsume hits the target URL according to:
   - total hits (`message`)
   - parallelism (`concurrency`)
5. Metrics are computed and logged

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Apache Kafka
- RestTemplate / WebClient
- Maven

