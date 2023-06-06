package com.jcastellar.devsuChallenge.dto.reporte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jcastellar.devsuChallenge.utility.enumerador.TipoMovimiento;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimientoReporteDTO {

  private TipoMovimiento tipoMovimiento;
  private LocalDate fecha;
  private Double valor;
}
