package com.jcastellar.devsuChallenge.controller;

import com.jcastellar.devsuChallenge.dto.reporte.ReporteDTO;
import com.jcastellar.devsuChallenge.service.ReporteService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reportes")
public class ReporteController {

  private ReporteService reporteService;

  @Autowired
  public ReporteController(ReporteService reporteService) {
    this.reporteService = reporteService;
  }

  @GetMapping
  @Transient
  public ResponseEntity<ReporteDTO> getReporte(@Valid @RequestParam("clienteId") Long clienteId,
      @RequestParam("fechaInicial") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicial,
      @RequestParam("fechaFinal") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFinal) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(reporteService.getReporte(clienteId, fechaInicial, fechaFinal));
  }
}