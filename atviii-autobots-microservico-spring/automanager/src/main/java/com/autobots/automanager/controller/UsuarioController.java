package com.autobots.automanager.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;

import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.repositorios.RepositorioEmail;
import com.autobots.automanager.repositorios.RepositorioTelefone;
import com.autobots.automanager.repositorios.RepositorioEndereco;
import com.autobots.automanager.repositorios.RepositorioCredencial;
import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.repositorios.RepositorioDocumento;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioEmail repositorioEmail;
    @Autowired
    private RepositorioTelefone repositorioTelefone;
    @Autowired
    private RepositorioEndereco repositorioEndereco;
    @Autowired
    private RepositorioCredencial repositorioCredencial;
    @Autowired
    private RepositorioDocumento repositorioDocumento;
    @Autowired
    private RepositorioVeiculo repositorioVeiculo;
    @Autowired
    private RepositorioMercadoria repositorioMercadoria;
    @Autowired
    private RepositorioServico repositorioServico;
    @Autowired
    private RepositorioVenda repositorioVenda;
    @PostMapping("/{id}/mercadorias/{mercadoriaId}")
    public ResponseEntity<EntityModel<Usuario>> associarMercadoria(@PathVariable Long id, @PathVariable Long mercadoriaId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        Optional<Mercadoria> mercadoriaOpt = repositorioMercadoria.findById(mercadoriaId);
        if (usuarioOpt.isPresent() && mercadoriaOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getMercadorias().add(mercadoriaOpt.get());
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/mercadorias/{mercadoriaId}")
    public ResponseEntity<EntityModel<Usuario>> desassociarMercadoria(@PathVariable Long id, @PathVariable Long mercadoriaId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getMercadorias().removeIf(m -> m.getId().equals(mercadoriaId));
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/servicos/{servicoId}")
    public ResponseEntity<EntityModel<Usuario>> associarServico(@PathVariable Long id, @PathVariable Long servicoId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        Optional<Servico> servicoOpt = repositorioServico.findById(servicoId);
        if (usuarioOpt.isPresent() && servicoOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/vendas/{vendaId}")
    public ResponseEntity<EntityModel<Usuario>> associarVenda(@PathVariable Long id, @PathVariable Long vendaId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        Optional<Venda> vendaOpt = repositorioVenda.findById(vendaId);
        if (usuarioOpt.isPresent() && vendaOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getVendas().add(vendaOpt.get());
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/vendas/{vendaId}")
    public ResponseEntity<EntityModel<Usuario>> desassociarVenda(@PathVariable Long id, @PathVariable Long vendaId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getVendas().removeIf(v -> v.getId().equals(vendaId));
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/{id}/veiculos/{veiculoId}")
    public ResponseEntity<EntityModel<Usuario>> associarVeiculo(@PathVariable Long id, @PathVariable Long veiculoId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        Optional<Veiculo> veiculoOpt = repositorioVeiculo.findById(veiculoId);
        if (usuarioOpt.isPresent() && veiculoOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getVeiculos().add(veiculoOpt.get());
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/veiculos/{veiculoId}")
    public ResponseEntity<EntityModel<Usuario>> desassociarVeiculo(@PathVariable Long id, @PathVariable Long veiculoId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getVeiculos().removeIf(v -> v.getId().equals(veiculoId));
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/{id}/documentos/{documentoId}")
    public ResponseEntity<EntityModel<Usuario>> associarDocumento(@PathVariable Long id, @PathVariable Long documentoId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        Optional<Documento> documentoOpt = repositorioDocumento.findById(documentoId);
        if (usuarioOpt.isPresent() && documentoOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getDocumentos().add(documentoOpt.get());
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/documentos/{documentoId}")
    public ResponseEntity<EntityModel<Usuario>> desassociarDocumento(@PathVariable Long id, @PathVariable Long documentoId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getDocumentos().removeIf(d -> d.getId().equals(documentoId));
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/{id}/emails/{emailId}")
    public ResponseEntity<EntityModel<Usuario>> associarEmail(@PathVariable Long id, @PathVariable Long emailId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        Optional<Email> emailOpt = repositorioEmail.findById(emailId);
        if (usuarioOpt.isPresent() && emailOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getEmails().add(emailOpt.get());
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/emails/{emailId}")
    public ResponseEntity<EntityModel<Usuario>> desassociarEmail(@PathVariable Long id, @PathVariable Long emailId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getEmails().removeIf(e -> e.getId().equals(emailId));
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/telefones/{telefoneId}")
    public ResponseEntity<EntityModel<Usuario>> associarTelefone(@PathVariable Long id, @PathVariable Long telefoneId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        Optional<Telefone> telefoneOpt = repositorioTelefone.findById(telefoneId);
        if (usuarioOpt.isPresent() && telefoneOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getTelefones().add(telefoneOpt.get());
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/telefones/{telefoneId}")
    public ResponseEntity<EntityModel<Usuario>> desassociarTelefone(@PathVariable Long id, @PathVariable Long telefoneId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getTelefones().removeIf(t -> t.getId().equals(telefoneId));
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/endereco/{enderecoId}")
    public ResponseEntity<EntityModel<Usuario>> associarEndereco(@PathVariable Long id, @PathVariable Long enderecoId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        Optional<Endereco> enderecoOpt = repositorioEndereco.findById(enderecoId);
        if (usuarioOpt.isPresent() && enderecoOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setEndereco(enderecoOpt.get());
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/endereco")
    public ResponseEntity<EntityModel<Usuario>> removerEndereco(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setEndereco(null);
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/credenciais/{credencialId}")
    public ResponseEntity<EntityModel<Usuario>> associarCredencial(@PathVariable Long id, @PathVariable Long credencialId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        Optional<Credencial> credencialOpt = repositorioCredencial.findById(credencialId);
        if (usuarioOpt.isPresent() && credencialOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getCredenciais().add(credencialOpt.get());
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/credenciais/{credencialId}")
    public ResponseEntity<EntityModel<Usuario>> desassociarCredencial(@PathVariable Long id, @PathVariable Long credencialId) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getCredenciais().removeIf(c -> c.getId().equals(credencialId));
            repositorioUsuario.save(usuario);
            EntityModel<Usuario> resource = EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public CollectionModel<EntityModel<Usuario>> listarUsuarios() {
        List<EntityModel<Usuario>> usuarios = repositorioUsuario.findAll().stream()
            .map(usuario -> EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(usuario.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(usuarios,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> buscarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = repositorioUsuario.findById(id);
        if (usuario.isPresent()) {
            EntityModel<Usuario> resource = EntityModel.of(usuario.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> criarUsuario(@RequestBody Usuario usuario) {
        Usuario salvo = repositorioUsuario.save(usuario);
        EntityModel<Usuario> resource = EntityModel.of(salvo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(salvo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (!repositorioUsuario.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuario.setId(id);
        Usuario atualizado = repositorioUsuario.save(usuario);
        EntityModel<Usuario> resource = EntityModel.of(atualizado,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarUsuario(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
        );
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (!repositorioUsuario.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        // Verificar se o usuário tem vendas associadas como cliente ou funcionário
        boolean temVendasComoCliente = repositorioVenda.findAll().stream()
            .anyMatch(venda -> venda.getCliente() != null && venda.getCliente().getId().equals(id));
        boolean temVendasComoFuncionario = repositorioVenda.findAll().stream()
            .anyMatch(venda -> venda.getFuncionario() != null && venda.getFuncionario().getId().equals(id));
            
        if (temVendasComoCliente || temVendasComoFuncionario) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }
        
        repositorioUsuario.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
