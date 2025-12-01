package com.inventoryservice.apiservice.kafka;

import com.inventoryservice.apiservice.model.KafkaResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

//  @Bean
//  public NewTopic createTopic(){
//    return new NewTopic("hitURL", 5, (short) 1);
//  }

  @Bean
  public Map<String,Object> producerConfig(){
    Map<String,Object> props=new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        "localhost:9092");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        JsonSerializer.class);
    return props;
  }

  @Bean
  public ProducerFactory<String, KafkaResponse> producerFactory(){
    return new DefaultKafkaProducerFactory<>(producerConfig());
  }

  @Bean
  public KafkaTemplate<String,KafkaResponse> kafkaTemplate(){
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put("bootstrap.servers", "localhost:9092");
    return new KafkaAdmin(configs);
  }

}