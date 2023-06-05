package com.jcastellar.devsuChallenge.utility.enumerador;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum TipoCuenta {

  AHORRO("Ahorro"), CORRIENTE("Corriente");

  private final String value;

  TipoCuenta(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

}