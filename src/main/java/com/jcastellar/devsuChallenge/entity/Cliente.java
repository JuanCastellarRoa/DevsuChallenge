package com.jcastellar.devsuChallenge.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class Cliente extends Persona implements Serializable {

  /* Como la clase Persona ya tiene una PK definida con la anotación @Id, esta PK se hereda automáticamente a la clase Cliente.
  Esto significa que la clase Cliente también tendrá una PK llamada 'clienteId', que es la misma que la de la clase Persona*/

  @NotEmpty
  //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$",
  //    message = "Verificar contraseña: Min 4 y Max 12 caracteres, al menos 1 mayuscula, 1 minuscula, 1 caracter especial y 1 digito.")
  private String password;

  private Boolean estado;

  @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Cuenta> cuentas;

}