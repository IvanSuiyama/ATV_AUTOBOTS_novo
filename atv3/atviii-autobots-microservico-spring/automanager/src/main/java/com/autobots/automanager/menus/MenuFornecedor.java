package com.autobots.automanager.menus;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class MenuFornecedor {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenuFornecedores() {
        int opcao;

        do {
            System.out.println("--- Menu de Fornecedores ---");
            System.out.println("1 - Cadastrar fornecedor");
            System.out.println("2 - Consultar fornecedor");
            System.out.println("3 - Editar fornecedor");
            System.out.println("4 - Deletar fornecedor");
            System.out.println("5 - Cadastrar mercadoria para fornecedor");
            System.out.println("6 - Consultar mercadorias do fornecedor");
            System.out.println("7 - Editar mercadoria do fornecedor");
            System.out.println("8 - Apagar mercadoria do fornecedor");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarFornecedor();
                    break;
                case 2:
                    consultarFornecedor();
                    break;
                case 3:
                    editarFornecedor();
                    break;
                case 4:
                    deletarFornecedor();
                    break;
                case 5:
                    cadastrarMercadoriaFornecedor();
                    break;
                case 6:
                    consultarMercadoriasFornecedor();
                    break;
                case 7:
                    editarMercadoriaFornecedor();
                    break;
                case 8:
                    apagarMercadoriaFornecedor();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void cadastrarFornecedor() {
        System.out.println("\n--- Cadastro de Fornecedor ---");
        Usuario fornecedor = new Usuario();
        System.out.print("Nome: ");
        fornecedor.setNome(scanner.nextLine());
        System.out.print("Nome social (opcional): ");
        fornecedor.setNomeSocial(scanner.nextLine());
        repositorioUsuario.save(fornecedor);
        System.out.println("Fornecedor cadastrado com sucesso!\n");
    }

    private void consultarFornecedor() {
        System.out.print("Digite o email do fornecedor: ");
        String email = scanner.nextLine();
        Optional<Usuario> fornecedor = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (fornecedor.isPresent()) {
            System.out.println("Fornecedor encontrado: " + fornecedor.get().getNome());
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
    }

    private void editarFornecedor() {
        System.out.print("Digite o email do fornecedor: ");
        String email = scanner.nextLine();
        Optional<Usuario> fornecedor = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (fornecedor.isPresent()) {
            Usuario usuario = fornecedor.get();
            System.out.print("Digite o novo nome: ");
            usuario.setNome(scanner.nextLine());
            repositorioUsuario.save(usuario);
            System.out.println("Fornecedor atualizado com sucesso!");
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
    }

    private void deletarFornecedor() {
        System.out.print("Digite o email do fornecedor: ");
        String email = scanner.nextLine();
        Optional<Usuario> fornecedor = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (fornecedor.isPresent()) {
            repositorioUsuario.delete(fornecedor.get());
            System.out.println("Fornecedor deletado com sucesso!");
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
    }

    private void cadastrarMercadoriaFornecedor() {
        System.out.print("Digite o email do fornecedor: ");
        String email = scanner.nextLine();
        Optional<Usuario> fornecedor = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (fornecedor.isPresent()) {
            Usuario usuario = fornecedor.get();
            Mercadoria mercadoria = new Mercadoria();
            System.out.print("Nome da mercadoria: ");
            mercadoria.setNome(scanner.nextLine());
            System.out.print("Quantidade: ");
            mercadoria.setQuantidade(scanner.nextLong());
            System.out.print("Valor: ");
            mercadoria.setValor(scanner.nextDouble());
            scanner.nextLine(); // Consumir a quebra de linha
            usuario.getMercadorias().add(mercadoria);
            repositorioUsuario.save(usuario);
            System.out.println("Mercadoria cadastrada com sucesso!");
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
    }

    private void consultarMercadoriasFornecedor() {
        System.out.print("Digite o email do fornecedor: ");
        String email = scanner.nextLine();
        Optional<Usuario> fornecedor = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (fornecedor.isPresent()) {
            Usuario usuario = fornecedor.get();
            System.out.println("Mercadorias do fornecedor: ");
            usuario.getMercadorias().forEach(m -> System.out.println("Nome: " + m.getNome() + ", Quantidade: " + m.getQuantidade() + ", Valor: " + m.getValor()));
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
    }

    private void editarMercadoriaFornecedor() {
        System.out.print("Digite o email do fornecedor: ");
        String email = scanner.nextLine();
        Optional<Usuario> fornecedor = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (fornecedor.isPresent()) {
            Usuario usuario = fornecedor.get();
            System.out.print("Digite o nome da mercadoria a ser editada: ");
            String nomeMercadoria = scanner.nextLine();
            Optional<Mercadoria> mercadoria = usuario.getMercadorias().stream()
                .filter(m -> m.getNome().equals(nomeMercadoria))
                .findFirst();

            if (mercadoria.isPresent()) {
                Mercadoria m = mercadoria.get();
                System.out.print("Digite o novo nome: ");
                m.setNome(scanner.nextLine());
                System.out.print("Digite a nova quantidade: ");
                m.setQuantidade(scanner.nextLong());
                System.out.print("Digite o novo valor: ");
                m.setValor(scanner.nextDouble());
                scanner.nextLine(); // Consumir a quebra de linha
                repositorioUsuario.save(usuario);
                System.out.println("Mercadoria atualizada com sucesso!");
            } else {
                System.out.println("Mercadoria não encontrada.");
            }
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
    }

    private void apagarMercadoriaFornecedor() {
        System.out.print("Digite o email do fornecedor: ");
        String email = scanner.nextLine();
        Optional<Usuario> fornecedor = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (fornecedor.isPresent()) {
            Usuario usuario = fornecedor.get();
            System.out.print("Digite o nome da mercadoria a ser removida: ");
            String nomeMercadoria = scanner.nextLine();
            usuario.getMercadorias().removeIf(m -> m.getNome().equals(nomeMercadoria));
            repositorioUsuario.save(usuario);
            System.out.println("Mercadoria removida com sucesso!");
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
    }
}
