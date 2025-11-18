package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.RepositorioCliente;

@RestController
@RequestMapping("/clientes")
public class ControleCliente {
	@Autowired
	private RepositorioCliente repositorio;

	// ADMIN e GERENTE podem criar clientes
	@PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
	@PostMapping
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
		Cliente salvo = repositorio.save(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}
	
	// ADMIN, GERENTE e VENDEDOR podem listar clientes
	@PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping
	public ResponseEntity<List<Cliente>> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		return ResponseEntity.ok(clientes);
	}
	
	// Buscar cliente específico
	@PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
		Optional<Cliente> cliente = repositorio.findById(id);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// ADMIN e GERENTE podem atualizar clientes
	@PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
		if (!repositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(id);
		Cliente atualizado = repositorio.save(cliente);
		return ResponseEntity.ok(atualizado);
	}
	
	// ADMIN pode deletar clientes
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
		if (!repositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}