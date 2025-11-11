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

import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.repositorios.RepositorioEmail;

@RestController
@RequestMapping("/emails")
public class EmailController {
    @Autowired
    private RepositorioEmail repositorioEmail;

    @GetMapping
    public CollectionModel<EntityModel<Email>> listarEmails() {
        List<EntityModel<Email>> emails = repositorioEmail.findAll().stream()
            .map(email -> EntityModel.of(email,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).buscarEmail(email.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).listarEmails()).withRel("emails")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(emails,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).listarEmails()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Email>> buscarEmail(@PathVariable Long id) {
        Optional<Email> email = repositorioEmail.findById(id);
        if (email.isPresent()) {
            EntityModel<Email> resource = EntityModel.of(email.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).buscarEmail(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).listarEmails()).withRel("emails")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Email>> criarEmail(@RequestBody Email email) {
        Email salvo = repositorioEmail.save(email);
        EntityModel<Email> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).buscarEmail(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).listarEmails()).withRel("emails")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Email>> atualizarEmail(@PathVariable Long id, @RequestBody Email email) {
        if (!repositorioEmail.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        email.setId(id);
        Email atualizado = repositorioEmail.save(email);
        EntityModel<Email> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).buscarEmail(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).listarEmails()).withRel("emails")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmail(@PathVariable Long id) {
        if (!repositorioEmail.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioEmail.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
