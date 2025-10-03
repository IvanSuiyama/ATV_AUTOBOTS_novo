package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;

@RestController
@RequestMapping("/telefones")
public class TelefoneControle {

    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    private AdicionadorLinkTelefone adicionadorLink = new AdicionadorLinkTelefone();

    @GetMapping
    public ResponseEntity<List<Telefone>> listarTelefones() {
        List<Telefone> telefones = telefoneRepositorio.findAll();
        adicionadorLink.adicionarLink(telefones);
        return new ResponseEntity<>(telefones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Telefone> obterTelefone(@PathVariable Long id) {
        Optional<Telefone> telefone = telefoneRepositorio.findById(id);
        if (telefone.isPresent()) {
            adicionadorLink.adicionarLink(telefone.get());
            return new ResponseEntity<>(telefone.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Telefone> cadastrarTelefone(@RequestBody Telefone telefone) {
        Telefone salvo = telefoneRepositorio.save(telefone);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Telefone> atualizarTelefone(@PathVariable Long id, @RequestBody Telefone atualizacao) {
        Optional<Telefone> telefoneOpt = telefoneRepositorio.findById(id);
        if (telefoneOpt.isPresent()) {
            Telefone telefone = telefoneOpt.get();
            telefone.setDdd(atualizacao.getDdd());
            telefone.setNumero(atualizacao.getNumero());
            Telefone atualizado = telefoneRepositorio.save(telefone);
            adicionadorLink.adicionarLink(atualizado);
            return new ResponseEntity<>(atualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTelefone(@PathVariable Long id) {
        if (telefoneRepositorio.existsById(id)) {
            telefoneRepositorio.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}