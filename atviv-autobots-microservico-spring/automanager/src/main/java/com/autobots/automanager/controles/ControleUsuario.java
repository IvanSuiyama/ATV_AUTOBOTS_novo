package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.Perfil;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/usuarios")
public class ControleUsuario {

	@Autowired
	private RepositorioUsuario repositorio;

	// Endpoint público para cadastro inicial
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
		BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
		try {
			Credencial credencial = new Credencial();
			credencial.setNomeUsuario(usuario.getCredencial().getNomeUsuario());
			String senha = codificador.encode(usuario.getCredencial().getSenha());
			credencial.setSenha(senha);
			usuario.setCredencial(credencial);
			Usuario salvo = repositorio.save(usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	// ADMIN pode ver todos os usuários
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Usuario>> obterUsuarios() {
		List<Usuario> usuarios = repositorio.findAll();
		return ResponseEntity.ok(usuarios);
	}

	// ADMIN e GERENTE podem buscar usuário específico
	@PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
		Optional<Usuario> usuario = repositorio.findById(id);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// ADMIN pode criar usuários com qualquer perfil
	// GERENTE pode criar usuários com perfil GERENTE, VENDEDOR ou CLIENTE
	@PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
	@PostMapping
	public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario, Authentication auth) {
		// Verificar se GERENTE está tentando criar ADMIN
		if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_GERENTE"))) {
			boolean temPerfilAdmin = usuario.getPerfis().stream()
					.anyMatch(perfil -> perfil == Perfil.ROLE_ADMIN);
			if (temPerfilAdmin) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}

		BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
		String senha = codificador.encode(usuario.getCredencial().getSenha());
		usuario.getCredencial().setSenha(senha);
		Usuario salvo = repositorio.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}

	// ADMIN pode atualizar qualquer usuário
	// GERENTE pode atualizar usuários com perfil GERENTE, VENDEDOR ou CLIENTE
	// Usuários podem atualizar seu próprio perfil (exceto mudar perfis)
	@PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario, Authentication auth) {
		Optional<Usuario> usuarioExistente = repositorio.findById(id);
		if (!usuarioExistente.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		// Verificar permissões baseadas no perfil
		String nomeUsuario = auth.getName();
		boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		boolean isGerente = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_GERENTE"));
		
		// Se não for ADMIN ou GERENTE, só pode atualizar a si mesmo
		if (!isAdmin && !isGerente) {
			Optional<Usuario> usuarioLogado = repositorio.findByNomeUsuario(nomeUsuario);
			if (!usuarioLogado.isPresent() || !usuarioLogado.get().getId().equals(id)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			// Usuário comum não pode alterar seus próprios perfis
			usuario.setPerfis(usuarioExistente.get().getPerfis());
		}

		usuario.setId(id);
		if (usuario.getCredencial().getSenha() != null && !usuario.getCredencial().getSenha().isEmpty()) {
			BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
			usuario.getCredencial().setSenha(codificador.encode(usuario.getCredencial().getSenha()));
		} else {
			usuario.getCredencial().setSenha(usuarioExistente.get().getCredencial().getSenha());
		}
		
		Usuario atualizado = repositorio.save(usuario);
		return ResponseEntity.ok(atualizado);
	}

	// ADMIN pode deletar qualquer usuário
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
		if (!repositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// Endpoint para usuário ver seu próprio perfil
	@PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
	@GetMapping("/meu-perfil")
	public ResponseEntity<Usuario> meuPerfil(Authentication auth) {
		String nomeUsuario = auth.getName();
		Optional<Usuario> usuario = repositorio.findByNomeUsuario(nomeUsuario);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}