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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

@Service
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository clienteRepository;
  private final ClienteMapper clienteMapper;
  private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

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
      logger.warn("Cliente ya existe");
      throw new YaExiste("Cliente ya existe");
    }
    Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
    cliente.setPassword(BCrypt.hashpw(cliente.getPassword(), BCrypt.gensalt()));
    final Cliente clienteCreated = clienteRepository.save(cliente);
    logger.info("Cliente creado!");
    return clienteMapper.clienteToClienteDTO(clienteCreated);
  }

  @Override
  @Transactional
  public void deleteById(Long clienteId) {
    clienteRepository.findById(clienteId)
        .orElseThrow(() -> new NoEncontrado("Cliente no encontrado"));
    clienteRepository.deleteById(clienteId);
    logger.info("Cliente eliminado!");
  }

  @Override
  @Transactional
  public ClienteDTO updateCliente(Long clienteId, ClienteDTO clienteDTO) {
    Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
    if (clienteOpt.isPresent()) {
      Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
      Cliente clienteActualizado = clienteOpt.get();
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
      logger.info("Cliente actualizado!");
      return clienteMapper.clienteToClienteDTO(clienteActualizado);
    } else {
      logger.warn("Cliente no encontrado");
      throw new NoEncontrado("Cliente no encontrado");
    }
  }

  @Override
  @Transactional
  public ClienteDTO actualizacionParcialByFields(Long clienteId, Map<String, Object> fields) {
    Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
    if (clienteOpt.isPresent()) {
      fields.forEach((key, value) -> {
        if (key.equals("genero")) {
          value = clienteMapper.stringToGenero(value.toString());
        }
        if (key.equals("estado")) {
          value = clienteMapper.stringToBoolean(value.toString());
        }
        Field field = ReflectionUtils.findField(Cliente.class, key);
        field.setAccessible(true);
        ReflectionUtils.setField(field, clienteOpt.get(), value);
      });
      clienteOpt.get()
          .setPassword(BCrypt.hashpw(clienteOpt.get().getPassword(), BCrypt.gensalt()));
      Cliente cliente = clienteRepository.save(clienteOpt.get());
      ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente);
      logger.info("Cliente parcialmente actualizado!");
      return clienteDTO;
    } else {
      logger.warn("Cliente no encontrado");
      throw new NoEncontrado("Cliente no encontrado");
    }
  }

  @Override
  public Cliente getMovByClienteId(Long clienteId, LocalDate fechaInicial,
      LocalDate fechaFinal) {
    final Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
    if (clienteOpt.isPresent()) {
      Cliente finalC = clienteOpt.get();
      finalC.getCuentas().forEach(c -> {
        List<Movimiento> movimientoList = c.getMovimientos().stream()
            .filter(m -> m.getFecha().isEqual(fechaInicial) || (m.getFecha().isAfter(fechaInicial)
                && m.getFecha().isBefore(fechaFinal)) || m.getFecha().isEqual(fechaFinal))
            .toList();
        c.setMovimientos(movimientoList);
      });
      logger.info("Listado de movimientos obtenido!");
      return finalC;
    } else {
      logger.warn("Cliente no encontrado");
      throw new NoEncontrado("Cliente no encontrado");
    }
  }
}