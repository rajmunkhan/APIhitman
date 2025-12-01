package com.urlconsumer.urlconsume.kafkaConsumer;

import com.inventoryservice.apiservice.model.KafkaResponse;
import com.urlconsumer.urlconsume.DisableSSL;
import com.urlconsumer.urlconsume.model.HitResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class KafkaConsumer {
  @Autowired
  DisableSSL disableSSL;

  @KafkaListener(topics="hitURL", groupId = "callURL-group")
  public void consumeTopic(KafkaResponse res){
    System.out.println(res.getUrl());

    int messages = res.getMessage();         // number of hits
    int concurrency = 5;  // max parallel threads

    ExecutorService executor = Executors.newFixedThreadPool(concurrency);

    List<Future<HitResult>> futures = new ArrayList<>();

    for(int i=0;i<messages;i++){
      futures.add(executor.submit(() -> hitURL(res.getUrl())));
    }

    List<HitResult> results = futures.stream()
        .map(f -> {
          try {
            return f.get();
          } catch (Exception e) {
            return new HitResult(false, 0, 0, e.getMessage());
          }
        })
        .toList();

    executor.shutdown();

    // Aggregate stats
    long success = results.stream().filter(HitResult::isSuccess).count();
    long fail = results.size() - success;
    long avgLatency = (long) results.stream().mapToLong(HitResult::getLatency).average().orElse(0);

    log.info("URL Hit Summary -> Success: {}, Fails: {}, Avg Latency: {} ms", success, fail, avgLatency);

  }

  private HitResult hitURL(String rawUrl) throws Exception {
    disableSSL.disableSSLValidation();
    String link = rawUrl.startsWith("http") ? rawUrl : "http://" + rawUrl;

    long start = System.currentTimeMillis();

    try {
      HttpURLConnection conn = (HttpURLConnection) new URL(link).openConnection();
      conn.setConnectTimeout(30000);
      conn.setReadTimeout(30000);

      int code = conn.getResponseCode();
      long latency = System.currentTimeMillis() - start;

      boolean success = code >= 200 && code < 300;

      return new HitResult(success, code, latency, null);

    } catch (Exception ex) {
      log.error("URL FAILED: {} | Error: {}", link, ex.getMessage());
      long latency = System.currentTimeMillis() - start;
      return new HitResult(false, 0, latency, ex.getMessage());
    }

  }
}
