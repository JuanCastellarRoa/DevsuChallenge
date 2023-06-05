package com.jcastellar.devsuChallenge.utility.mapper;

import com.jcastellar.devsuChallenge.dto.ClienteDTO;
import com.jcastellar.devsuChallenge.dto.CuentaDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import com.jcastellar.devsuChallenge.entity.Cuenta;
import com.jcastellar.devsuChallenge.utility.enumerador.TipoCuenta;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-05T04:11:27-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class CuentaMapperImpl implements CuentaMapper {

    @Override
    public Cuenta cuentaDTOToCuenta(CuentaDTO cuentaDTO) {
        if ( cuentaDTO == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setTipoCuenta( tipoCuentaToTipoCuenta( cuentaDTO.getTipoCuenta() ) );
        cuenta.setId( cuentaDTO.getId() );
        cuenta.setNumeroCuenta( cuentaDTO.getNumeroCuenta() );
        if ( cuentaDTO.getSaldoInicial() != null ) {
            cuenta.setSaldoInicial( cuentaDTO.getSaldoInicial() );
        }
        cuenta.setEstado( cuentaDTO.getEstado() );
        cuenta.setCliente( clienteDTOToCliente( cuentaDTO.getCliente() ) );

        return cuenta;
    }

    @Override
    public CuentaDTO cuentaToCuentaDTO(Cuenta cuenta) {
        if ( cuenta == null ) {
            return null;
        }

        CuentaDTO cuentaDTO = new CuentaDTO();

        cuentaDTO.setTipoCuenta( tipoCuentaToTipoCuenta( cuenta.getTipoCuenta() ) );
        cuentaDTO.setId( cuenta.getId() );
        cuentaDTO.setNumeroCuenta( cuenta.getNumeroCuenta() );
        cuentaDTO.setSaldoInicial( cuenta.getSaldoInicial() );
        cuentaDTO.setEstado( cuenta.getEstado() );
        cuentaDTO.setCliente( clienteToClienteDTO( cuenta.getCliente() ) );

        return cuentaDTO;
    }

    protected TipoCuenta tipoCuentaToTipoCuenta(TipoCuenta tipoCuenta) {
        if ( tipoCuenta == null ) {
            return null;
        }

        TipoCuenta tipoCuenta1;

        switch ( tipoCuenta ) {
            case AHORRO: tipoCuenta1 = TipoCuenta.AHORRO;
            break;
            case CORRIENTE: tipoCuenta1 = TipoCuenta.CORRIENTE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + tipoCuenta );
        }

        return tipoCuenta1;
    }

    protected Cliente clienteDTOToCliente(ClienteDTO clienteDTO) {
        if ( clienteDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setClienteId( clienteDTO.getClienteId() );
        cliente.setNombre( clienteDTO.getNombre() );
        cliente.setGenero( clienteDTO.getGenero() );
        cliente.setEdad( clienteDTO.getEdad() );
        cliente.setIdentificacion( clienteDTO.getIdentificacion() );
        cliente.setDireccion( clienteDTO.getDireccion() );
        cliente.setTelefono( clienteDTO.getTelefono() );
        cliente.setPassword( clienteDTO.getPassword() );
        cliente.setEstado( clienteDTO.getEstado() );

        return cliente;
    }

    protected ClienteDTO clienteToClienteDTO(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setClienteId( cliente.getClienteId() );
        clienteDTO.setNombre( cliente.getNombre() );
        clienteDTO.setGenero( cliente.getGenero() );
        clienteDTO.setEdad( cliente.getEdad() );
        clienteDTO.setIdentificacion( cliente.getIdentificacion() );
        clienteDTO.setDireccion( cliente.getDireccion() );
        clienteDTO.setTelefono( cliente.getTelefono() );
        clienteDTO.setEstado( cliente.getEstado() );
        clienteDTO.setPassword( cliente.getPassword() );

        return clienteDTO;
    }
}
