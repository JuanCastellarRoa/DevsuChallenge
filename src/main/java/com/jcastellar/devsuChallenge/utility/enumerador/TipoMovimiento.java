package com.jcastellar.devsuChallenge.utility.enumerador;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TipoMovimiento {

  RETIRO("Retiro"), DEPOSITO("Deposito");

  private final String value;

  TipoMovimiento(String value) {
    this.value = value;
  }

  public static TipoMovimiento fromValue(String value) {
    for (TipoMovimiento tipoMovimiento : TipoMovimiento.values()) {
      if (tipoMovimiento.getValue().equals(value)) {
        return tipoMovimiento;
      }
    }
    throw new IllegalArgumentException("Tipo de movimiento inválido: " + value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}