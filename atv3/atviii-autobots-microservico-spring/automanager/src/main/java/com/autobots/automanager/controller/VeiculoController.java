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

import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @GetMapping
    public CollectionModel<EntityModel<Veiculo>> listarVeiculos() {
        List<EntityModel<Veiculo>> veiculos = repositorioVeiculo.findAll().stream()
            .map(veiculo -> EntityModel.of(veiculo,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).buscarVeiculo(veiculo.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).listarVeiculos()).withRel("veiculos")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(veiculos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).listarVeiculos()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Veiculo>> buscarVeiculo(@PathVariable Long id) {
        Optional<Veiculo> veiculo = repositorioVeiculo.findById(id);
        if (veiculo.isPresent()) {
            EntityModel<Veiculo> resource = EntityModel.of(veiculo.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).buscarVeiculo(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).listarVeiculos()).withRel("veiculos")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Veiculo>> criarVeiculo(@RequestBody Veiculo veiculo) {
        Veiculo salvo = repositorioVeiculo.save(veiculo);
        EntityModel<Veiculo> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).buscarVeiculo(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).listarVeiculos()).withRel("veiculos")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Veiculo>> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        if (!repositorioVeiculo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        veiculo.setId(id);
        Veiculo atualizado = repositorioVeiculo.save(veiculo);
        EntityModel<Veiculo> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).buscarVeiculo(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).listarVeiculos()).withRel("veiculos")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id) {
        if (!repositorioVeiculo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        // Verificar se o veículo tem vendas associadas
        Optional<Veiculo> veiculo = repositorioVeiculo.findById(id);
        if (veiculo.isPresent() && !veiculo.get().getVendas().isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }
        
        repositorioVeiculo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
