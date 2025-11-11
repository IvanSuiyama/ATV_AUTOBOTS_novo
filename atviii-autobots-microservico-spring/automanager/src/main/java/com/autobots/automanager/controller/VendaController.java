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

import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    @Autowired
    private RepositorioVenda repositorioVenda;

    @GetMapping
    public CollectionModel<EntityModel<Venda>> listarVendas() {
        List<EntityModel<Venda>> vendas = repositorioVenda.findAll().stream()
            .map(venda -> EntityModel.of(venda,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).buscarVenda(venda.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).listarVendas()).withRel("vendas")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(vendas,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).listarVendas()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Venda>> buscarVenda(@PathVariable Long id) {
        Optional<Venda> venda = repositorioVenda.findById(id);
        if (venda.isPresent()) {
            EntityModel<Venda> resource = EntityModel.of(venda.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).buscarVenda(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).listarVendas()).withRel("vendas")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Venda>> criarVenda(@RequestBody Venda venda) {
        Venda salvo = repositorioVenda.save(venda);
        EntityModel<Venda> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).buscarVenda(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).listarVendas()).withRel("vendas")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Venda>> atualizarVenda(@PathVariable Long id, @RequestBody Venda venda) {
        if (!repositorioVenda.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        venda.setId(id);
        Venda atualizado = repositorioVenda.save(venda);
        EntityModel<Venda> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).buscarVenda(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).listarVendas()).withRel("vendas")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        if (!repositorioVenda.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioVenda.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
