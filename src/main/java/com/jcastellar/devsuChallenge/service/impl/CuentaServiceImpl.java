package com.jcastellar.devsuChallenge.service.impl;

import com.jcastellar.devsuChallenge.dto.CuentaDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import com.jcastellar.devsuChallenge.entity.Cuenta;
import com.jcastellar.devsuChallenge.entity.Movimiento;
import com.jcastellar.devsuChallenge.repository.ClienteRepository;
import com.jcastellar.devsuChallenge.repository.CuentaRepository;
import com.jcastellar.devsuChallenge.repository.MovimientoRepository;
import com.jcastellar.devsuChallenge.service.CuentaService;
import com.jcastellar.devsuChallenge.service.MovimientoService;
import com.jcastellar.devsuChallenge.utility.enumerador.TipoMovimiento;
import com.jcastellar.devsuChallenge.utility.excepciones.ErrorInterno;
import com.jcastellar.devsuChallenge.utility.excepciones.NoEncontrado;
import com.jcastellar.devsuChallenge.utility.excepciones.PeticionErronea;
import com.jcastellar.devsuChallenge.utility.mapper.CuentaMapper;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

@Service
public class CuentaServiceImpl implements CuentaService {

  private final CuentaRepository cuentaRepository;
  private final CuentaMapper cuentaMapper;
  private final ClienteRepository clienteRepository;
  private final MovimientoRepository movimientoRepository;

  @Autowired
  public CuentaServiceImpl(CuentaRepository cuentaRepository,
      CuentaMapper cuentaMapper,
      ClienteRepository clienteRepository, MovimientoRepository movimientoRepository) {
    this.cuentaRepository = cuentaRepository;
    this.cuentaMapper = cuentaMapper;
    this.clienteRepository = clienteRepository;
    this.movimientoRepository=movimientoRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<CuentaDTO> getCuentas() {
    List<Cuenta> cuentas = cuentaRepository.findAll();
    return cuentas.stream().map(cuentaMapper::cuentaToCuentaDTO).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public CuentaDTO getCuenta(Long id) {
    return cuentaRepository.findById(id).map(cuentaMapper::cuentaToCuentaDTO).orElse(null);
  }

  @Override
  @Transactional
  public CuentaDTO createCuenta(CuentaDTO cuentaDTO) {
    Optional<Cliente> clienteOpt = clienteRepository.findByIdentificacion(
        cuentaDTO.getCliente().getIdentificacion());
    if (clienteOpt.isEmpty()) {
      throw new PeticionErronea("Verificar la identificación del cliente");
    }
    try {
      Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
      cuenta.setCliente(clienteOpt.get());
      final Cuenta nuevaCuenta = cuentaRepository.save(cuenta);
      //final Movimiento movimientoInicial = getNewMovimientoInicial(nuevaCuenta);
      //movimientoRepository.save(movimientoInicial);
      return cuentaMapper.cuentaToCuentaDTO(nuevaCuenta);
    } catch (Exception e) {
      throw new ErrorInterno("Verificar los datos de la cuenta");
    }
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    try {
      cuentaRepository.deleteById(id);
    } catch (Exception e) {
      throw new NoEncontrado("Cuenta no encontrada");
    }
  }

  @Override
  @Transactional
  public CuentaDTO updateCuenta(Long id, CuentaDTO cuentaDTO) {
    Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);
    if (cuentaOpt.isPresent()) {
      Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
      Cuenta cuentaActualizada = cuentaRepository.findById(id).get();
      cuentaActualizada.setNumeroCuenta(cuenta.getNumeroCuenta());
      cuentaActualizada.setTipoCuenta(cuenta.getTipoCuenta());
      cuentaActualizada.setSaldoInicial(cuenta.getSaldoInicial());
      cuentaActualizada.setEstado(cuenta.getEstado());
      cuentaRepository.save(cuentaActualizada);
      //final Movimiento movimiento = getNewMovimientoInicial(cuentaActualizada);
      //movimientoRepository.save(movimiento);
      return cuentaMapper.cuentaToCuentaDTO(cuentaActualizada);
    } else {
      throw new NoEncontrado("Cuenta no encontrada");
    }
  }

  @Override
  @Transactional
  public CuentaDTO actualizacionParcialByFields(Long id, Map<String, Object> fields) {
    Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);
    if (cuentaOpt.isPresent()) {

      Optional<Cuenta> cuentaActualizada = cuentaRepository.findById(id);
      fields.forEach((key, value) -> {
        Field field = ReflectionUtils.findField(Cuenta.class, key);
        field.setAccessible(true);
        ReflectionUtils.setField(field, cuentaActualizada.get(), value);
      });
      cuentaRepository.save(cuentaActualizada.get());
      CuentaDTO cuentaDTO = cuentaMapper.cuentaToCuentaDTO(cuentaActualizada.get());
      return cuentaDTO;
    } else {
      throw new NoEncontrado("Cuenta no encontrada");
    }
  }

  /*private Movimiento getNewMovimientoInicial(Cuenta cuenta) {
    Movimiento movimiento = new Movimiento();
    movimiento.setCuenta(cuenta);
    movimiento.setTipoMovimiento(TipoMovimiento.DEPOSITO);
    movimiento.setFecha(LocalDate.now());
    movimiento.setValor(cuenta.getSaldoInicial());
    movimiento.setSaldo(cuenta.getSaldoInicial());

    return movimiento;
  }*/
}