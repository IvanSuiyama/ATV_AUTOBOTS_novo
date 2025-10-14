package com.autobots.automanager.menus;

import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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

    private final Scanner scanner = new Scanner(System.in);

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
                System.out.print("Descrição: ");
                servico.setDescricao(scanner.nextLine());
                repositorioServico.save(servico);
                System.out.println("Serviço cadastrado com sucesso!");
                break;
            case 2:
                System.out.print("Digite a descrição do serviço: ");
                String descricao = scanner.nextLine();
                Optional<Servico> servicoEncontrado = repositorioServico.findAll().stream()
                    .filter(s -> s.getDescricao().equalsIgnoreCase(descricao))
                    .findFirst();
                if (servicoEncontrado.isPresent()) {
                    System.out.println("Serviço encontrado: " + servicoEncontrado.get().getDescricao());
                } else {
                    System.out.println("Serviço não encontrado.");
                }
                break;
            case 3:
                System.out.print("Digite a descrição do serviço: ");
                descricao = scanner.nextLine();
                servicoEncontrado = repositorioServico.findAll().stream()
                    .filter(s -> s.getDescricao().equalsIgnoreCase(descricao))
                    .findFirst();
                if (servicoEncontrado.isPresent()) {
                    Servico servicoParaEditar = servicoEncontrado.get();
                    System.out.print("Nova descrição: ");
                    servicoParaEditar.setDescricao(scanner.nextLine());
                    repositorioServico.save(servicoParaEditar);
                    System.out.println("Serviço atualizado com sucesso!");
                } else {
                    System.out.println("Serviço não encontrado.");
                }
                break;
            case 4:
                System.out.print("Digite a descrição do serviço: ");
                descricao = scanner.nextLine();
                servicoEncontrado = repositorioServico.findAll().stream()
                    .filter(s -> s.getDescricao().equalsIgnoreCase(descricao))
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
                System.out.print("Modelo: ");
                veiculo.setModelo(scanner.nextLine());
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
        System.out.print("Descrição da venda: ");
        venda.setDescricao(scanner.nextLine());
        repositorioVenda.save(venda);
        System.out.println("Venda registrada com sucesso!");
    }

    private void consultarVendas() {
        System.out.println("\n--- Consultar Vendas ---");
        List<Venda> vendas = repositorioVenda.findAll();
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
        } else {
            System.out.println("Vendas registradas:");
            vendas.forEach(venda -> System.out.println("- " + venda.getDescricao()));
        }
    }
}