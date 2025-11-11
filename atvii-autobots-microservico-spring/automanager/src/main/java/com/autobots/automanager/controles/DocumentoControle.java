package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;

@RestController
@RequestMapping("/documentos")
public class DocumentoControle {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    private AdicionadorLinkDocumento adicionadorLink = new AdicionadorLinkDocumento();

    @GetMapping
    public ResponseEntity<List<Documento>> listarDocumentos() {
        List<Documento> documentos = documentoRepositorio.findAll();
        adicionadorLink.adicionarLink(documentos);
        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obterDocumento(@PathVariable Long id) {
        Optional<Documento> documento = documentoRepositorio.findById(id);
        if (documento.isPresent()) {
            adicionadorLink.adicionarLink(documento.get());
            return new ResponseEntity<>(documento.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Documento> cadastrarDocumento(@RequestBody Documento documento) {
        Documento salvo = documentoRepositorio.save(documento);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> atualizarDocumento(@PathVariable Long id, @RequestBody Documento atualizacao) {
        Optional<Documento> documentoOpt = documentoRepositorio.findById(id);
        if (documentoOpt.isPresent()) {
            Documento documento = documentoOpt.get();
            documento.setTipo(atualizacao.getTipo());
            documento.setNumero(atualizacao.getNumero());
            Documento atualizado = documentoRepositorio.save(documento);
            adicionadorLink.adicionarLink(atualizado);
            return new ResponseEntity<>(atualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDocumento(@PathVariable Long id) {
        if (documentoRepositorio.existsById(id)) {
            documentoRepositorio.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}