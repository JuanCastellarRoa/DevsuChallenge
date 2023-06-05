package com.jcastellar.devsuChallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jcastellar.devsuChallenge.utility.enumerador.Genero;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO {

  private Long clienteId;

  @NotEmpty
  @NotBlank
  @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "Verificar el nombre")
  private String nombre;

  @Enumerated(EnumType.STRING)
  private Genero genero;

  @NotNull
  @Min(1)
  @Max(120)
  private Integer edad;

  @NotEmpty
  @NotBlank
  @Pattern(regexp = "^[0-9]{6,13}$", message = "Verificar el número de identificación")
  private String identificacion;

  @NotEmpty
  @NotBlank
  @Pattern(regexp = "^[A-Za-z0-9\\s#-]+$", message = "Verificar la dirección")
  private String direccion;

  @NotEmpty
  @NotBlank
  @Pattern(regexp = "^[0-9]{6,13}$", message = "Verificar el número de teléfono")
  private String telefono;

  private Boolean estado;

  @NotEmpty
  @NotBlank
  private String password;
}