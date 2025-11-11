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

import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.repositorios.RepositorioCredencial;

@RestController
@RequestMapping("/credenciais")
public class CredencialController {
    @Autowired
    private RepositorioCredencial repositorioCredencial;

    @GetMapping
    public CollectionModel<EntityModel<Credencial>> listarCredenciais() {
        List<EntityModel<Credencial>> credenciais = repositorioCredencial.findAll().stream()
            .map(credencial -> EntityModel.of(credencial,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).buscarCredencial(credencial.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).listarCredenciais()).withRel("credenciais")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(credenciais,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).listarCredenciais()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Credencial>> buscarCredencial(@PathVariable Long id) {
        Optional<Credencial> credencial = repositorioCredencial.findById(id);
        if (credencial.isPresent()) {
            EntityModel<Credencial> resource = EntityModel.of(credencial.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).buscarCredencial(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).listarCredenciais()).withRel("credenciais")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Credencial>> criarCredencial(@RequestBody Credencial credencial) {
        Credencial salvo = repositorioCredencial.save(credencial);
        EntityModel<Credencial> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).buscarCredencial(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).listarCredenciais()).withRel("credenciais")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Credencial>> atualizarCredencial(@PathVariable Long id, @RequestBody Credencial credencial) {
        if (!repositorioCredencial.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        credencial.setId(id);
        Credencial atualizado = repositorioCredencial.save(credencial);
        EntityModel<Credencial> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).buscarCredencial(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).listarCredenciais()).withRel("credenciais")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCredencial(@PathVariable Long id) {
        if (!repositorioCredencial.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioCredencial.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
