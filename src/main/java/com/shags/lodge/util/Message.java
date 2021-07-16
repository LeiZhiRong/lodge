package com.shags.lodge.util;

public class Message {

  private Object data;
  private String message;
  private Integer code;

  public Message() {
    super();

  }

  public Message(Integer code, String message, Object data) {
    super();
    this.message = message;
    this.code = code;
    this.data = data;
  }

  public Message(Integer code, String message) {
    super();
    this.code = code;
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }
}

