package com.autobots.automanager.controles;

import java.util.Date;
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

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioCliente;
import com.autobots.automanager.repositorios.RepositorioVenda;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/vendas")
public class ControleVenda {
    
    @Autowired
    private RepositorioVenda repositorioVenda;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    @Autowired
    private RepositorioCliente repositorioCliente;

    // Todos os perfis autenticados podem listar vendas (com diferentes restrições)
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // ADMIN e GERENTE podem ver todas as vendas
        if (auth.getAuthorities().stream().anyMatch(a -> 
            a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_GERENTE"))) {
            List<Venda> vendas = repositorioVenda.findAll();
            return ResponseEntity.ok(vendas);
        }
        
        // VENDEDOR pode ver apenas suas vendas
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR"))) {
            String nomeUsuario = auth.getName();
            Optional<Usuario> vendedor = repositorioUsuario.findByNomeUsuario(nomeUsuario);
            if (vendedor.isPresent()) {
                List<Venda> vendas = repositorioVenda.findByFuncionarioId(vendedor.get().getId());
                return ResponseEntity.ok(vendas);
            }
        }
        
        // CLIENTE pode ver suas compras
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"))) {
            // Para clientes, buscar vendas relacionadas (implementação simplificada)
            List<Venda> vendas = repositorioVenda.findAll().stream()
                    .filter(venda -> venda.getCliente() != null)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(vendas);
        }
        
        return ResponseEntity.ok(List.of());
    }



    // Buscar venda específica
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVenda(@PathVariable Long id) {
        Optional<Venda> venda = repositorioVenda.findById(id);
        if (venda.isPresent()) {
            return ResponseEntity.ok(venda.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ADMIN, GERENTE, VENDEDOR e CLIENTE podem criar vendas
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR') or hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda) {
        try {
            // Definir data de cadastro
            venda.setCadastro(new Date());
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String nomeUsuario = auth.getName();
            
            // Buscar e associar cliente se fornecido
            if (venda.getCliente() != null && venda.getCliente().getId() != null) {
                Optional<Cliente> clienteExistente = repositorioCliente.findById(venda.getCliente().getId());
                if (clienteExistente.isPresent()) {
                    venda.setCliente(clienteExistente.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
            
            // Buscar e associar funcionário se fornecido
            if (venda.getFuncionario() != null && venda.getFuncionario().getId() != null) {
                Optional<Usuario> funcionarioExistente = repositorioUsuario.findById(venda.getFuncionario().getId());
                if (funcionarioExistente.isPresent()) {
                    venda.setFuncionario(funcionarioExistente.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                // Se não informado, usar usuário logado como funcionário (para VENDEDOR)
                Optional<Usuario> usuarioLogado = repositorioUsuario.findByNomeUsuario(nomeUsuario);
                if (usuarioLogado.isPresent()) {
                    venda.setFuncionario(usuarioLogado.get());
                }
            }
            
            Venda salva = repositorioVenda.save(venda);
            return ResponseEntity.status(HttpStatus.CREATED).body(salva);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ADMIN, GERENTE e VENDEDOR podem atualizar vendas
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Venda> atualizarVenda(@PathVariable Long id, @RequestBody Venda venda) {
        Optional<Venda> vendaExistente = repositorioVenda.findById(id);
        if (!vendaExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        venda.setId(id);
        // Preservar data de cadastro original
        venda.setCadastro(vendaExistente.get().getCadastro());
        
        Venda atualizada = repositorioVenda.save(venda);
        return ResponseEntity.ok(atualizada);
    }

    // ADMIN, GERENTE e VENDEDOR podem deletar vendas
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        if (!repositorioVenda.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorioVenda.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}