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

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaController {
    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @GetMapping
    public CollectionModel<EntityModel<Mercadoria>> listarMercadorias() {
        List<EntityModel<Mercadoria>> mercadorias = repositorioMercadoria.findAll().stream()
            .map(mercadoria -> EntityModel.of(mercadoria,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).buscarMercadoria(mercadoria.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).listarMercadorias()).withRel("mercadorias")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(mercadorias,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).listarMercadorias()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Mercadoria>> buscarMercadoria(@PathVariable Long id) {
        Optional<Mercadoria> mercadoria = repositorioMercadoria.findById(id);
        if (mercadoria.isPresent()) {
            EntityModel<Mercadoria> resource = EntityModel.of(mercadoria.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).buscarMercadoria(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).listarMercadorias()).withRel("mercadorias")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Mercadoria>> criarMercadoria(@RequestBody Mercadoria mercadoria) {
        Mercadoria salvo = repositorioMercadoria.save(mercadoria);
        EntityModel<Mercadoria> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).buscarMercadoria(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).listarMercadorias()).withRel("mercadorias")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Mercadoria>> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoria) {
        if (!repositorioMercadoria.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mercadoria.setId(id);
        Mercadoria atualizado = repositorioMercadoria.save(mercadoria);
        EntityModel<Mercadoria> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).buscarMercadoria(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).listarMercadorias()).withRel("mercadorias")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMercadoria(@PathVariable Long id) {
        if (!repositorioMercadoria.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioMercadoria.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
