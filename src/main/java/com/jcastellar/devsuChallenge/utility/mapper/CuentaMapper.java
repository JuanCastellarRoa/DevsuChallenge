package com.jcastellar.devsuChallenge.utility.mapper;

import com.jcastellar.devsuChallenge.dto.CuentaDTO;
import com.jcastellar.devsuChallenge.entity.Cuenta;
import com.jcastellar.devsuChallenge.utility.enumerador.TipoCuenta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface CuentaMapper {

  CuentaMapper MAPPER = Mappers.getMapper(CuentaMapper.class);

  Cuenta cuentaDTOToCuenta(CuentaDTO cuentaDTO);

  CuentaDTO cuentaToCuentaDTO(Cuenta cuenta);

  default String tipoCuentaToString(TipoCuenta tipoCuenta) {
    return tipoCuenta.getValue();
  }

  default TipoCuenta stringToTipoCuenta(String tipoCuenta) {
    return TipoCuenta.fromValue(tipoCuenta);
  }

  default Boolean stringToBoolean(String valor) {
    return Boolean.valueOf(valor);
  }
}