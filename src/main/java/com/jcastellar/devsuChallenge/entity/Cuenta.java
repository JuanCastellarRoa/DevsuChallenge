package com.jcastellar.devsuChallenge.entity;

import com.jcastellar.devsuChallenge.utility.enumerador.TipoCuenta;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuentas")
public class Cuenta implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Pattern(regexp = "^[0-9]{6,11}$", message = "Verificar el número de la cuenta")
  @Column(name = "numero_de_cuenta", unique = true)
  private String numeroCuenta;


  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_de_cuenta")
  private TipoCuenta tipoCuenta;

  @NotNull
  @Column(name = "saldo_inicial")
  private double saldoInicial;

  private Boolean estado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Movimiento> movimientos;

}