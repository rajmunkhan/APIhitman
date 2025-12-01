package com.inventoryservice.apiservice.model;

public class KafkaResponse {

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  private int message;
  private String url;
  private String method;

  public KafkaResponse(int message, String url, String method) {
    this.message = message;
    this.url = url;
    this.method = method;
  }

  public KafkaResponse() {
  }

  public int getMessage() {
    return message;
  }

  public void setMessage(int message) {
    this.message = message;
  }
}
