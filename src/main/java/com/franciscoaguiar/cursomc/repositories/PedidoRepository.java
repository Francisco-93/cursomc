package com.franciscoaguiar.cursomc.repositories;

import com.franciscoaguiar.cursomc.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franciscoaguiar.cursomc.domain.Pedido;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

    @Transactional(readOnly = true)
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageable);

}
