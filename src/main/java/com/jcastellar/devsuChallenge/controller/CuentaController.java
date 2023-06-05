package com.jcastellar.devsuChallenge.controller;

import com.jcastellar.devsuChallenge.dto.CuentaDTO;
import com.jcastellar.devsuChallenge.service.CuentaService;
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
@RequestMapping("/cuentas")
public class CuentaController {

  @Autowired
  private CuentaService cuentaService;

  @GetMapping
  public ResponseEntity<List<CuentaDTO>> getCuentas() {
    List<CuentaDTO> cuentas = cuentaService.getCuentas();
    return new ResponseEntity<>(cuentas, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CuentaDTO> getCuenta(@PathVariable Long id) {
    Optional<CuentaDTO> cuentaOpt = Optional.ofNullable(cuentaService.getCuenta(id));
    if (cuentaOpt.isEmpty()) {
      throw new NoEncontrado("Cuenta no encontrada");
    }
    CuentaDTO cuentaDTO = cuentaOpt.get();
    return ResponseEntity.ok(cuentaDTO);
  }

  @PostMapping
  public ResponseEntity<CuentaDTO> createCuenta(@Valid @RequestBody CuentaDTO cuentaDTO,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new PeticionErronea(bindingResult.getAllErrors().toString());
    }
    CuentaDTO cuenta = cuentaService.createCuenta(cuentaDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CuentaDTO> updateCuenta(@PathVariable Long id,
      @RequestBody CuentaDTO cuentaDTO) {
    try {
      CuentaDTO cuentaActualizada = cuentaService.updateCuenta(id, cuentaDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(cuentaActualizada);
    } catch (Exception e) {
      throw new PeticionErronea("Verificar los datos de la cuenta");
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CuentaDTO> actualizacionParcial(@PathVariable Long id,
      @RequestBody Map<String, Object> fields) {
    try {
      CuentaDTO cuentaActualizada = cuentaService.actualizacionParcialByFields(id, fields);
      return ResponseEntity.status(HttpStatus.CREATED).body(cuentaActualizada);
    } catch (Exception e) {
      throw new PeticionErronea("Verificar los datos de la cuenta");
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCuenta(@PathVariable Long id) {
    //Validar Saldo en la cuenta para devolver dinero al cliente en caso de saldo positivo
    try {
      cuentaService.deleteById(id);
      return ResponseEntity.ok("La cuenta con ID " + id + " ha sido eliminada correctamente.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Ocurrió un error al eliminar la cuenta.");
    }
  }
}