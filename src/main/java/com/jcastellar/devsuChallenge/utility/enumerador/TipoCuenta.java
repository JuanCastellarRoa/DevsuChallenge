package com.jcastellar.devsuChallenge.utility.enumerador;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TipoCuenta {

  AHORRO("Ahorro"), CORRIENTE("Corriente");

  private final String value;

  TipoCuenta(String value) {
    this.value = value;
  }

  public static TipoCuenta fromValue(String value) {
    for (TipoCuenta tipoCuenta : TipoCuenta.values()) {
      if (tipoCuenta.getValue().equals(value)) {
        return tipoCuenta;
      }
    }
    throw new IllegalArgumentException("Tipo de cuenta inválido: " + value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }

}