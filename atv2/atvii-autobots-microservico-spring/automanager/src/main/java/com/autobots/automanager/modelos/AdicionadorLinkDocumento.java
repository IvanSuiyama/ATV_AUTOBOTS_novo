package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.controles.DocumentoControle;

public class AdicionadorLinkDocumento implements AdicionadorLink<Documento> {
    @Override
    public void adicionarLink(List<Documento> lista) {
        for (Documento documento : lista) {
            Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterDocumento(documento.getId()))
                .withSelfRel();
            documento.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Documento objeto) {
        Link linkProprio = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterDocumento(objeto.getId()))
            .withSelfRel();
        objeto.add(linkProprio);
    }
}