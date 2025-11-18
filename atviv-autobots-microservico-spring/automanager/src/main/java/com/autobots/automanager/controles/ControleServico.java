package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.RepositorioServico;

@RestController
@RequestMapping("/servicos")
public class ControleServico {
    
    @Autowired
    private RepositorioServico repositorioServico;

    // ADMIN, GERENTE, VENDEDOR e CLIENTE podem ler serviços
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
    @GetMapping
    public ResponseEntity<List<Servico>> listarServicos() {
        List<Servico> servicos = repositorioServico.findAll();
        return ResponseEntity.ok(servicos);
    }

    // ADMIN, GERENTE, VENDEDOR e CLIENTE podem buscar um serviço específico
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarServico(@PathVariable Long id) {
        Optional<Servico> servico = repositorioServico.findById(id);
        if (servico.isPresent()) {
            return ResponseEntity.ok(servico.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ADMIN, GERENTE e VENDEDOR podem criar serviços
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody Servico servico) {
        Servico salvo = repositorioServico.save(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // ADMIN, GERENTE e VENDEDOR podem atualizar serviços
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        if (!repositorioServico.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servico.setId(id);
        Servico atualizado = repositorioServico.save(servico);
        return ResponseEntity.ok(atualizado);
    }

    // ADMIN, GERENTE e VENDEDOR podem deletar serviços
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        if (!repositorioServico.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioServico.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}