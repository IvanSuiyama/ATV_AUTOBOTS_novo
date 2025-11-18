package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@RestController
@RequestMapping("/mercadorias")
public class ControleMercadoria {
    
    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    // ADMIN, GERENTE, VENDEDOR e CLIENTE podem ler mercadorias
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
    @GetMapping
    public ResponseEntity<List<Mercadoria>> listarMercadorias() {
        List<Mercadoria> mercadorias = repositorioMercadoria.findAll();
        return ResponseEntity.ok(mercadorias);
    }

    // ADMIN, GERENTE, VENDEDOR e CLIENTE podem buscar uma mercadoria específica
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> buscarMercadoria(@PathVariable Long id) {
        Optional<Mercadoria> mercadoria = repositorioMercadoria.findById(id);
        if (mercadoria.isPresent()) {
            return ResponseEntity.ok(mercadoria.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ADMIN, GERENTE e VENDEDOR podem criar mercadorias
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    @PostMapping
    public ResponseEntity<Mercadoria> criarMercadoria(@RequestBody Mercadoria mercadoria) {
        // Definir data de cadastro automaticamente
        mercadoria.setCadastro(new java.util.Date());
        Mercadoria salva = repositorioMercadoria.save(mercadoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // ADMIN, GERENTE e VENDEDOR podem atualizar mercadorias
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Mercadoria> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoria) {
        if (!repositorioMercadoria.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mercadoria.setId(id);
        // Preservar data de cadastro original se não informada
        if (mercadoria.getCadastro() == null) {
            java.util.Optional<Mercadoria> existente = repositorioMercadoria.findById(id);
            if (existente.isPresent()) {
                mercadoria.setCadastro(existente.get().getCadastro());
            }
        }
        Mercadoria atualizada = repositorioMercadoria.save(mercadoria);
        return ResponseEntity.ok(atualizada);
    }

    // ADMIN, GERENTE e VENDEDOR podem deletar mercadorias
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMercadoria(@PathVariable Long id) {
        if (!repositorioMercadoria.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioMercadoria.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}