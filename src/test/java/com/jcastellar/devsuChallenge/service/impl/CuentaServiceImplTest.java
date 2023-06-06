package com.jcastellar.devsuChallenge.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.jcastellar.devsuChallenge.dto.ClienteDTO;
import com.jcastellar.devsuChallenge.dto.CuentaDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import com.jcastellar.devsuChallenge.entity.Cuenta;
import com.jcastellar.devsuChallenge.repository.ClienteRepository;
import com.jcastellar.devsuChallenge.repository.CuentaRepository;
import com.jcastellar.devsuChallenge.repository.MovimientoRepository;
import com.jcastellar.devsuChallenge.utility.enumerador.TipoCuenta;
import com.jcastellar.devsuChallenge.utility.mapper.CuentaMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

  @Mock
  private CuentaRepository mockCuentaRepository;
  @Mock
  private CuentaMapper mockCuentaMapper;
  @Mock
  private ClienteRepository mockClienteRepository;
  @Mock
  private MovimientoRepository mockMovimientoRepository;

  private CuentaServiceImpl cuentaServiceImplUnderTest;

  @BeforeEach
  void setUp() {
    cuentaServiceImplUnderTest = new CuentaServiceImpl(mockCuentaRepository, mockCuentaMapper,
        mockClienteRepository, mockMovimientoRepository);
  }

  @Test
  void testGetCuenta() {
    final CuentaDTO expectedResult = new CuentaDTO();
    expectedResult.setId(1L);
    expectedResult.setNumeroCuenta("numeroCuenta");
    expectedResult.setTipoCuenta(TipoCuenta.AHORRO);
    final ClienteDTO cliente = new ClienteDTO();
    cliente.setIdentificacion("identificacion");
    expectedResult.setCliente(cliente);
    final Cuenta cuenta1 = new Cuenta();
    cuenta1.setNumeroCuenta("numeroCuenta");
    cuenta1.setTipoCuenta(TipoCuenta.AHORRO);
    cuenta1.setSaldoInicial(0.0);
    cuenta1.setEstado(false);
    final Cliente cliente1 = new Cliente();
    cuenta1.setCliente(cliente1);
    final Optional<Cuenta> cuenta = Optional.of(cuenta1);
    when(mockCuentaRepository.findById(1L)).thenReturn(cuenta);
    final CuentaDTO cuentaDTO = new CuentaDTO();
    cuentaDTO.setId(1L);
    cuentaDTO.setNumeroCuenta("numeroCuenta");
    cuentaDTO.setTipoCuenta(TipoCuenta.AHORRO);
    final ClienteDTO cliente2 = new ClienteDTO();
    cliente2.setIdentificacion("identificacion");
    cuentaDTO.setCliente(cliente2);
    final Cuenta cuenta2 = new Cuenta();
    cuenta2.setNumeroCuenta("numeroCuenta");
    cuenta2.setTipoCuenta(TipoCuenta.AHORRO);
    cuenta2.setSaldoInicial(0.0);
    cuenta2.setEstado(false);
    final Cliente cliente3 = new Cliente();
    cuenta2.setCliente(cliente3);
    when(mockCuentaMapper.cuentaToCuentaDTO(cuenta2)).thenReturn(cuentaDTO);
    final CuentaDTO result = cuentaServiceImplUnderTest.getCuenta(1L);
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testUpdateCuenta() {
    final CuentaDTO cuentaDTO = new CuentaDTO();
    cuentaDTO.setId(1L);
    cuentaDTO.setNumeroCuenta("numeroCuenta");
    cuentaDTO.setTipoCuenta(TipoCuenta.AHORRO);
    final ClienteDTO cliente = new ClienteDTO();
    cliente.setIdentificacion("identificacion");
    cuentaDTO.setCliente(cliente);
    final CuentaDTO expectedResult = new CuentaDTO();
    expectedResult.setId(1L);
    expectedResult.setNumeroCuenta("numeroCuenta");
    expectedResult.setTipoCuenta(TipoCuenta.AHORRO);
    final ClienteDTO cliente1 = new ClienteDTO();
    cliente1.setIdentificacion("identificacion");
    expectedResult.setCliente(cliente1);
    final Cuenta cuenta1 = new Cuenta();
    cuenta1.setNumeroCuenta("numeroCuenta");
    cuenta1.setTipoCuenta(TipoCuenta.AHORRO);
    cuenta1.setSaldoInicial(0.0);
    cuenta1.setEstado(false);
    final Cliente cliente2 = new Cliente();
    cuenta1.setCliente(cliente2);
    final Optional<Cuenta> cuenta = Optional.of(cuenta1);
    when(mockCuentaRepository.findById(1L)).thenReturn(cuenta);
    final Cuenta cuenta2 = new Cuenta();
    cuenta2.setNumeroCuenta("numeroCuenta");
    cuenta2.setTipoCuenta(TipoCuenta.AHORRO);
    cuenta2.setSaldoInicial(0.0);
    cuenta2.setEstado(false);
    final Cliente cliente3 = new Cliente();
    cuenta2.setCliente(cliente3);
    final CuentaDTO cuentaDTO1 = new CuentaDTO();
    cuentaDTO1.setId(1L);
    cuentaDTO1.setNumeroCuenta("numeroCuenta");
    cuentaDTO1.setTipoCuenta(TipoCuenta.AHORRO);
    final ClienteDTO cliente4 = new ClienteDTO();
    cliente4.setIdentificacion("identificacion");
    cuentaDTO1.setCliente(cliente4);
    when(mockCuentaMapper.cuentaDTOToCuenta(cuentaDTO1)).thenReturn(cuenta2);
    final CuentaDTO cuentaDTO2 = new CuentaDTO();
    cuentaDTO2.setId(1L);
    cuentaDTO2.setNumeroCuenta("numeroCuenta");
    cuentaDTO2.setTipoCuenta(TipoCuenta.AHORRO);
    final ClienteDTO cliente5 = new ClienteDTO();
    cliente5.setIdentificacion("identificacion");
    cuentaDTO2.setCliente(cliente5);
    final Cuenta cuenta3 = new Cuenta();
    cuenta3.setNumeroCuenta("numeroCuenta");
    cuenta3.setTipoCuenta(TipoCuenta.AHORRO);
    cuenta3.setSaldoInicial(0.0);
    cuenta3.setEstado(false);
    final Cliente cliente6 = new Cliente();
    cuenta3.setCliente(cliente6);
    when(mockCuentaMapper.cuentaToCuentaDTO(cuenta3)).thenReturn(cuentaDTO2);
    final CuentaDTO result = cuentaServiceImplUnderTest.updateCuenta(1L, cuentaDTO);
    assertThat(result).isEqualTo(expectedResult);
    final Cuenta entity = new Cuenta();
    entity.setNumeroCuenta("numeroCuenta");
    entity.setTipoCuenta(TipoCuenta.AHORRO);
    entity.setSaldoInicial(0.0);
    entity.setEstado(false);
    final Cliente cliente7 = new Cliente();
    entity.setCliente(cliente7);
    verify(mockCuentaRepository).save(entity);
  }
}
