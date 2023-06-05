package com.jcastellar.devsuChallenge.utility.mapper;

import com.jcastellar.devsuChallenge.dto.CuentaDTO;
import com.jcastellar.devsuChallenge.entity.Cuenta;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface CuentaMapper {

  CuentaMapper MAPPER = Mappers.getMapper(CuentaMapper.class);

  @Mapping(target = "tipoCuenta.value", source = "tipoCuenta.value")
  Cuenta cuentaDTOToCuenta(CuentaDTO cuentaDTO);

  @Mapping(target = "tipoCuenta.value", source = "tipoCuenta.value")
  CuentaDTO cuentaToCuentaDTO(Cuenta cuenta);
}
