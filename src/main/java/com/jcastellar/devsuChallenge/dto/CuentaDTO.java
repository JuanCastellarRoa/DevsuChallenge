package com.jcastellar.devsuChallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jcastellar.devsuChallenge.utility.enumerador.TipoCuenta;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaDTO {

  private Long id;

  @NotEmpty
  @Pattern(regexp = "^[0-9]{6,11}$", message = "Verificar el número de la cuenta")
  @Column(name = "numero_de_cuenta", unique = true)
  private String numeroCuenta;

  @Enumerated(EnumType.STRING)
  private TipoCuenta tipoCuenta;

  @NotNull
  private Double saldoInicial;

  private Boolean estado;

  private ClienteDTO cliente;
}