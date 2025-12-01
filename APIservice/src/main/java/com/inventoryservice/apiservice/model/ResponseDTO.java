package com.inventoryservice.apiservice.model;

public class ResponseDTO {
  private String msg;

  public ResponseDTO(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
