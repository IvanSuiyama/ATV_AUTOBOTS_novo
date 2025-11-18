package com.autobots.automanager.menus;

import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

@Component
public class MenuServico {

    @Autowired
    private RepositorioServico repositorioServico;

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    private final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    public void exibirMenuServicos() {
        int opcao;

        do {
            System.out.println("--- Menu de Serviços ---");
            System.out.println("1 - CRUD de serviços");
            System.out.println("2 - CRUD de automóveis");
            System.out.println("3 - Registrar venda");
            System.out.println("4 - Consultar vendas (total)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    crudServicos();
                    break;
                case 2:
                    crudAutomoveis();
                    break;
                case 3:
                    registrarVenda();
                    break;
                case 4:
                    consultarVendas();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void crudServicos() {
        System.out.println("\n--- CRUD de Serviços ---");
        System.out.println("1 - Cadastrar serviço");
        System.out.println("2 - Consultar serviço");
        System.out.println("3 - Editar serviço");
        System.out.println("4 - Deletar serviço");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                Servico servico = new Servico();
                System.out.print("Nome do serviço: ");
                servico.setNome(scanner.nextLine());
                System.out.print("Valor (R$): ");
                servico.setValor(scanner.nextDouble());
                scanner.nextLine(); // Consumir quebra de linha
                System.out.print("Descrição (opcional): ");
                servico.setDescricao(scanner.nextLine());
                repositorioServico.save(servico);
                System.out.println("Serviço cadastrado com sucesso!");
                break;
            case 2:
                System.out.print("Digite o nome do serviço: ");
                String nome = scanner.nextLine();
                Optional<Servico> servicoEncontrado = repositorioServico.findAll().stream()
                    .filter(s -> s.getNome().equalsIgnoreCase(nome))
                    .findFirst();
                if (servicoEncontrado.isPresent()) {
                    Servico s = servicoEncontrado.get();
                    System.out.println("Serviço encontrado: " + s.getNome() + " - R$ " + s.getValor() + " - " + s.getDescricao());
                } else {
                    System.out.println("Serviço não encontrado.");
                }
                break;
            case 3:
                System.out.print("Digite o nome do serviço: ");
                nome = scanner.nextLine();
                servicoEncontrado = repositorioServico.findAll().stream()
                    .filter(s -> s.getNome().equalsIgnoreCase(nome))
                    .findFirst();
                if (servicoEncontrado.isPresent()) {
                    Servico servicoParaEditar = servicoEncontrado.get();
                    System.out.print("Novo nome: ");
                    servicoParaEditar.setNome(scanner.nextLine());
                    System.out.print("Novo valor (R$): ");
                    servicoParaEditar.setValor(scanner.nextDouble());
                    scanner.nextLine(); // Consumir quebra de linha
                    System.out.print("Nova descrição: ");
                    servicoParaEditar.setDescricao(scanner.nextLine());
                    repositorioServico.save(servicoParaEditar);
                    System.out.println("Serviço atualizado com sucesso!");
                } else {
                    System.out.println("Serviço não encontrado.");
                }
                break;
            case 4:
                System.out.print("Digite o nome do serviço: ");
                nome = scanner.nextLine();
                servicoEncontrado = repositorioServico.findAll().stream()
                    .filter(s -> s.getNome().equalsIgnoreCase(nome))
                    .findFirst();
                if (servicoEncontrado.isPresent()) {
                    repositorioServico.delete(servicoEncontrado.get());
                    System.out.println("Serviço deletado com sucesso!");
                } else {
                    System.out.println("Serviço não encontrado.");
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void crudAutomoveis() {
        System.out.println("\n--- CRUD de Automóveis ---");
        System.out.println("1 - Cadastrar automóvel");
        System.out.println("2 - Consultar automóvel");
        System.out.println("3 - Editar automóvel");
        System.out.println("4 - Deletar automóvel");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                Veiculo veiculo = new Veiculo();
                System.out.print("Tipo de veículo (HATCH, SEDA, SUV, PICKUP, SW): ");
                try {
                    veiculo.setTipo(TipoVeiculo.valueOf(scanner.nextLine().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo de veículo inválido. Cadastro cancelado.");
                    return;
                }
                System.out.print("Marca: ");
                veiculo.setMarca(scanner.nextLine());
                System.out.print("Modelo: ");
                veiculo.setModelo(scanner.nextLine());
                System.out.print("Cor: ");
                veiculo.setCor(scanner.nextLine());
                System.out.print("Placa: ");
                veiculo.setPlaca(scanner.nextLine());
                repositorioVeiculo.save(veiculo);
                System.out.println("Automóvel cadastrado com sucesso!");
                break;
            case 2:
                System.out.print("Digite o modelo do automóvel: ");
                String modelo = scanner.nextLine();
                Optional<Veiculo> veiculoEncontrado = repositorioVeiculo.findAll().stream()
                    .filter(v -> v.getModelo().equalsIgnoreCase(modelo))
                    .findFirst();
                if (veiculoEncontrado.isPresent()) {
                    System.out.println("Automóvel encontrado: " + veiculoEncontrado.get().getModelo());
                } else {
                    System.out.println("Automóvel não encontrado.");
                }
                break;
            case 3:
                System.out.print("Digite o modelo do automóvel: ");
                modelo = scanner.nextLine();
                veiculoEncontrado = repositorioVeiculo.findAll().stream()
                    .filter(v -> v.getModelo().equalsIgnoreCase(modelo))
                    .findFirst();
                if (veiculoEncontrado.isPresent()) {
                    Veiculo veiculoParaEditar = veiculoEncontrado.get();
                    System.out.print("Novo modelo: ");
                    veiculoParaEditar.setModelo(scanner.nextLine());
                    repositorioVeiculo.save(veiculoParaEditar);
                    System.out.println("Automóvel atualizado com sucesso!");
                } else {
                    System.out.println("Automóvel não encontrado.");
                }
                break;
            case 4:
                System.out.print("Digite o modelo do automóvel: ");
                modelo = scanner.nextLine();
                veiculoEncontrado = repositorioVeiculo.findAll().stream()
                    .filter(v -> v.getModelo().equalsIgnoreCase(modelo))
                    .findFirst();
                if (veiculoEncontrado.isPresent()) {
                    repositorioVeiculo.delete(veiculoEncontrado.get());
                    System.out.println("Automóvel deletado com sucesso!");
                } else {
                    System.out.println("Automóvel não encontrado.");
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void registrarVenda() {
        System.out.println("\n--- Registrar Venda ---");
        Venda venda = new Venda();
        
        // Pergunta o tipo da venda
        System.out.println("Tipo de venda:");
        System.out.println("1 - Serviço");
        System.out.println("2 - Automóvel");
        System.out.print("Escolha uma opção: ");
        int tipoVenda = scanner.nextInt();
        scanner.nextLine();
        
        if (tipoVenda == 1) {
            // Venda de serviço
            System.out.println("\n--- Serviços Disponíveis ---");
            List<Servico> servicos = repositorioServico.findAll();
            if (servicos.isEmpty()) {
                System.out.println("Nenhum serviço disponível.");
                return;
            }
            
            for (int i = 0; i < servicos.size(); i++) {
                Servico s = servicos.get(i);
                System.out.println((i + 1) + " - " + s.getNome() + " (Valor: R$ " + s.getValor() + ")");
            }
            
            System.out.print("Escolha um serviço (número): ");
            int escolhaServico = scanner.nextInt();
            scanner.nextLine();
            
            if (escolhaServico > 0 && escolhaServico <= servicos.size()) {
                Servico servicoEscolhido = servicos.get(escolhaServico - 1);
                venda.getServicos().add(servicoEscolhido);
                venda.setDescricao("SERVICO-" + servicoEscolhido.getId() + "-" + System.currentTimeMillis());
            } else {
                System.out.println("Opção inválida.");
                return;
            }
            
        } else if (tipoVenda == 2) {
            // Venda de automóvel
            System.out.println("\n--- Automóveis Disponíveis ---");
            List<Veiculo> veiculos = repositorioVeiculo.findAll();
            if (veiculos.isEmpty()) {
                System.out.println("Nenhum automóvel disponível.");
                return;
            }
            
            for (int i = 0; i < veiculos.size(); i++) {
                Veiculo v = veiculos.get(i);
                System.out.println((i + 1) + " - " + v.getMarca() + " " + v.getModelo() + " " + v.getCor() + " (Placa: " + v.getPlaca() + ")");
            }
            
            System.out.print("Escolha um automóvel (número): ");
            int escolhaVeiculo = scanner.nextInt();
            scanner.nextLine();
            
            if (escolhaVeiculo > 0 && escolhaVeiculo <= veiculos.size()) {
                Veiculo veiculoEscolhido = veiculos.get(escolhaVeiculo - 1);
                venda.setVeiculo(veiculoEscolhido);
                venda.setDescricao("AUTOMOVEL-" + veiculoEscolhido.getPlaca() + "-" + System.currentTimeMillis());
            } else {
                System.out.println("Opção inválida.");
                return;
            }
        } else {
            System.out.println("Opção inválida.");
            return;
        }
        
        // Pede o valor da venda
        System.out.print("Valor da venda (R$): ");
        venda.setValor(scanner.nextDouble());
        scanner.nextLine(); // Consumir quebra de linha
        
        // Pede o email do cliente
        System.out.print("Email do cliente: ");
        String emailCliente = scanner.nextLine();
        
        // Busca o cliente pelo email
        Optional<Usuario> cliente = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(emailCliente)))
            .findFirst();
            
        if (cliente.isPresent()) {
            venda.setCliente(cliente.get());
            venda.setCadastro(new java.util.Date());
            repositorioVenda.save(venda);
            System.out.println("Venda registrada com sucesso! Valor: R$ " + venda.getValor());
        } else {
            System.out.println("Cliente não encontrado com o email: " + emailCliente);
        }
    }

    private void consultarVendas() {
        System.out.println("\n--- Consultar Vendas ---");
        List<Venda> vendas = repositorioVenda.findAll();
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
        } else {
            System.out.println("Vendas registradas:");
            vendas.forEach(venda -> {
                String cliente = venda.getCliente() != null ? venda.getCliente().getNome() : "Cliente não informado";
                System.out.println("- " + venda.getDescricao() + " | Cliente: " + cliente + " | Valor: R$ " + venda.getValor());
            });
        }
    }
}