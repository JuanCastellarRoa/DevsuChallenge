package com.jcastellar.devsuChallenge.repository;

import com.jcastellar.devsuChallenge.entity.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  Optional<Cliente> findByIdentificacion(String identificacion);


}