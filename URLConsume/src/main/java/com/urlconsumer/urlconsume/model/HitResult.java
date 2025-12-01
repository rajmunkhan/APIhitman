package com.urlconsumer.urlconsume.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HitResult {
  private boolean success;
  private int statusCode;
  private long latency;       // ms
  private String error;
}