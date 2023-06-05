package com.jcastellar.devsuChallenge.service;

import com.jcastellar.devsuChallenge.dto.CuentaDTO;
import java.util.List;
import java.util.Map;

public interface CuentaService {

  List<CuentaDTO> getCuentas();

  CuentaDTO getCuenta(Long id);

  CuentaDTO createCuenta(CuentaDTO cuentaDTO);

  CuentaDTO updateCuenta(Long id, CuentaDTO cuentaDTO);

  CuentaDTO actualizacionParcialByFields(Long id, Map<String, Object> fields);

  void deleteById(Long id);

}