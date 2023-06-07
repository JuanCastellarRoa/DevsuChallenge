package com.jcastellar.devsuChallenge.entity;

import com.jcastellar.devsuChallenge.utility.enumerador.TipoMovimiento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimientos")
public class Movimiento implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate fecha;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_de_movimiento")
  private TipoMovimiento tipoMovimiento;

  @NotNull
  private Double valor;

  @NotNull
  private Double saldo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "numero_de_cuenta")
  private Cuenta cuenta;

}