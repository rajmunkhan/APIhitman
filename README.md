API Hitman

API Hitman is a lightweight two-microservice system designed to repeatedly hit user-provided URLs with controlled concurrency. Using Kafka for asynchronous processing, it allows users to simulate load, test performance, and collect metrics from any target endpoint.

🚀 Features

Hit any user-provided URL

Configure:

Total number of hits
Concurrency (parallel execution)
Asynchronous and reliable processing using Kafka
Consumer service executes the actual calls

Runtime metrics collection:
Success count
Failure count
Average latency

🧩 System Architecture
Client → APIservice → Kafka Topic (hitURL) → URLconsume → Target URL


Two microservices work together:

🟦 1. APIservice

Public-facing microservice that accepts the user's request.
Responsibilities
Exposes REST endpoint:
POST /v1/hit

Accepts configuration for:

url → The target URL to hit
message → Number of times to hit
concurrency → Number of parallel requests

Publishes validated requests to Kafka topic hitURL

Example Request
{
  "url": "https://google.com",
  "message": 200,
  "concurrency": 10
}

🟩 2. URLconsume

Backend worker microservice that processes queued requests.

Responsibilities
-> Consumes messages from Kafka topic hitURL
-> Executes the actual HTTP requests
-> Performs calls with user-specified concurrency

Collects and logs metrics such as:

success: number of successful responses
failure: number of failed responses
latency: average response time

📡 Workflow

-> Client sends payload to /v1/hit

-> APIservice pushes the request to Kafka

-> URLconsume reads the message

-> URLconsume hits the target URL based on:

total hits (message)

parallelism (concurrency)

Metrics are calculated and logged/output

🛠️ Tech Stack

Java 17+

Spring Boot

Apache Kafka

RestTemplate / WebClient

Maven
