package com.jcastellar.devsuChallenge.utility.mapper;

import com.jcastellar.devsuChallenge.dto.MovimientoDTO;
import com.jcastellar.devsuChallenge.dto.reporte.MovimientoReporteDTO;
import com.jcastellar.devsuChallenge.entity.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface MovimientoMapper {

  MovimientoMapper MAPPER = Mappers.getMapper(MovimientoMapper.class);

  Movimiento movimientoDTOToMovimiento(MovimientoDTO movimientoDTO);

  MovimientoDTO movimientoToMovimientoDTO(Movimiento movimiento);

}
