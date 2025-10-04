package com.autobots.automanager.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.repositorios.RepositorioTelefone;

@RestController
@RequestMapping("/telefones")
public class TelefoneController {
    @Autowired
    private RepositorioTelefone repositorioTelefone;

    @GetMapping
    public CollectionModel<EntityModel<Telefone>> listarTelefones() {
        List<EntityModel<Telefone>> telefones = repositorioTelefone.findAll().stream()
            .map(telefone -> EntityModel.of(telefone,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneController.class).buscarTelefone(telefone.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneController.class).listarTelefones()).withRel("telefones")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(telefones,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneController.class).listarTelefones()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Telefone>> buscarTelefone(@PathVariable Long id) {
        Optional<Telefone> telefone = repositorioTelefone.findById(id);
        if (telefone.isPresent()) {
            EntityModel<Telefone> resource = EntityModel.of(telefone.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneController.class).buscarTelefone(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneController.class).listarTelefones()).withRel("telefones")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Telefone>> criarTelefone(@RequestBody Telefone telefone) {
        Telefone salvo = repositorioTelefone.save(telefone);
        EntityModel<Telefone> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneController.class).buscarTelefone(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneController.class).listarTelefones()).withRel("telefones")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Telefone>> atualizarTelefone(@PathVariable Long id, @RequestBody Telefone telefone) {
        if (!repositorioTelefone.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        telefone.setId(id);
        Telefone atualizado = repositorioTelefone.save(telefone);
        EntityModel<Telefone> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneController.class).buscarTelefone(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneController.class).listarTelefones()).withRel("telefones")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTelefone(@PathVariable Long id) {
        if (!repositorioTelefone.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioTelefone.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
