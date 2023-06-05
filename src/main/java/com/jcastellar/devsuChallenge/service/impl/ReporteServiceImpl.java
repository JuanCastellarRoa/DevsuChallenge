package com.jcastellar.devsuChallenge.service.impl;

import com.jcastellar.devsuChallenge.dto.reporte.CuentaReporteDTO;
import com.jcastellar.devsuChallenge.dto.reporte.MovimientoReporteDTO;
import com.jcastellar.devsuChallenge.dto.reporte.ReporteDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import com.jcastellar.devsuChallenge.entity.Cuenta;
import com.jcastellar.devsuChallenge.entity.Movimiento;
import com.jcastellar.devsuChallenge.service.ClienteService;
import com.jcastellar.devsuChallenge.service.MovimientoService;
import com.jcastellar.devsuChallenge.service.ReporteService;
import com.jcastellar.devsuChallenge.utility.excepciones.NoEncontrado;
import com.jcastellar.devsuChallenge.utility.mapper.MovimientoMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

  private final ClienteService clienteService;
  private final MovimientoService movimientoService;
  private final MovimientoMapper movimientoMapper;

  @Autowired
  public ReporteServiceImpl(ClienteService clienteService, MovimientoService movimientoService,
      MovimientoMapper movimientoMapper) {
    this.clienteService = clienteService;
    this.movimientoService = movimientoService;
    this.movimientoMapper = movimientoMapper;
  }

  @Override
  public ReporteDTO getReporte(Long clienteId, LocalDate fechaInicial, LocalDate fechafinal) {

    final Cliente cliente = clienteService.getMovByClienteId(clienteId, fechaInicial, fechafinal);

    ReporteDTO reporteDTO = new ReporteDTO();
    reporteDTO.setClienteId(cliente.getClienteId());
    reporteDTO.setNombre(cliente.getNombre());
    reporteDTO.setIdentificacion(cliente.getIdentificacion());

    List<CuentaReporteDTO> cuentaReporteDTOList = new ArrayList<>();

    for (Cuenta cuenta : cliente.getCuentas()) {
      CuentaReporteDTO cuentaReporteDTO = new CuentaReporteDTO();
      cuentaReporteDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
      cuentaReporteDTO.setSaldo(cuenta.getSaldoInicial());

      List<MovimientoReporteDTO> movimientosReporteDTO = new ArrayList<>();

      double totalRetiros = cuenta.getMovimientos().stream().filter(m -> "Retiro".equals(
          m.getTipoMovimiento().getValue())).mapToDouble(Movimiento::getValor).sum();

      double totalDepositos = cuenta.getMovimientos().stream().filter(m -> "Deposito".equals(
          m.getTipoMovimiento().getValue())).mapToDouble(Movimiento::getValor).sum();

      for (Movimiento m : cuenta.getMovimientos()) {
        MovimientoReporteDTO movimientoReporteDTO = new MovimientoReporteDTO();
        movimientoReporteDTO.setFecha(m.getFecha());
        movimientoReporteDTO.setTipoMovimiento(m.getTipoMovimiento().toString());
        movimientoReporteDTO.setValor(m.getValor());
        movimientosReporteDTO.add(movimientoReporteDTO);
      }
      cuentaReporteDTO.setMovimientos(movimientosReporteDTO);
      cuentaReporteDTO.setTotalRetiros(totalRetiros);
      cuentaReporteDTO.setTotalDepositos(totalDepositos);
      cuentaReporteDTO.setSaldo(cuentaReporteDTO.getSaldo() + (totalDepositos-totalRetiros));
      cuentaReporteDTOList.add(cuentaReporteDTO);
    }
    reporteDTO.setCuentas(cuentaReporteDTOList);
    if (cliente == null) {
      throw new NoEncontrado("Cliente no encontrado");
    }
    return reporteDTO;
  }
}