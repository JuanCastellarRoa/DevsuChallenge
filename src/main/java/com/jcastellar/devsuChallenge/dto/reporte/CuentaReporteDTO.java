package com.jcastellar.devsuChallenge.dto.reporte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaReporteDTO {

  private String numeroCuenta;
  private String tipoCuenta;
  private Double saldo;
  private Double totalRetiros;
  private Double totalDepositos;
  private List<MovimientoReporteDTO> movimientos;

}
