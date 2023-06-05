package com.jcastellar.devsuChallenge.service;

import com.jcastellar.devsuChallenge.dto.reporte.ReporteDTO;
import java.time.LocalDate;

public interface ReporteService {

  ReporteDTO getReporte(Long clienteId, LocalDate fechaInicial, LocalDate fechafinal);

}
