package com.jcastellar.devsuChallenge.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.jcastellar.devsuChallenge.dto.ClienteDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import com.jcastellar.devsuChallenge.entity.Cuenta;
import com.jcastellar.devsuChallenge.entity.Movimiento;
import com.jcastellar.devsuChallenge.repository.ClienteRepository;
import com.jcastellar.devsuChallenge.utility.enumerador.Genero;
import com.jcastellar.devsuChallenge.utility.excepciones.NoEncontrado;
import com.jcastellar.devsuChallenge.utility.excepciones.YaExiste;
import com.jcastellar.devsuChallenge.utility.mapper.ClienteMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

  @Mock
  private ClienteRepository mockClienteRepository;
  @Mock
  private ClienteMapper mockClienteMapper;

  private ClienteServiceImpl clienteServiceImplUnderTest;

  @BeforeEach
  void setUp() {
    clienteServiceImplUnderTest = new ClienteServiceImpl(mockClienteRepository, mockClienteMapper);
  }

  @Test
  void testGetClientes_ClienteRepositoryReturnsNoItems() {
    when(mockClienteRepository.findAll()).thenReturn(Collections.emptyList());
    final List<ClienteDTO> result = clienteServiceImplUnderTest.getClientes();
    assertThat(result).isEqualTo(Collections.emptyList());
  }


  @Test
  void testCreateCliente_ThrowsYaExiste() {
    final ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setClienteId(1L);
    clienteDTO.setNombre("jose lema");
    clienteDTO.setGenero(Genero.MASCULINO);
    clienteDTO.setEdad(18);
    clienteDTO.setIdentificacion("identificacion");
    clienteDTO.setDireccion("otavalo sn y principal");
    clienteDTO.setTelefono("098254785");
    clienteDTO.setPassword("1234");
    clienteDTO.setEstado(true);

    final Cliente cliente = new Cliente();
    cliente.setNombre("jose lema");
    cliente.setGenero(Genero.MASCULINO);
    cliente.setEdad(18);
    cliente.setIdentificacion("identificacion");
    cliente.setDireccion("otavalo sn y principal");
    cliente.setTelefono("098254785");
    cliente.setPassword("1234");
    cliente.setEstado(true);

    final Cuenta cuenta = new Cuenta();
    final Movimiento movimiento = new Movimiento();
    movimiento.setFecha(LocalDate.of(2020, 1, 1));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));
    final Optional<Cliente> clienteOptional = Optional.of(cliente);
    when(mockClienteRepository.findByIdentificacion("identificacion")).thenReturn(clienteOptional);

    assertThatThrownBy(() -> clienteServiceImplUnderTest.createCliente(clienteDTO))
        .isInstanceOf(YaExiste.class);
  }

  @Test
  void testDeleteById() {
    final Cliente cliente = new Cliente();
    cliente.setNombre("nombre");
    cliente.setGenero(Genero.MASCULINO);
    cliente.setEdad(0);
    cliente.setIdentificacion("identificacion");
    cliente.setDireccion("direccion");
    cliente.setTelefono("telefono");
    cliente.setPassword("password");
    cliente.setEstado(false);
    final Cuenta cuenta = new Cuenta();
    final Movimiento movimiento = new Movimiento();
    movimiento.setFecha(LocalDate.of(2020, 1, 1));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));
    final Optional<Cliente> clienteOptional = Optional.of(cliente);
    when(mockClienteRepository.findById(0L)).thenReturn(clienteOptional);
    clienteServiceImplUnderTest.deleteById(0L);
    verify(mockClienteRepository).deleteById(0L);
  }

  @Test
  void testGetMovByClienteId() {
    final Cliente expectedResult = new Cliente();
    expectedResult.setNombre("nombre");
    expectedResult.setGenero(Genero.MASCULINO);
    expectedResult.setEdad(0);
    expectedResult.setIdentificacion("identificacion");
    expectedResult.setDireccion("direccion");
    expectedResult.setTelefono("telefono");
    expectedResult.setPassword("password");
    expectedResult.setEstado(false);
    final Cuenta cuenta = new Cuenta();
    final Movimiento movimiento = new Movimiento();
    movimiento.setFecha(LocalDate.of(2020, 1, 1));
    cuenta.setMovimientos(List.of(movimiento));
    expectedResult.setCuentas(List.of(cuenta));

    final Cliente cliente = new Cliente();
    cliente.setNombre("nombre");
    cliente.setGenero(Genero.MASCULINO);
    cliente.setEdad(0);
    cliente.setIdentificacion("identificacion");
    cliente.setDireccion("direccion");
    cliente.setTelefono("telefono");
    cliente.setPassword("password");
    cliente.setEstado(false);
    final Cuenta cuenta1 = new Cuenta();
    final Movimiento movimiento1 = new Movimiento();
    movimiento1.setFecha(LocalDate.of(2020, 1, 1));
    cuenta1.setMovimientos(List.of(movimiento1));
    cliente.setCuentas(List.of(cuenta1));
    final Optional<Cliente> clienteOptional = Optional.of(cliente);
    when(mockClienteRepository.findById(0L)).thenReturn(clienteOptional);

    final Cliente result = clienteServiceImplUnderTest.getMovByClienteId(0L,
        LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1));

    assertThat(result).isEqualTo(expectedResult);
  }
}
