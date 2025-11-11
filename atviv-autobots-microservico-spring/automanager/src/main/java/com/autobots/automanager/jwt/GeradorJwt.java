package com.autobots.automanager.jwt;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class GeradorJwt {
	private String assinatura;
	private Date expiracao;

	public GeradorJwt(String assinatura, long duracao) {
		this.assinatura = assinatura;
		this.expiracao = new Date(System.currentTimeMillis() + duracao);
	}

	public String gerarJwt(String nomeUsuario) {
		SecretKeySpec secretKeySpec = new SecretKeySpec(this.assinatura.getBytes(), SignatureAlgorithm.HS512.getJcaName());
		String jwt = Jwts.builder()
				.setSubject(nomeUsuario)
				.setExpiration(this.expiracao)
				.signWith(secretKeySpec, SignatureAlgorithm.HS512)
				.compact();
		return jwt;
	}
}