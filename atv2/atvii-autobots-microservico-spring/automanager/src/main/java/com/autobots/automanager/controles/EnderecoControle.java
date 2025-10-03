package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;

@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {

	@Autowired
	private EnderecoRepositorio enderecoRepositorio;

	private AdicionadorLinkEndereco adicionadorLink = new AdicionadorLinkEndereco();

	@GetMapping
	public ResponseEntity<List<Endereco>> listarEnderecos() {
		List<Endereco> enderecos = enderecoRepositorio.findAll();
		adicionadorLink.adicionarLink(enderecos);
		return new ResponseEntity<>(enderecos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable Long id) {
		Optional<Endereco> endereco = enderecoRepositorio.findById(id);
		if (endereco.isPresent()) {
			adicionadorLink.adicionarLink(endereco.get());
			return new ResponseEntity<>(endereco.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody Endereco endereco) {
		Endereco salvo = enderecoRepositorio.save(endereco);
		adicionadorLink.adicionarLink(salvo);
		return new ResponseEntity<>(salvo, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Endereco> atualizarEndereco(@PathVariable Long id, @RequestBody Endereco atualizacao) {
		Optional<Endereco> enderecoOpt = enderecoRepositorio.findById(id);
		if (enderecoOpt.isPresent()) {
			Endereco endereco = enderecoOpt.get();
			// Atualize os campos necessários
			endereco.setEstado(atualizacao.getEstado());
			endereco.setCidade(atualizacao.getCidade());
			endereco.setBairro(atualizacao.getBairro());
			endereco.setRua(atualizacao.getRua());
			endereco.setNumero(atualizacao.getNumero());
			endereco.setCodigoPostal(atualizacao.getCodigoPostal());
			endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());
			Endereco atualizado = enderecoRepositorio.save(endereco);
			adicionadorLink.adicionarLink(atualizado);
			return new ResponseEntity<>(atualizado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirEndereco(@PathVariable Long id) {
		if (enderecoRepositorio.existsById(id)) {
			enderecoRepositorio.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
