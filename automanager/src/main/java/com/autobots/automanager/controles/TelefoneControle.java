package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    @Autowired
    private TelefoneRepositorio repositorio;

    @GetMapping("/telefone/{id}")
    public Telefone obterTelefone(@PathVariable long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/telefones")
    public List<Telefone> obterTelefones() {
        return repositorio.findAll();
    }

    @PostMapping("/cadastro")
    public void cadastrarTelefone(@RequestBody Telefone telefone) {
        repositorio.save(telefone);
    }

    @PutMapping("/atualizar")
    public void atualizarTelefone(@RequestBody Telefone atualizacao) {
        Telefone telefone = repositorio.getById(atualizacao.getId());
        TelefoneAtualizador atualizador = new TelefoneAtualizador();
        atualizador.atualizar(telefone, atualizacao);
        repositorio.save(telefone);
    }

    @DeleteMapping("/excluir")
    public void excluirTelefone(@RequestBody Telefone exclusao) {
        Telefone telefone = repositorio.getById(exclusao.getId());
        repositorio.delete(telefone);
    }
}
