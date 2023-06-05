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
public class ReporteDTO {

  private Long clienteId;

  private String nombre;

  private String identificacion;

  private List<CuentaReporteDTO> cuentas;
}