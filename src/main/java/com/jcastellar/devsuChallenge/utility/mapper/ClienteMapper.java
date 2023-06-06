package com.jcastellar.devsuChallenge.utility.mapper;

import com.jcastellar.devsuChallenge.dto.ClienteDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import com.jcastellar.devsuChallenge.utility.enumerador.Genero;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface ClienteMapper {

  ClienteMapper MAPPER = Mappers.getMapper(ClienteMapper.class);

  Cliente clienteDTOToCliente(ClienteDTO clienteDTO);

  ClienteDTO clienteToClienteDTO(Cliente cliente);

  default String generoToString(Genero genero) {
    return genero.getValue();
  }

  default Genero stringToGenero(String genero) {
    return Genero.fromValue(genero);
  }

  default String booleanToString(Boolean estado) {
    return estado.toString();
  }

  default Boolean stringToBoolean(String estado) {
    return Boolean.valueOf(estado);
  }

}