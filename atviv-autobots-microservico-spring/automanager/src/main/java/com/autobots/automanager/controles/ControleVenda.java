package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioVenda;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/vendas")
public class ControleVenda {
    
    @Autowired
    private RepositorioVenda repositorioVenda;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    // ADMIN e GERENTE podem ver todas as vendas
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = repositorioVenda.findAll();
        return ResponseEntity.ok(vendas);
    }

    // VENDEDOR pode ver apenas suas próprias vendas
    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/minhas-vendas")
    public ResponseEntity<List<Venda>> listarMinhasVendas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nomeUsuario = auth.getName();
        
        Optional<Usuario> vendedor = repositorioUsuario.findByNomeUsuario(nomeUsuario);
        if (vendedor.isPresent()) {
            List<Venda> vendas = repositorioVenda.findByFuncionarioId(vendedor.get().getId());
            return ResponseEntity.ok(vendas);
        }
        return ResponseEntity.ok(List.of());
    }

    // CLIENTE pode ver apenas suas compras
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/minhas-compras")
    public ResponseEntity<List<Venda>> listarMinhasCompras() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nomeUsuario = auth.getName();
        
        Optional<Usuario> cliente = repositorioUsuario.findByNomeUsuario(nomeUsuario);
        if (cliente.isPresent()) {
            // Implementar lógica específica para clientes aqui
            List<Venda> vendas = repositorioVenda.findAll().stream()
                    .filter(venda -> venda.getCliente() != null)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(vendas);
        }
        return ResponseEntity.ok(List.of());
    }

    // Buscar venda específica com controle de acesso
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVenda(@PathVariable Long id) {
        Optional<Venda> venda = repositorioVenda.findById(id);
        if (venda.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean temPermissao = verificarPermissaoVenda(venda.get(), auth);
            
            if (temPermissao) {
                return ResponseEntity.ok(venda.get());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // VENDEDOR pode criar vendas
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEDOR')")
    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nomeUsuario = auth.getName();
        
        Optional<Usuario> vendedor = repositorioUsuario.findByNomeUsuario(nomeUsuario);
        if (vendedor.isPresent()) {
            venda.setFuncionario(vendedor.get());
        }
        
        Venda salva = repositorioVenda.save(venda);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // VENDEDOR pode atualizar apenas suas vendas
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEDOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Venda> atualizarVenda(@PathVariable Long id, @RequestBody Venda venda) {
        Optional<Venda> vendaExistente = repositorioVenda.findById(id);
        if (!vendaExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!verificarPermissaoVenda(vendaExistente.get(), auth)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        venda.setId(id);
        Venda atualizada = repositorioVenda.save(venda);
        return ResponseEntity.ok(atualizada);
    }

    // ADMIN pode deletar qualquer venda
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        if (!repositorioVenda.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioVenda.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para verificar permissões
    private boolean verificarPermissaoVenda(Venda venda, Authentication auth) {
        String nomeUsuario = auth.getName();
        
        // ADMIN e GERENTE podem ver qualquer venda
        if (auth.getAuthorities().stream().anyMatch(a -> 
            a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_GERENTE"))) {
            return true;
        }
        
        // VENDEDOR pode ver apenas suas vendas
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR"))) {
            return venda.getFuncionario() != null && 
                   venda.getFuncionario().getCredencial().getNomeUsuario().equals(nomeUsuario);
        }
        
        // CLIENTE pode ver apenas suas compras
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"))) {
            return true; // Simplificado - implementar lógica específica
        }
        
        return false;
    }
}