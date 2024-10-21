package com.app.datapart.data.repository;

import com.app.datapart.data.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT COALESCE(MAX(p.numeroControlPedido), 0) + 1 nropedido FROM Pedido p")
    Integer controlNumerPedido();
}