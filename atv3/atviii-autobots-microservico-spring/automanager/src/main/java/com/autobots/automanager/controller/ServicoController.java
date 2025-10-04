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

import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.repositorios.RepositorioServico;

@RestController
@RequestMapping("/servicos")
public class ServicoController {
    @Autowired
    private RepositorioServico repositorioServico;

    @GetMapping
    public CollectionModel<EntityModel<Servico>> listarServicos() {
        List<EntityModel<Servico>> servicos = repositorioServico.findAll().stream()
            .map(servico -> EntityModel.of(servico,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).buscarServico(servico.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).listarServicos()).withRel("servicos")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(servicos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).listarServicos()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Servico>> buscarServico(@PathVariable Long id) {
        Optional<Servico> servico = repositorioServico.findById(id);
        if (servico.isPresent()) {
            EntityModel<Servico> resource = EntityModel.of(servico.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).buscarServico(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).listarServicos()).withRel("servicos")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Servico>> criarServico(@RequestBody Servico servico) {
        Servico salvo = repositorioServico.save(servico);
        EntityModel<Servico> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).buscarServico(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).listarServicos()).withRel("servicos")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Servico>> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        if (!repositorioServico.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servico.setId(id);
        Servico atualizado = repositorioServico.save(servico);
        EntityModel<Servico> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).buscarServico(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).listarServicos()).withRel("servicos")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        if (!repositorioServico.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioServico.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
