package com.autobots.automanager.menus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;

import java.util.Scanner;

@Component
public class MenuTerminal implements CommandLineRunner {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private MenuCliente menuCliente;
    @Autowired
    private MenuFornecedor menuFornecedor;
    @Autowired
    private MenuFuncionario menuFuncionario;
    @Autowired
    private MenuServico menuServico;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        System.out.println("\n=== Bem-vindo ao Automanager ===");
        boolean sair = false;
        boolean primeiroFuncionario = repositorioUsuario.findAll().stream().noneMatch(u -> u.getPerfis().contains(com.autobots.automanager.enumeracoes.PerfilUsuario.FUNCIONARIO));
        while (!sair) {
            System.out.println("\nMenu:");
            if (primeiroFuncionario) {
                System.out.println("1 - Criar conta de Funcionário (primeiro acesso)");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                String opcao = scanner.nextLine();
                switch (opcao) {
                    case "1":
                        criarContaFuncionario();
                        // Após criar o primeiro funcionário, forçar login
                        primeiroFuncionario = false;
                        System.out.println("\nPrimeiro funcionário cadastrado. Faça login para continuar.");
                        fazerLoginFuncionario();
                        sair = true;
                        break;
                    case "0":
                        sair = true;
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } else {
                // Após o primeiro cadastro, só permite login
                fazerLoginFuncionario();
                sair = true;
            }
        }
    }


    private void criarContaFuncionario() {
        System.out.println("\n--- Cadastro de Funcionário ---");
        Usuario usuario = new Usuario();
        System.out.print("Nome: ");
        usuario.setNome(scanner.nextLine());
        System.out.print("Nome social (opcional): ");
        usuario.setNomeSocial(scanner.nextLine());
        usuario.getPerfis().add(PerfilUsuario.FUNCIONARIO);

        // Cadastro de nome de usuário e senha
        System.out.print("Nome de usuário: ");
        String nomeUsuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        com.autobots.automanager.entitades.CredencialUsuarioSenha cred = new com.autobots.automanager.entitades.CredencialUsuarioSenha();
        cred.setNomeUsuario(nomeUsuario);
        cred.setSenha(senha);
        cred.setCriacao(new java.util.Date());
        cred.setInativo(false);
        usuario.getCredenciais().add(cred);

        repositorioUsuario.save(usuario);
        System.out.println("Funcionário cadastrado com sucesso!\n");
    }


    private void fazerLoginFuncionario() {
        System.out.println("\n--- Login Funcionário ---");
        Usuario usuario = null;
        while (usuario == null) {
            System.out.print("Nome de usuário: ");
            String nomeUsuario = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            usuario = repositorioUsuario.findAll().stream()
                .filter(u -> u.getPerfis().contains(PerfilUsuario.FUNCIONARIO))
                .filter(u -> u.getCredenciais().stream().anyMatch(c -> c instanceof com.autobots.automanager.entitades.CredencialUsuarioSenha && ((com.autobots.automanager.entitades.CredencialUsuarioSenha) c).getNomeUsuario().equals(nomeUsuario) && ((com.autobots.automanager.entitades.CredencialUsuarioSenha) c).getSenha().equals(senha)))
                .findFirst().orElse(null);
            if (usuario == null) {
                System.out.println("Funcionário não encontrado ou senha incorreta!");
            }
        }
        System.out.println("Login realizado como Funcionário: " + usuario.getNome());
        menuUnicoFuncionario(usuario);
    }


    private void menuUnicoFuncionario(Usuario usuario) {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1 - Menu de Clientes");
            System.out.println("2 - Menu de Funcionários");
            System.out.println("3 - Menu de Fornecedores");
            System.out.println("4 - Menu de Serviços");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    exibirMenuClientes();
                    break;
                case "2":
                    exibirMenuFuncionarios();
                    break;
                case "3":
                    exibirMenuFornecedores();
                    break;
                case "4":
                    exibirMenuServicos();
                    break;
                case "0":
                    sair = true;
                    System.out.println("Saindo do menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public void exibirMenuPrincipal() {
        int opcao;

        do {
            System.out.println("--- Menu Principal ---");
            System.out.println("1 - Entrar no menu de funcionários");
            System.out.println("2 - Entrar no menu de clientes");
            System.out.println("3 - Entrar no menu de fornecedores");
            System.out.println("4 - Entrar no menu de serviços");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    exibirMenuFuncionarios();
                    break;
                case 2:
                    exibirMenuClientes();
                    break;
                case 3:
                    exibirMenuFornecedores();
                    break;
                case 4:
                    exibirMenuServicos();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void exibirMenuFuncionarios() {
        menuFuncionario.exibirMenuFuncionarios();
    }

    private void exibirMenuClientes() {
        menuCliente.exibirMenuClientes();
    }

    private void exibirMenuFornecedores() {
        menuFornecedor.exibirMenuFornecedores();
    }

    private void exibirMenuServicos() {
        menuServico.exibirMenuServicos();
    }
}
