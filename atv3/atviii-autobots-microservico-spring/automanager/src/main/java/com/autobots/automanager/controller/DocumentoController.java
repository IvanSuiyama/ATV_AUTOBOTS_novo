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

import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.repositorios.RepositorioDocumento;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {
    @Autowired
    private RepositorioDocumento repositorioDocumento;

    @GetMapping
    public CollectionModel<EntityModel<Documento>> listarDocumentos() {
        List<EntityModel<Documento>> documentos = repositorioDocumento.findAll().stream()
            .map(documento -> EntityModel.of(documento,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoController.class).buscarDocumento(documento.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoController.class).listarDocumentos()).withRel("documentos")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(documentos,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoController.class).listarDocumentos()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Documento>> buscarDocumento(@PathVariable Long id) {
        Optional<Documento> documento = repositorioDocumento.findById(id);
        if (documento.isPresent()) {
            EntityModel<Documento> resource = EntityModel.of(documento.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoController.class).buscarDocumento(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoController.class).listarDocumentos()).withRel("documentos")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Documento>> criarDocumento(@RequestBody Documento documento) {
        Documento salvo = repositorioDocumento.save(documento);
        EntityModel<Documento> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoController.class).buscarDocumento(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoController.class).listarDocumentos()).withRel("documentos")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Documento>> atualizarDocumento(@PathVariable Long id, @RequestBody Documento documento) {
        if (!repositorioDocumento.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        documento.setId(id);
        Documento atualizado = repositorioDocumento.save(documento);
        EntityModel<Documento> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoController.class).buscarDocumento(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoController.class).listarDocumentos()).withRel("documentos")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDocumento(@PathVariable Long id) {
        if (!repositorioDocumento.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioDocumento.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
