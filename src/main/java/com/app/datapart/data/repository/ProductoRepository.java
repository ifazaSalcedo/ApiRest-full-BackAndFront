package com.app.datapart.data.repository;

import com.app.datapart.data.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, String> {
    Page<Producto> findByNombreContains(String nom, Pageable pag);
}