package com.shags.lodge.util;

public class SelectJson {

  private String id;
  private String name;
  private String values;
  private String str;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValues() {
    return values;
  }

  public void setValues(String values) {
    this.values = values;
  }

  public String getStr() {
    return str;
  }

  public void setStr(String str) {
    this.str = str;
  }

  public SelectJson() {
    super();
  }

  public SelectJson(String id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public SelectJson(String id, String name, String values) {
    super();
    this.id = id;
    this.name = name;
    this.values = values;
  }

  public SelectJson(String id, String name, String values, String str) {
    this.id = id;
    this.name = name;
    this.values = values;
    this.str = str;
  }
}

