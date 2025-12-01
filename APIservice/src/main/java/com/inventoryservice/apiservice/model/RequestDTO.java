package com.inventoryservice.apiservice.model;


public class RequestDTO {

  private String url;
  private int message;
  private int concurrency;

  public RequestDTO(int message, int concurrency, String url) {
    this.message = message;
    this.concurrency = concurrency;
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getMessage() {
    return message;
  }

  public void setMessage(int message) {
    this.message = message;
  }

  public int getConcurrency() {
    return concurrency;
  }

  public void setConcurrency(int concurrency) {
    this.concurrency = concurrency;
  }

}
