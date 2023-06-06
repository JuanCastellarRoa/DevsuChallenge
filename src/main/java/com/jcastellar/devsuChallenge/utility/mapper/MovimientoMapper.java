package com.jcastellar.devsuChallenge.utility.mapper;

import com.jcastellar.devsuChallenge.dto.MovimientoDTO;
import com.jcastellar.devsuChallenge.entity.Movimiento;
import com.jcastellar.devsuChallenge.utility.enumerador.TipoMovimiento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface MovimientoMapper {

  MovimientoMapper MAPPER = Mappers.getMapper(MovimientoMapper.class);

  Movimiento movimientoDTOToMovimiento(MovimientoDTO movimientoDTO);

  MovimientoDTO movimientoToMovimientoDTO(Movimiento movimiento);

  default String tipoMovimientoToString(TipoMovimiento tipoMovimiento) {
    return tipoMovimiento.getValue();
  }

  default TipoMovimiento stringToTipoMovimiento(String tipoMovimiento) {
    return TipoMovimiento.fromValue(tipoMovimiento);
  }

}
