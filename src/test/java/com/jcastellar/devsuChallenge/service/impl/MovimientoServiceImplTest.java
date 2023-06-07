package com.jcastellar.devsuChallenge.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.when;
import com.jcastellar.devsuChallenge.dto.CuentaDTO;
import com.jcastellar.devsuChallenge.dto.MovimientoDTO;
import com.jcastellar.devsuChallenge.entity.Cuenta;
import com.jcastellar.devsuChallenge.entity.Movimiento;
import com.jcastellar.devsuChallenge.repository.ClienteRepository;
import com.jcastellar.devsuChallenge.repository.CuentaRepository;
import com.jcastellar.devsuChallenge.repository.MovimientoRepository;
import com.jcastellar.devsuChallenge.utility.enumerador.TipoMovimiento;
import com.jcastellar.devsuChallenge.utility.excepciones.NoEncontrado;
import com.jcastellar.devsuChallenge.utility.mapper.MovimientoMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceImplTest {

  @Mock
  private MovimientoRepository mockMovimientoRepository;
  @Mock
  private MovimientoMapper mockMovimientoMapper;
  @Mock
  private CuentaRepository mockCuentaRepository;
  @Mock
  private ClienteRepository mockClienteRepository;

  private MovimientoServiceImpl movimientoServiceImplUnderTest;

  @BeforeEach
  void setUp() {
    movimientoServiceImplUnderTest = new MovimientoServiceImpl(mockMovimientoRepository,
        mockMovimientoMapper, mockCuentaRepository, mockClienteRepository);
  }

  @Test
  void testGetMovimientos() {
    // Setup
    final MovimientoDTO movimientoDTO = new MovimientoDTO();
    movimientoDTO.setId(0L);
    movimientoDTO.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimientoDTO.setValor(0.0);
    final CuentaDTO cuenta = new CuentaDTO();
    cuenta.setNumeroCuenta("numeroCuenta");
    movimientoDTO.setCuenta(cuenta);
    final List<MovimientoDTO> expectedResult = List.of(movimientoDTO);
    final Movimiento movimiento = new Movimiento();
    movimiento.setFecha(LocalDate.of(2020, 1, 1));
    movimiento.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimiento.setValor(0.0);
    movimiento.setSaldo(0.0);
    final Cuenta cuenta1 = new Cuenta();
    cuenta1.setSaldoInicial(0.0);
    cuenta1.setMovimientos(List.of(new Movimiento()));
    movimiento.setCuenta(cuenta1);
    final List<Movimiento> movimientos = List.of(movimiento);
    when(mockMovimientoRepository.findAll()).thenReturn(movimientos);
    final MovimientoDTO movimientoDTO1 = new MovimientoDTO();
    movimientoDTO1.setId(0L);
    movimientoDTO1.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimientoDTO1.setValor(0.0);
    final CuentaDTO cuenta2 = new CuentaDTO();
    cuenta2.setNumeroCuenta("numeroCuenta");
    movimientoDTO1.setCuenta(cuenta2);
    final Movimiento movimiento1 = new Movimiento();
    movimiento1.setFecha(LocalDate.of(2020, 1, 1));
    movimiento1.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimiento1.setValor(0.0);
    movimiento1.setSaldo(0.0);
    final Cuenta cuenta3 = new Cuenta();
    cuenta3.setSaldoInicial(0.0);
    cuenta3.setMovimientos(List.of(new Movimiento()));
    movimiento1.setCuenta(cuenta3);
    when(mockMovimientoMapper.movimientoToMovimientoDTO(movimiento1)).thenReturn(movimientoDTO1);
    final List<MovimientoDTO> result = movimientoServiceImplUnderTest.getMovimientos();
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetMovimiento() {
    final MovimientoDTO expectedResult = new MovimientoDTO();
    expectedResult.setId(0L);
    expectedResult.setTipoMovimiento(TipoMovimiento.RETIRO);
    expectedResult.setValor(0.0);
    final CuentaDTO cuenta = new CuentaDTO();
    cuenta.setNumeroCuenta("numeroCuenta");
    expectedResult.setCuenta(cuenta);
    final Movimiento movimiento1 = new Movimiento();
    movimiento1.setFecha(LocalDate.of(2020, 1, 1));
    movimiento1.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimiento1.setValor(0.0);
    movimiento1.setSaldo(0.0);
    final Cuenta cuenta1 = new Cuenta();
    cuenta1.setSaldoInicial(0.0);
    cuenta1.setMovimientos(List.of(new Movimiento()));
    movimiento1.setCuenta(cuenta1);
    final Optional<Movimiento> movimiento = Optional.of(movimiento1);
    when(mockMovimientoRepository.findById(0L)).thenReturn(movimiento);
    final MovimientoDTO movimientoDTO = new MovimientoDTO();
    movimientoDTO.setId(0L);
    movimientoDTO.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimientoDTO.setValor(0.0);
    final CuentaDTO cuenta2 = new CuentaDTO();
    cuenta2.setNumeroCuenta("numeroCuenta");
    movimientoDTO.setCuenta(cuenta2);
    final Movimiento movimiento2 = new Movimiento();
    movimiento2.setFecha(LocalDate.of(2020, 1, 1));
    movimiento2.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimiento2.setValor(0.0);
    movimiento2.setSaldo(0.0);
    final Cuenta cuenta3 = new Cuenta();
    cuenta3.setSaldoInicial(0.0);
    cuenta3.setMovimientos(List.of(new Movimiento()));
    movimiento2.setCuenta(cuenta3);
    when(mockMovimientoMapper.movimientoToMovimientoDTO(movimiento2)).thenReturn(movimientoDTO);
    final MovimientoDTO result = movimientoServiceImplUnderTest.getMovimiento(0L);
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testCreateMovimiento_CuentaRepositoryReturnsAbsent() {
    final MovimientoDTO movimientoDTO = new MovimientoDTO();
    movimientoDTO.setId(0L);
    movimientoDTO.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimientoDTO.setValor(0.0);
    final CuentaDTO cuenta = new CuentaDTO();
    cuenta.setNumeroCuenta("numeroCuenta");
    movimientoDTO.setCuenta(cuenta);
    when(mockCuentaRepository.findByNumeroCuenta("numeroCuenta")).thenReturn(Optional.empty());
    assertThatThrownBy(
        () -> movimientoServiceImplUnderTest.createMovimiento(movimientoDTO))
        .isInstanceOf(NoEncontrado.class);
  }


  @Test
  void testUpdateMovimiento_MovimientoRepositoryFindByIdReturnsAbsent() {
    // Setup
    final MovimientoDTO movimientoDTO = new MovimientoDTO();
    movimientoDTO.setId(0L);
    movimientoDTO.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimientoDTO.setValor(0.0);
    final CuentaDTO cuenta = new CuentaDTO();
    cuenta.setNumeroCuenta("numeroCuenta");
    movimientoDTO.setCuenta(cuenta);
    when(mockMovimientoRepository.findById(0L)).thenReturn(Optional.empty());
    assertThatThrownBy(
        () -> movimientoServiceImplUnderTest.updateMovimiento(0L, movimientoDTO))
        .isInstanceOf(NoEncontrado.class);
  }

  @Test
  void testGetUltimoMovimiento() {
    final Movimiento movimiento = new Movimiento();
    movimiento.setFecha(LocalDate.of(2020, 1, 1));
    movimiento.setTipoMovimiento(TipoMovimiento.RETIRO);
    movimiento.setValor(0.0);
    movimiento.setSaldo(0.0);
    final Cuenta cuenta = new Cuenta();
    cuenta.setSaldoInicial(0.0);
    cuenta.setMovimientos(List.of(new Movimiento()));
    movimiento.setCuenta(cuenta);
    final List<Movimiento> movimientos = List.of(movimiento);
    final double result = movimientoServiceImplUnderTest.getUltimoMovimiento(movimientos);
    assertThat(result).isEqualTo(0.0, within(0.0001));
  }
}
