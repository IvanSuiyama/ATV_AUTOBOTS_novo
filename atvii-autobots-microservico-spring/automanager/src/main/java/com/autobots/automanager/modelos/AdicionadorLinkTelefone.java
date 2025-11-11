package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.controles.TelefoneControle;

public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone> {
    @Override
    public void adicionarLink(List<Telefone> lista) {
        for (Telefone telefone : lista) {
            Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControle.class).obterTelefone(telefone.getId()))
                .withSelfRel();
            telefone.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Telefone objeto) {
        Link linkProprio = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControle.class).obterTelefone(objeto.getId()))
            .withSelfRel();
        objeto.add(linkProprio);
    }
}