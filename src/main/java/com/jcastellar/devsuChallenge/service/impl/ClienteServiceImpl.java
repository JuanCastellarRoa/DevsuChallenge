package com.jcastellar.devsuChallenge.service.impl;

import com.jcastellar.devsuChallenge.dto.ClienteDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import com.jcastellar.devsuChallenge.entity.Movimiento;
import com.jcastellar.devsuChallenge.repository.ClienteRepository;
import com.jcastellar.devsuChallenge.service.ClienteService;
import com.jcastellar.devsuChallenge.utility.BCrypt;
import com.jcastellar.devsuChallenge.utility.excepciones.NoEncontrado;
import com.jcastellar.devsuChallenge.utility.excepciones.YaExiste;
import com.jcastellar.devsuChallenge.utility.mapper.ClienteMapper;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

@Service
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository clienteRepository;
  private final ClienteMapper clienteMapper;

  @Autowired
  public ClienteServiceImpl(
      ClienteRepository clienteRepository,
      ClienteMapper clienteMapper) {
    this.clienteRepository = clienteRepository;
    this.clienteMapper = clienteMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ClienteDTO> getClientes() {
    List<Cliente> clientes = clienteRepository.findAll();
    return clientes.stream().map(clienteMapper::clienteToClienteDTO).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public ClienteDTO getCliente(Long clienteId) {
    return clienteRepository.findById(clienteId).map(clienteMapper::clienteToClienteDTO).orElse(null);
  }

  @Override
  @Transactional
  public ClienteDTO createCliente(ClienteDTO clienteDTO) {
    Optional<Cliente> clienteOpt = clienteRepository.findByIdentificacion(
        clienteDTO.getIdentificacion());
    if (clienteOpt.isPresent()) {
      throw new YaExiste("Cliente ya existe");
    }
    Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
    cliente.setPassword(BCrypt.hashpw(cliente.getPassword(), BCrypt.gensalt()));
    final Cliente clienteCreated = clienteRepository.save(cliente);
    return clienteMapper.clienteToClienteDTO(clienteCreated);
  }

  @Override
  @Transactional
  public void deleteById(Long clienteId) {
    clienteRepository.findById(clienteId)
        .orElseThrow(() -> new NoEncontrado("Cliente no encontrado"));
    clienteRepository.deleteById(clienteId);
  }

  @Override
  @Transactional
  public ClienteDTO updateCliente(Long clienteId, ClienteDTO clienteDTO) {
    Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
    if (clienteOpt.isPresent()) {
      Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
      Cliente clienteActualizado = clienteRepository.findById(clienteId).get();
      clienteActualizado.setNombre(cliente.getNombre());
      clienteActualizado.setGenero(cliente.getGenero());
      clienteActualizado.setEdad(cliente.getEdad());
      clienteActualizado.setIdentificacion(cliente.getIdentificacion());
      clienteActualizado.setDireccion(cliente.getDireccion());
      clienteActualizado.setTelefono(cliente.getTelefono());
      clienteActualizado.setPassword(BCrypt.hashpw(cliente.getPassword(), BCrypt.gensalt()));
      clienteActualizado.setEstado(cliente.getEstado().booleanValue());
      clienteActualizado.setCuentas(cliente.getCuentas());
      clienteRepository.save(clienteActualizado);
      return clienteMapper.clienteToClienteDTO(clienteActualizado);
    } else {
      throw new NoEncontrado("Cliente no encontrado");
    }
  }

  @Override
  @Transactional
  public ClienteDTO actualizacionParcialByFields(Long clienteId, ClienteDTO clienteDTO) {
    Cliente clienteActual = clienteRepository.findById(clienteId)
        .orElseThrow(() -> new NoEncontrado("No se encontró al cliente con el ID especificado"));

    clienteActual.setClienteId(clienteActual.getClienteId());

    // Mapear el clienteDTO al clienteActual
    clienteMapper.actualizarClienteDesdeDTO(clienteDTO, clienteActual);

    // Guardar el cliente actualizado en la base de datos
    Cliente clienteActualizado = clienteRepository.save(clienteActual);

    // Mapear el clienteActualizado a un objeto ClienteDTO y devolverlo
    return clienteMapper.clienteToClienteDTO(clienteActualizado);
  }

/*
  @Override
  @Transactional
  public ClienteDTO actualizacionParcialByFields(Long clienteId, Map<String, Object> fields) {
    Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
    if (clienteOpt.isPresent()) {
      Optional<Cliente> clienteActualizado = clienteRepository.findById(clienteId);
      fields.forEach((key, value) -> {
        Field field = ReflectionUtils.findField(Cliente.class, key);
        field.setAccessible(true);
        ReflectionUtils.setField(field, clienteActualizado.get(), value);
      });
      clienteActualizado.get()
          .setPassword(BCrypt.hashpw(clienteActualizado.get().getPassword(), BCrypt.gensalt()));
      clienteRepository.save(clienteActualizado.get());
      ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(clienteActualizado.get());
      return clienteDTO;
    } else {
      throw new NoEncontrado("Cliente no encontrado");
    }
  }
*/
  @Override
  public Cliente getMovByClienteId(Long clienteId, LocalDate fechaInicial,
      LocalDate fechaFinal) {
    final Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
    if (clienteOpt.isPresent()) {
      Cliente finalC=clienteOpt.get();
      finalC.getCuentas().forEach(c -> {
        List<Movimiento> movimientoList = c.getMovimientos().stream()
            .filter(m -> m.getFecha().isEqual(fechaInicial) || (m.getFecha().isAfter(fechaInicial) && m.getFecha().isBefore(fechaFinal)) || m.getFecha().isEqual(fechaFinal) )
            .toList();
        c.setMovimientos(movimientoList);
      });
      return finalC;
    } else {
      throw new NoEncontrado("Cliente no encontrado");
    }
  }
}