package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.dto.ClienteCompletoDTO;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;
	// @Autowired
	// private ClienteSelecionador selecionador;
	@Autowired
	private DocumentoRepositorio documentoRepositorio;
	@Autowired
	private TelefoneRepositorio telefoneRepositorio;
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;

	@GetMapping("/cliente/{id}")
	public ClienteCompletoDTO obterCliente(@PathVariable long id) {
		Cliente cliente = repositorio.findById(id).orElse(null);
		if (cliente == null) return null;
		ClienteCompletoDTO dto = new ClienteCompletoDTO();
		dto.setId(cliente.getId());
		dto.setNome(cliente.getNome());
		dto.setNomeSocial(cliente.getNomeSocial());
		dto.setDataCadastro(cliente.getDataCadastro());
		dto.setDataNascimento(cliente.getDataNascimento());
		dto.setDocumentos(documentoRepositorio.findAll().stream().filter(d -> d.getCliente() != null && d.getCliente().getId().equals(id)).toList());
		dto.setTelefones(telefoneRepositorio.findAll().stream().filter(t -> t.getCliente() != null && t.getCliente().getId().equals(id)).toList());
		dto.setEnderecos(enderecoRepositorio.findAll().stream().filter(e -> e.getCliente() != null && e.getCliente().getId().equals(id)).toList());
		return dto;
	}

	@GetMapping("/clientes")
	public List<Cliente> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		return clientes;
	}

	@PostMapping("/cadastro")
	public void cadastrarCliente(@RequestBody Cliente cliente) {
		repositorio.save(cliente);
	}

	@PutMapping("/atualizar")
	public void atualizarCliente(@RequestBody Cliente atualizacao) {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		ClienteAtualizador atualizador = new ClienteAtualizador();
		atualizador.atualizar(cliente, atualizacao);
		repositorio.save(cliente);
	}

	@DeleteMapping("/excluir")
	public void excluirCliente(@RequestBody Cliente exclusao) {
		Cliente cliente = repositorio.getById(exclusao.getId());
		repositorio.delete(cliente);
	}
}
