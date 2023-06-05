package com.jcastellar.devsuChallenge.utility.enumerador;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum TipoMovimiento {

  RETIRO("Retiro"), DEPOSITO("Deposito");

  private final String value;

  TipoMovimiento(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}