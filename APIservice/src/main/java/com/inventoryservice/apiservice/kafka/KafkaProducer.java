package com.inventoryservice.apiservice.kafka;

import com.inventoryservice.apiservice.model.KafkaResponse;
import com.inventoryservice.apiservice.model.RequestDTO;
import com.inventoryservice.apiservice.model.ResponseDTO;
import org.apache.commons.logging.Log;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
//import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaProducer {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
  private static final String TOPIC = "hitURL";

  @Autowired
  private KafkaTemplate<String, KafkaResponse> kafkaTemplate;

  @Autowired
  private KafkaAdmin kafkaAdmin;

  public void sendOrderEvent(RequestDTO req) {
    KafkaResponse kafkaResponse = new KafkaResponse(req.getMessage(),req.getUrl(), "POST");

    NewTopic topic = new NewTopic(TOPIC, req.getConcurrency(), (short)1);
    kafkaAdmin.createOrModifyTopics(topic);

//    for(int i=0;i<req.getMessage();i++){
      LOGGER.info("Sending event -> {}", kafkaResponse.getMessage());
      kafkaTemplate.send(TOPIC, kafkaResponse);
//    }
  }
}
