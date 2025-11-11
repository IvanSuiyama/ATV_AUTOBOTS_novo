package com.autobots.automanager.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.autobots.automanager.entidades.Venda;

public interface RepositorioVenda extends JpaRepository<Venda, Long> {
	
	@Query("SELECT v FROM Venda v WHERE v.cliente.id = :clienteId")
	List<Venda> findByClienteId(@Param("clienteId") Long clienteId);
	
	@Query("SELECT v FROM Venda v WHERE v.funcionario.id = :funcionarioId")
	List<Venda> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

}