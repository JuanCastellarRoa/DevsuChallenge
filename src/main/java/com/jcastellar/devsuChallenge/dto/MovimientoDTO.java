package com.jcastellar.devsuChallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jcastellar.devsuChallenge.utility.enumerador.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimientoDTO {

  private Long id;

  //@Enumerated(EnumType.STRING)
  private TipoMovimiento tipoMovimiento;

  @NotNull
  private Double valor;

  private Double saldo;

  private CuentaDTO cuenta;
}