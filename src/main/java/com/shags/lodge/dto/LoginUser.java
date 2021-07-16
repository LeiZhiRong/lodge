package com.shags.lodge.dto;

import java.util.List;

public class LoginUser {

  private String username;
  private String address;
  private String fdbh;
  private List<String> fdbharr;
  private String contentTitle;
  private String startDate;
  private String endDate;
  private String cardinalJe;
  private String exchangJe;
  private String acid;
  private String roles;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getFdbh() {
    return fdbh;
  }

  public void setFdbh(String fdbh) {
    this.fdbh = fdbh;
  }

  public List<String> getFdbharr() {
    return fdbharr;
  }

  public void setFdbharr(List<String> fdbharr) {
    this.fdbharr = fdbharr;
  }

  public String getContentTitle() {
    return contentTitle;
  }

  public void setContentTitle(String contentTitle) {
    this.contentTitle = contentTitle;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getCardinalJe() {
    return cardinalJe;
  }

  public void setCardinalJe(String cardinalJe) {
    this.cardinalJe = cardinalJe;
  }

  public String getExchangJe() {
    return exchangJe;
  }

  public void setExchangJe(String exchangJe) {
    this.exchangJe = exchangJe;
  }

  public String getAcid() {
    return acid;
  }

  public void setAcid(String acid) {
    this.acid = acid;
  }

  public LoginUser() {
    super();
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }
}

