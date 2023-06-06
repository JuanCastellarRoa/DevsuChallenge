package com.jcastellar.devsuChallenge.service;

import com.jcastellar.devsuChallenge.dto.ClienteDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ClienteService {

  List<ClienteDTO> getClientes();

  ClienteDTO getCliente(Long clienteId);

  ClienteDTO createCliente(ClienteDTO clienteDTO);

  ClienteDTO updateCliente(Long clienteId, ClienteDTO clienteDTO);

  ClienteDTO actualizacionParcialByFields(Long clienteId, Map<String, Object> fields);

  void deleteById(Long clienteId);

  Cliente getMovByClienteId(Long clienteId, LocalDate fechaInicial,
      LocalDate fechaFinal);

}