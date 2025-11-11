package com.autobots.automanager.menus;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class MenuFuncionario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenuFuncionarios() {
        int opcao;

        do {
            System.out.println("--- Menu de Funcionários ---");
            System.out.println("1 - Cadastrar funcionário");
            System.out.println("2 - Consultar funcionário");
            System.out.println("3 - Editar funcionário");
            System.out.println("4 - Deletar funcionário");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    consultarFuncionario();
                    break;
                case 3:
                    editarFuncionario();
                    break;
                case 4:
                    deletarFuncionario();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void cadastrarFuncionario() {
        System.out.println("\n--- Cadastro de Funcionário ---");
        Usuario funcionario = new Usuario();
        System.out.print("Nome: ");
        funcionario.setNome(scanner.nextLine());
        System.out.print("Nome social (opcional): ");
        funcionario.setNomeSocial(scanner.nextLine());
        repositorioUsuario.save(funcionario);
        System.out.println("Funcionário cadastrado com sucesso!\n");
    }

    private void consultarFuncionario() {
        System.out.print("Digite o nome do funcionário: ");
        String nome = scanner.nextLine();
        Optional<Usuario> funcionario = repositorioUsuario.findAll().stream()
            .filter(u -> u.getNome().equalsIgnoreCase(nome))
            .findFirst();

        if (funcionario.isPresent()) {
            System.out.println("Funcionário encontrado: " + funcionario.get().getNome());
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }

    private void editarFuncionario() {
        System.out.print("Digite o nome do funcionário: ");
        String nome = scanner.nextLine();
        Optional<Usuario> funcionario = repositorioUsuario.findAll().stream()
            .filter(u -> u.getNome().equalsIgnoreCase(nome))
            .findFirst();

        if (funcionario.isPresent()) {
            Usuario usuario = funcionario.get();
            System.out.print("Digite o novo nome: ");
            usuario.setNome(scanner.nextLine());
            repositorioUsuario.save(usuario);
            System.out.println("Funcionário atualizado com sucesso!");
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }

    private void deletarFuncionario() {
        System.out.print("Digite o nome do funcionário: ");
        String nome = scanner.nextLine();
        Optional<Usuario> funcionario = repositorioUsuario.findAll().stream()
            .filter(u -> u.getNome().equalsIgnoreCase(nome))
            .findFirst();

        if (funcionario.isPresent()) {
            repositorioUsuario.delete(funcionario.get());
            System.out.println("Funcionário deletado com sucesso!");
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }
}
