package com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.autobots.automanager.entidades.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
	@Query("SELECT u FROM Usuario u WHERE u.credencial.nomeUsuario = :nomeUsuario")
	Optional<Usuario> findByNomeUsuario(@Param("nomeUsuario") String nomeUsuario);
}