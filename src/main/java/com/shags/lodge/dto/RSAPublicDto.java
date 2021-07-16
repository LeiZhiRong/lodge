package com.shags.lodge.dto;

public class RSAPublicDto {

  String modulus;
  String exponent;

  public String getModulus() {
    return modulus;
  }

  public void setModulus(String modulus) {
    this.modulus = modulus;
  }

  public String getExponent() {
    return exponent;
  }

  public void setExponent(String exponent) {
    this.exponent = exponent;
  }

  public RSAPublicDto() {
    super();
  }
}

