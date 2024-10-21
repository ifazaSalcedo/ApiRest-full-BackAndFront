package com.app.datapart.data.repository;

import com.app.datapart.data.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByDocumentoNro(String doc);

    Page<Cliente> findByNombreContains(String nom, Pageable pag);
}