package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private EnderecoRepositorio repositorio;

    @GetMapping("/endereco/{id}")
    public Endereco obterEndereco(@PathVariable long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/enderecos")
    public List<Endereco> obterEnderecos() {
        return repositorio.findAll();
    }

    @PostMapping("/cadastro")
    public void cadastrarEndereco(@RequestBody Endereco endereco) {
        repositorio.save(endereco);
    }

    @PutMapping("/atualizar")
    public void atualizarEndereco(@RequestBody Endereco atualizacao) {
        Endereco endereco = repositorio.getById(atualizacao.getId());
        EnderecoAtualizador atualizador = new EnderecoAtualizador();
        atualizador.atualizar(endereco, atualizacao);
        repositorio.save(endereco);
    }

    @DeleteMapping("/excluir")
    public void excluirEndereco(@RequestBody Endereco exclusao) {
        Endereco endereco = repositorio.getById(exclusao.getId());
        repositorio.delete(endereco);
    }
}
