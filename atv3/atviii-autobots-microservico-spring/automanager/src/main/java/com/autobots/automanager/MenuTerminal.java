package com.autobots.automanager;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioVenda;

@Component
public class MenuTerminal implements CommandLineRunner {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioVeiculo repositorioVeiculo;
    @Autowired
    private RepositorioServico repositorioServico;
    @Autowired
    private RepositorioMercadoria repositorioMercadoria;
    @Autowired
    private RepositorioVenda repositorioVenda;

    private Scanner scanner = new Scanner(System.in);

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
        // Cadastro de email
        System.out.print("Email: ");
        String email = scanner.nextLine();
        com.autobots.automanager.entitades.Email emailObj = new com.autobots.automanager.entitades.Email();
        emailObj.setEndereco(email);
        usuario.getEmails().add(emailObj);
        // Cadastro de documento CPF
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        com.autobots.automanager.entitades.Documento doc = new com.autobots.automanager.entitades.Documento();
        doc.setTipo(com.autobots.automanager.enumeracoes.TipoDocumento.CPF);
        doc.setNumero(cpf);
        doc.setDataEmissao(new java.util.Date());
        usuario.getDocumentos().add(doc);
        // Cadastro de senha
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        com.autobots.automanager.entitades.CredencialUsuarioSenha cred = new com.autobots.automanager.entitades.CredencialUsuarioSenha();
        cred.setNomeUsuario(email); // login por email
        cred.setSenha(senha);
        cred.setCriacao(new java.util.Date());
        cred.setInativo(false);
        usuario.getCredenciais().add(cred);
        repositorioUsuario.save(usuario);
        System.out.println("Funcionário cadastrado com sucesso!\n");
    }


    private void fazerLoginFuncionario() {
        System.out.println("\n--- Login Funcionário ---");
        System.out.println("Escolha o método de login:");
        System.out.println("1 - Email e senha");
        System.out.println("2 - CPF e senha");
        System.out.print("Opção: ");
        String metodo = scanner.nextLine();
        Usuario usuario = null;
        switch (metodo) {
            case "1": {
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Senha: ");
                String senhaEmail = scanner.nextLine();
                usuario = repositorioUsuario.findAll().stream()
                    .filter(u -> u.getPerfis().contains(PerfilUsuario.FUNCIONARIO))
                    .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equalsIgnoreCase(email)))
                    .filter(u -> u.getCredenciais().stream().anyMatch(c -> c instanceof com.autobots.automanager.entitades.CredencialUsuarioSenha && ((com.autobots.automanager.entitades.CredencialUsuarioSenha) c).getSenha().equals(senhaEmail)))
                    .findFirst().orElse(null);
                break;
            }
            case "2": {
                System.out.print("CPF: ");
                String cpf = scanner.nextLine();
                System.out.print("Senha: ");
                String senhaCpf = scanner.nextLine();
                usuario = repositorioUsuario.findAll().stream()
                    .filter(u -> u.getPerfis().contains(PerfilUsuario.FUNCIONARIO))
                    .filter(u -> u.getDocumentos().stream().anyMatch(d -> d.getTipo() == com.autobots.automanager.enumeracoes.TipoDocumento.CPF && d.getNumero().equals(cpf)))
                    .filter(u -> u.getCredenciais().stream().anyMatch(c -> c instanceof com.autobots.automanager.entitades.CredencialUsuarioSenha && ((com.autobots.automanager.entitades.CredencialUsuarioSenha) c).getSenha().equals(senhaCpf)))
                    .findFirst().orElse(null);
                break;
            }
            default:
                System.out.println("Opção inválida!");
                return;
        }
        if (usuario == null) {
            System.out.println("Funcionário não encontrado ou senha incorreta!");
            return;
        }
        System.out.println("Login realizado como Funcionário: " + usuario.getNome());
        menuUnicoFuncionario(usuario);
    }


    private void menuUnicoFuncionario(Usuario usuario) {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n--- Menu Administrativo Funcionário ---");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Cadastrar fornecedor");
            System.out.println("3 - Cadastrar funcionário");
            System.out.println("4 - Cadastrar veículo da loja");
            System.out.println("5 - Cadastrar serviço");
            System.out.println("6 - Cadastrar mercadoria");
            System.out.println("7 - Cadastrar venda");
            System.out.println("8 - Consultar usuários");
            System.out.println("9 - Consultar fornecedores");
            System.out.println("10 - Consultar veículos");
            System.out.println("11 - Consultar serviços");
            System.out.println("12 - Consultar mercadorias");
            System.out.println("13 - Consultar vendas");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":
                    cadastrarUsuarioPerfil(com.autobots.automanager.enumeracoes.PerfilUsuario.CLIENTE);
                    break;
                case "2":
                    cadastrarUsuarioPerfil(com.autobots.automanager.enumeracoes.PerfilUsuario.FORNECEDOR);
                    break;
                case "3":
                    cadastrarUsuarioPerfil(com.autobots.automanager.enumeracoes.PerfilUsuario.FUNCIONARIO);
                    break;
                case "4":
                    cadastrarVeiculo();
                    break;
                case "5":
                    cadastrarServico();
                    break;
                case "6":
                    cadastrarMercadoria();
                    break;
                case "7":
                    cadastrarVenda();
                    break;
                case "8":
                    consultarUsuarios();
                    break;
                case "9":
                    consultarFornecedores();
                    break;
                case "10":
                    consultarVeiculos();
                    break;
                case "11":
                    consultarServicos();
                    break;
                case "12":
                    consultarMercadorias();
                    break;
                case "13":
                    consultarVendas();
                    break;
                case "0":
                    sair = true;
                    System.out.println("Saindo do menu administrativo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarUsuarioPerfil(com.autobots.automanager.enumeracoes.PerfilUsuario perfil) {
        System.out.println("\n--- Cadastro de " + perfil.name().charAt(0) + perfil.name().substring(1).toLowerCase() + " (completo) ---");
        Usuario usuario = new Usuario();
        System.out.print("Nome: ");
        usuario.setNome(scanner.nextLine());
        System.out.print("Nome social (opcional): ");
        usuario.setNomeSocial(scanner.nextLine());
        usuario.getPerfis().add(perfil);
        // Documentos
        System.out.print("Quantos documentos deseja cadastrar? ");
        int qtdDocs = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < qtdDocs; i++) {
            com.autobots.automanager.entitades.Documento doc = new com.autobots.automanager.entitades.Documento();
            System.out.print("Tipo de documento (CPF, RG, CNH, etc): ");
            String tipo = scanner.nextLine();
            try {
                doc.setTipo(com.autobots.automanager.enumeracoes.TipoDocumento.valueOf(tipo.toUpperCase()));
            } catch (Exception e) {
                System.out.println("Tipo inválido. Pulando documento.");
                continue;
            }
            System.out.print("Número: ");
            doc.setNumero(scanner.nextLine());
            doc.setDataEmissao(new java.util.Date());
            usuario.getDocumentos().add(doc);
        }
        // Emails
        System.out.print("Quantos emails deseja cadastrar? ");
        int qtdEmails = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < qtdEmails; i++) {
            com.autobots.automanager.entitades.Email email = new com.autobots.automanager.entitades.Email();
            System.out.print("Email: ");
            email.setEndereco(scanner.nextLine());
            usuario.getEmails().add(email);
        }
        // Telefones
        System.out.print("Quantos telefones deseja cadastrar? ");
        int qtdTels = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < qtdTels; i++) {
            com.autobots.automanager.entitades.Telefone tel = new com.autobots.automanager.entitades.Telefone();
            System.out.print("DDD: ");
            tel.setDdd(scanner.nextLine());
            System.out.print("Número: ");
            tel.setNumero(scanner.nextLine());
            usuario.getTelefones().add(tel);
        }
        // Endereço
        System.out.print("Deseja cadastrar endereço? (s/n): ");
        String respEnd = scanner.nextLine();
        if (respEnd.equalsIgnoreCase("s")) {
            com.autobots.automanager.entitades.Endereco end = new com.autobots.automanager.entitades.Endereco();
            System.out.print("Estado: ");
            end.setEstado(scanner.nextLine());
            System.out.print("Cidade: ");
            end.setCidade(scanner.nextLine());
            System.out.print("Bairro: ");
            end.setBairro(scanner.nextLine());
            System.out.print("Rua: ");
            end.setRua(scanner.nextLine());
            System.out.print("Número: ");
            end.setNumero(scanner.nextLine());
            System.out.print("CEP: ");
            end.setCodigoPostal(scanner.nextLine());
            System.out.print("Informações adicionais: ");
            end.setInformacoesAdicionais(scanner.nextLine());
            usuario.setEndereco(end);
        }
        repositorioUsuario.save(usuario);
        System.out.println(perfil.name().charAt(0) + perfil.name().substring(1).toLowerCase() + " cadastrado com sucesso!\n");
    }

    private void cadastrarVeiculo() {
        System.out.println("\n--- Cadastro de Veículo ---");
        com.autobots.automanager.entitades.Veiculo veiculo = new com.autobots.automanager.entitades.Veiculo();
        System.out.print("Modelo: ");
        veiculo.setModelo(scanner.nextLine());
        System.out.print("Placa: ");
        veiculo.setPlaca(scanner.nextLine());
        // TipoVeiculo pode ser solicitado ao usuário
        System.out.print("Tipo (ex: CARRO, MOTO): ");
        String tipo = scanner.nextLine();
        try {
            veiculo.setTipo(com.autobots.automanager.enumeracoes.TipoVeiculo.valueOf(tipo.toUpperCase()));
        } catch (Exception e) {
            System.out.println("Tipo inválido. Cadastro cancelado.");
            return;
        }
        repositorioVeiculo.save(veiculo);
        System.out.println("Veículo cadastrado com sucesso!\n");
    }

    private void cadastrarServico() {
        System.out.println("\n--- Cadastro de Serviço ---");
        com.autobots.automanager.entitades.Servico servico = new com.autobots.automanager.entitades.Servico();
        System.out.print("Nome: ");
        servico.setNome(scanner.nextLine());
        System.out.print("Valor: ");
        servico.setValor(Double.parseDouble(scanner.nextLine()));
        System.out.print("Descrição: ");
        servico.setDescricao(scanner.nextLine());
        repositorioServico.save(servico);
        System.out.println("Serviço cadastrado com sucesso!\n");
    }

    private void cadastrarMercadoria() {
        System.out.println("\n--- Cadastro de Mercadoria ---");
        com.autobots.automanager.entitades.Mercadoria mercadoria = new com.autobots.automanager.entitades.Mercadoria();
        System.out.print("Nome: ");
        mercadoria.setNome(scanner.nextLine());
        System.out.print("Quantidade: ");
        mercadoria.setQuantidade(Long.parseLong(scanner.nextLine()));
        System.out.print("Valor: ");
        mercadoria.setValor(Double.parseDouble(scanner.nextLine()));
        System.out.print("Descrição: ");
        mercadoria.setDescricao(scanner.nextLine());
        mercadoria.setCadastro(new java.util.Date());
        mercadoria.setFabricao(new java.util.Date());
        mercadoria.setValidade(new java.util.Date());
        repositorioMercadoria.save(mercadoria);
        System.out.println("Mercadoria cadastrada com sucesso!\n");
    }

    private void cadastrarVenda() {
        System.out.println("\n--- Cadastro de Venda ---");
        com.autobots.automanager.entitades.Venda venda = new com.autobots.automanager.entitades.Venda();
        System.out.print("Identificação: ");
        venda.setIdentificacao(scanner.nextLine());
        venda.setCadastro(new java.util.Date());
        repositorioVenda.save(venda);
        System.out.println("Venda cadastrada com sucesso!\n");
    }

    private void consultarUsuarios() {
        System.out.println("\n--- Lista de Usuários ---");
        for (Usuario u : repositorioUsuario.findAll()) {
            System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome() + " | Perfis: " + u.getPerfis());
        }
    }

    private void consultarFornecedores() {
        System.out.println("\n--- Lista de Fornecedores ---");
        for (Usuario u : repositorioUsuario.findAll()) {
            if (u.getPerfis().contains(com.autobots.automanager.enumeracoes.PerfilUsuario.FORNECEDOR)) {
                System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome());
            }
        }
    }

    private void consultarVeiculos() {
        System.out.println("\n--- Lista de Veículos ---");
        for (com.autobots.automanager.entitades.Veiculo v : repositorioVeiculo.findAll()) {
            System.out.println("ID: " + v.getId() + " | Modelo: " + v.getModelo() + " | Placa: " + v.getPlaca() + " | Tipo: " + v.getTipo());
        }
    }

    private void consultarServicos() {
        System.out.println("\n--- Lista de Serviços ---");
        for (com.autobots.automanager.entitades.Servico s : repositorioServico.findAll()) {
            System.out.println("ID: " + s.getId() + " | Nome: " + s.getNome() + " | Valor: " + s.getValor());
        }
    }

    private void consultarMercadorias() {
        System.out.println("\n--- Lista de Mercadorias ---");
        for (com.autobots.automanager.entitades.Mercadoria m : repositorioMercadoria.findAll()) {
            System.out.println("ID: " + m.getId() + " | Nome: " + m.getNome() + " | Quantidade: " + m.getQuantidade() + " | Valor: " + m.getValor());
        }
    }

    private void consultarVendas() {
        System.out.println("\n--- Lista de Vendas ---");
        for (com.autobots.automanager.entitades.Venda v : repositorioVenda.findAll()) {
            System.out.println("ID: " + v.getId() + " | Identificação: " + v.getIdentificacao() + " | Cadastro: " + v.getCadastro());
        }
    }
}
