package com.jcastellar.devsuChallenge.controller;

import com.jcastellar.devsuChallenge.dto.MovimientoDTO;
import com.jcastellar.devsuChallenge.service.MovimientoService;
import com.jcastellar.devsuChallenge.utility.excepciones.NoEncontrado;
import com.jcastellar.devsuChallenge.utility.excepciones.PeticionErronea;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/movimientos")
public class MovimientoController {

  @Autowired
  private MovimientoService movimientoService;

  @GetMapping
  public ResponseEntity<List<MovimientoDTO>> getMovimientos() {
    List<MovimientoDTO> movimientos = movimientoService.getMovimientos();
    return new ResponseEntity<>(movimientos, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovimientoDTO> getMovimiento(@PathVariable Long id) {
    Optional<MovimientoDTO> movimientoOpt = Optional.ofNullable(
        movimientoService.getMovimiento(id));
    if (movimientoOpt.isEmpty()) {
      throw new NoEncontrado("Movimiento no encontrado");
    }
    MovimientoDTO movimientoDTO = movimientoOpt.get();
    return ResponseEntity.ok(movimientoDTO);
  }

  @PostMapping
  public ResponseEntity<?> createMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new PeticionErronea(bindingResult.getAllErrors().toString());
    }
    MovimientoDTO movimiento = movimientoService.createMovimiento(movimientoDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(movimiento);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MovimientoDTO> updateMovimiento(@PathVariable Long id,
      @RequestBody MovimientoDTO movimientoDTO) {
    MovimientoDTO movimientoActualizado = movimientoService.updateMovimiento(id, movimientoDTO);
    return ResponseEntity.status(HttpStatus.OK).body(movimientoActualizado);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> actualizacionParcial(@PathVariable Long id,
      @RequestBody Map<String, Object> fields) {
    MovimientoDTO movimientoActualizado = movimientoService.actualizacionParcialByFields(id,
        fields);
    return ResponseEntity.status(HttpStatus.OK).body(movimientoActualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MovimientoDTO> deleteMovimiento(@PathVariable Long id) {
    Optional<MovimientoDTO> movimientoOpt = Optional.ofNullable(
        movimientoService.getMovimiento(id));
    if (movimientoOpt.isEmpty()) {
      throw new NoEncontrado("Movimiento no entontrado");
    }
    movimientoService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}