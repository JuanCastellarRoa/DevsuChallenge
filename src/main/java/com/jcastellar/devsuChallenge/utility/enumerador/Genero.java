package com.jcastellar.devsuChallenge.utility.enumerador;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Genero {

  MASCULINO("Masculino"), FEMENINO("Femenino"), NO_BINARIO("No Binario"), OTRO("Otro");

  private final String value;

  Genero(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

}