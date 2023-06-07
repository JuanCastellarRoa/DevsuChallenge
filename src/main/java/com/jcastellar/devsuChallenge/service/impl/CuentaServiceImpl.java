package com.jcastellar.devsuChallenge.service.impl;

import com.jcastellar.devsuChallenge.dto.CuentaDTO;
import com.jcastellar.devsuChallenge.entity.Cliente;
import com.jcastellar.devsuChallenge.entity.Cuenta;
import com.jcastellar.devsuChallenge.repository.ClienteRepository;
import com.jcastellar.devsuChallenge.repository.CuentaRepository;
import com.jcastellar.devsuChallenge.repository.MovimientoRepository;
import com.jcastellar.devsuChallenge.service.CuentaService;
import com.jcastellar.devsuChallenge.utility.excepciones.ErrorInterno;
import com.jcastellar.devsuChallenge.utility.excepciones.NoEncontrado;
import com.jcastellar.devsuChallenge.utility.excepciones.PeticionErronea;
import com.jcastellar.devsuChallenge.utility.mapper.CuentaMapper;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

@Service
public class CuentaServiceImpl implements CuentaService {

  private static final Logger logger = LoggerFactory.getLogger(CuentaServiceImpl.class);
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
    this.movimientoRepository = movimientoRepository;
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
      logger.warn("Verificar la identificación del cliente");
      throw new PeticionErronea("Verificar la identificación del cliente");
    }
    try {
      Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
      cuenta.setCliente(clienteOpt.get());
      final Cuenta nuevaCuenta = cuentaRepository.save(cuenta);
      logger.info("Cuenta creada!");
      return cuentaMapper.cuentaToCuentaDTO(nuevaCuenta);
    } catch (Exception e) {
      logger.warn("Verificar los datos de la cuenta");
      throw new ErrorInterno("Verificar los datos de la cuenta");
    }
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    //verificar saldo antes de eliminar
    try {
      cuentaRepository.deleteById(id);
      logger.info("Cuenta eliminada!");
    } catch (Exception e) {
      logger.warn("Cuenta no encontrada");
      throw new NoEncontrado("Cuenta no encontrada");
    }
  }

  @Override
  @Transactional
  public CuentaDTO updateCuenta(Long id, CuentaDTO cuentaDTO) {
    Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);
    if (cuentaOpt.isPresent()) {
      Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
      Cuenta cuentaActualizada = cuentaOpt.get();
      cuentaActualizada.setNumeroCuenta(cuenta.getNumeroCuenta());
      cuentaActualizada.setTipoCuenta(cuenta.getTipoCuenta());
      cuentaActualizada.setSaldoInicial(cuenta.getSaldoInicial());
      cuentaActualizada.setEstado(cuenta.getEstado());
      cuentaRepository.save(cuentaActualizada);
      logger.info("Cuenta actualizada!");
      return cuentaMapper.cuentaToCuentaDTO(cuentaActualizada);
    } else {
      logger.warn("Cuenta no encontrada!");
      throw new NoEncontrado("Cuenta no encontrada");
    }
  }

  @Override
  @Transactional
  public CuentaDTO actualizacionParcialByFields(Long id, Map<String, Object> fields) {
    Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);
    if (cuentaOpt.isPresent()) {
      fields.forEach((key, value) -> {
        if (key.equals("tipoCuenta")) {
          value = cuentaMapper.stringToTipoCuenta(value.toString());
        }
        if (key.equals("estado")) {
          value = cuentaMapper.stringToBoolean(value.toString());
        }
        Field field = ReflectionUtils.findField(Cuenta.class, key);
        field.setAccessible(true);
        ReflectionUtils.setField(field, cuentaOpt.get(), value);
      });
      cuentaRepository.save(cuentaOpt.get());
      logger.info("Cuenta actualizada!");
      CuentaDTO cuentaDTO = cuentaMapper.cuentaToCuentaDTO(cuentaOpt.get());
      return cuentaDTO;
    } else {
      logger.warn("Cuenta no encontrada!");
      throw new NoEncontrado("Cuenta no encontrada");
    }
  }
}