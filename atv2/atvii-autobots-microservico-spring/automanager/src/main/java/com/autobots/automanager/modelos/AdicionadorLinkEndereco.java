package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.controles.EnderecoControle;

public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco> {
    @Override
    public void adicionarLink(List<Endereco> lista) {
        for (Endereco endereco : lista) {
            Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterEndereco(endereco.getId()))
                .withSelfRel();
            endereco.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Endereco objeto) {
        Link linkProprio = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterEndereco(objeto.getId()))
            .withSelfRel();
        objeto.add(linkProprio);
    }
}