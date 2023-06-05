package com.jcastellar.devsuChallenge.entity;

import com.jcastellar.devsuChallenge.utility.enumerador.Genero;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("null")
@Table(name = "personas")
public class Persona implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cliente_id")
  private Long clienteId;

  @NotEmpty
  @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "Verificar el nombre")
  private String nombre;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Genero genero;

  @NotNull
  @Min(0)
  @Max(120)
  private Integer edad;

  @NotEmpty
  @Column(unique = true)
  @Pattern(regexp = "^[0-9]{6,13}$", message = "Verificar el número de identificación")
  private String identificacion;

  @NotEmpty
  @Pattern(regexp = "^[A-Za-z0-9\\s#-]+$", message = "Verificar la dirección")
  private String direccion;

  @NotEmpty
  @Pattern(regexp = "^[0-9]{6,13}$", message = "Verificar el número de teléfono")
  private String telefono;

}