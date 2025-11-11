package com.autobots.automanager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.menus.MenuTerminal;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {

	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	
	@Autowired
	private MenuTerminal menuTerminal;

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa Exemplo Ltda");
		empresa.setNomeFantasia("Exemplo Serviços");
		empresa.setCadastro(new Date());

		Usuario funcionario = new Usuario();
		funcionario.setNome("Ivan");
		funcionario.setNomeSocial("Ivan");
		funcionario.getPerfis().add(PerfilUsuario.FUNCIONARIO);

		Email emailFuncionario = new Email();
		emailFuncionario.setEndereco("ivan@email.com");
		funcionario.getEmails().add(emailFuncionario);

		Documento cpf = new Documento();
		cpf.setDataEmissao(new Date());
		cpf.setNumero("50145499854");
		cpf.setTipo(TipoDocumento.CPF);
		funcionario.getDocumentos().add(cpf);

		CredencialUsuarioSenha credencialFuncionario = new CredencialUsuarioSenha();
		credencialFuncionario.setNomeUsuario("ivan");
		credencialFuncionario.setSenha("1234");
		credencialFuncionario.setCriacao(new Date()); // Define a data de criação
		funcionario.getCredenciais().add(credencialFuncionario);

		empresa.getUsuarios().add(funcionario);

		repositorioEmpresa.save(empresa);
		
		// Chamar o menu após a criação do usuário padrão
		menuTerminal.run(args);
	}
}