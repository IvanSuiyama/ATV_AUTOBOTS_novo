package com.autobots.automanager.jwt;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class AnalisadorJwt {
	private String assinatura;
	private String jwt;

	public AnalisadorJwt(String assinatura, String jwt) {
		this.assinatura = assinatura;
		this.jwt = jwt;
	}

	public Claims obterReivindicacoes() {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(assinatura.getBytes(), SignatureAlgorithm.HS512.getJcaName());
			return Jwts.parserBuilder()
					.setSigningKey(secretKeySpec)
					.build()
					.parseClaimsJws(jwt)
					.getBody();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String obterNomeUsuairo(Claims reivindicacoes) {
		if (reivindicacoes != null) {
			String nomeUsuario = reivindicacoes.getSubject();
			return nomeUsuario;
		}
		return null;
	}
}