package com.jcastellar.devsuChallenge.controller;

import com.jcastellar.devsuChallenge.dto.ClienteDTO;
import com.jcastellar.devsuChallenge.service.ClienteService;
import com.jcastellar.devsuChallenge.utility.excepciones.NoEncontrado;
import com.jcastellar.devsuChallenge.utility.excepciones.PeticionErronea;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

  private final ClienteService clienteService;

  @GetMapping
  public ResponseEntity<List<ClienteDTO>> getClientes() {
    List<ClienteDTO> clientes = clienteService.getClientes();
    return new ResponseEntity<>(clientes, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClienteDTO> getCliente(@PathVariable Long id) {
    Optional<ClienteDTO> clienteOpt = Optional.ofNullable(clienteService.getCliente(id));
    if (clienteOpt.isEmpty()) {
      throw new NoEncontrado("Cliente no encontrado");
    }
    ClienteDTO clienteDTO = clienteOpt.get();
    return ResponseEntity.ok(clienteDTO);
  }

  @PostMapping
  public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new PeticionErronea(bindingResult.getAllErrors().toString());
    }
    ClienteDTO cliente = clienteService.createCliente(clienteDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id,
      @RequestBody ClienteDTO clienteDTO) {
    ClienteDTO clienteActualizado = clienteService.updateCliente(id, clienteDTO);
    return ResponseEntity.status(HttpStatus.OK).body(clienteActualizado);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ClienteDTO> actualizacionParcial(@PathVariable Long id,
      @RequestBody Map<String, Object> fields) {
    ClienteDTO clienteActualizado = clienteService.actualizacionParcialByFields(id, fields);
    return ResponseEntity.status(HttpStatus.OK).body(clienteActualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ClienteDTO> deleteCliente(@PathVariable Long id) {
    clienteService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}