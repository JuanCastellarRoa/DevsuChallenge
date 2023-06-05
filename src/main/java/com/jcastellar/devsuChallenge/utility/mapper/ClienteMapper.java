package com.jcastellar.devsuChallenge.utility.mapper;

import com.jcastellar.devsuChallenge.dto.ClienteDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import com.jcastellar.devsuChallenge.utility.enumerador.Genero;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface ClienteMapper {

  ClienteMapper MAPPER = Mappers.getMapper(ClienteMapper.class);

  //@Mapping(target = "genero.value", source = "genero.value")
  Cliente clienteDTOToCliente(ClienteDTO clienteDTO);

  //@Mapping(target = "genero.value", source = "genero.value")
  ClienteDTO clienteToClienteDTO(Cliente cliente);

  void actualizarClienteDesdeDTO(ClienteDTO clienteDTO, @MappingTarget Cliente cliente);


}