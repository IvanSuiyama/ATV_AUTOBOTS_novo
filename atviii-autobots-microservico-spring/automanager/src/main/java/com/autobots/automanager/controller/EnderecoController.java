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

import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.repositorios.RepositorioEndereco;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    @Autowired
    private RepositorioEndereco repositorioEndereco;

    @GetMapping
    public CollectionModel<EntityModel<Endereco>> listarEnderecos() {
        List<EntityModel<Endereco>> enderecos = repositorioEndereco.findAll().stream()
            .map(endereco -> EntityModel.of(endereco,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).buscarEndereco(endereco.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).listarEnderecos()).withRel("enderecos")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(enderecos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).listarEnderecos()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Endereco>> buscarEndereco(@PathVariable Long id) {
        Optional<Endereco> endereco = repositorioEndereco.findById(id);
        if (endereco.isPresent()) {
            EntityModel<Endereco> resource = EntityModel.of(endereco.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).buscarEndereco(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).listarEnderecos()).withRel("enderecos")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Endereco>> criarEndereco(@RequestBody Endereco endereco) {
        Endereco salvo = repositorioEndereco.save(endereco);
        EntityModel<Endereco> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).buscarEndereco(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).listarEnderecos()).withRel("enderecos")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Endereco>> atualizarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        if (!repositorioEndereco.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        endereco.setId(id);
        Endereco atualizado = repositorioEndereco.save(endereco);
        EntityModel<Endereco> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).buscarEndereco(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoController.class).listarEnderecos()).withRel("enderecos")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        if (!repositorioEndereco.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioEndereco.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
