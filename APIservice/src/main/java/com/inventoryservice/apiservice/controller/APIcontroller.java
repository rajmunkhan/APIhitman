package com.inventoryservice.apiservice.controller;

import com.inventoryservice.apiservice.kafka.KafkaProducer;
import com.inventoryservice.apiservice.model.RequestDTO;
import com.inventoryservice.apiservice.model.KafkaResponse;
import com.inventoryservice.apiservice.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/hit")
public class APIcontroller {

  @Autowired
  private KafkaProducer kafkaProducer;

  @PostMapping
  public ResponseEntity<ResponseDTO> hitAPI(@RequestBody RequestDTO req){

    kafkaProducer.sendOrderEvent(req);
    return ResponseEntity.ok().body(new ResponseDTO("working..."));
  }
}
