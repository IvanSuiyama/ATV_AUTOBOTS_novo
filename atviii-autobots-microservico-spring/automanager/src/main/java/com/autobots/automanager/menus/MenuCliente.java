package com.autobots.automanager.menus;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;
import com.autobots.automanager.entitades.Email;

@Component
public class MenuCliente {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenuClientes() {
        int opcao;

        do {
            System.out.println("--- Menu de Clientes ---");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Consultar cliente");
            System.out.println("3 - Editar cliente");
            System.out.println("4 - Cadastrar automóvel para cliente");
            System.out.println("5 - Consultar automóveis do cliente");
            System.out.println("6 - Editar automóvel do cliente");
            System.out.println("7 - Apagar automóvel do cliente");
            System.out.println("8 - Editar cliente");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    consultarCliente();
                    break;
                case 3:
                    editarCliente();
                    break;
                case 4:
                    cadastrarAutomovelCliente();
                    break;
                case 5:
                    consultarAutomoveisCliente();
                    break;
                case 6:
                    editarAutomovelCliente();
                    break;
                case 7:
                    apagarAutomovelCliente();
                    break;
                case 8:
                    // Removido duplicata de editarCliente
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void cadastrarCliente() {
        System.out.println("\n--- Cadastro de Cliente ---");
        Usuario usuario = new Usuario();
        System.out.print("Nome: ");
        usuario.setNome(scanner.nextLine());
        System.out.print("Nome social (opcional): ");
        usuario.setNomeSocial(scanner.nextLine());

        System.out.print("Deseja cadastrar um documento? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            Documento documento = new Documento();
            System.out.print("Tipo de documento (CPF, CNPJ, RG, CNH, PASSAPORTE): ");
            try {
                documento.setTipo(TipoDocumento.valueOf(scanner.nextLine().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de documento inválido. Cadastro cancelado.");
                return;
            }
            System.out.print("Número do documento: ");
            documento.setNumero(scanner.nextLine());
            documento.setDataEmissao(new java.util.Date()); // Define a data de emissão atual
            usuario.getDocumentos().add(documento);
        }

        System.out.print("Deseja cadastrar um telefone? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            Telefone telefone = new Telefone();
            System.out.print("DDD: ");
            telefone.setDdd(scanner.nextLine());
            System.out.print("Número do telefone: ");
            telefone.setNumero(scanner.nextLine());
            usuario.getTelefones().add(telefone);
        }

        System.out.print("Deseja cadastrar um endereço? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            Endereco endereco = new Endereco();
            System.out.print("Rua: ");
            endereco.setRua(scanner.nextLine());
            System.out.print("Número: ");
            endereco.setNumero(scanner.nextLine());
            System.out.print("Bairro: ");
            endereco.setBairro(scanner.nextLine());
            System.out.print("Cidade: ");
            endereco.setCidade(scanner.nextLine());
            System.out.print("Estado: ");
            endereco.setEstado(scanner.nextLine());
            System.out.print("CEP: ");
            endereco.setCodigoPostal(scanner.nextLine());
            usuario.setEndereco(endereco);
        }

        System.out.print("Deseja cadastrar um automóvel? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            Veiculo veiculo = new Veiculo();
            System.out.print("Tipo de veículo (HATCH, SEDA, SUV, PICKUP, SW): ");
            try {
                veiculo.setTipo(TipoVeiculo.valueOf(scanner.nextLine().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de veículo inválido. Cadastro cancelado.");
                return;
            }
            System.out.print("Marca do automóvel: ");
            veiculo.setMarca(scanner.nextLine());
            System.out.print("Modelo do automóvel: ");
            veiculo.setModelo(scanner.nextLine());
            System.out.print("Cor do automóvel: ");
            veiculo.setCor(scanner.nextLine());
            System.out.print("Placa do automóvel: ");
            veiculo.setPlaca(scanner.nextLine());
            usuario.getVeiculos().add(veiculo);
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();
        while (email.isEmpty()) {
            System.out.println("O email é obrigatório. Por favor, insira um email válido.");
            System.out.print("Email: ");
            email = scanner.nextLine();
        }
        Email novoEmail = new Email();
        novoEmail.setEndereco(email);
        usuario.getEmails().add(novoEmail);

        repositorioUsuario.save(usuario);
        System.out.println("Cliente cadastrado com sucesso!\n");
    }

    private void consultarCliente() {
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        Optional<Usuario> cliente = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (cliente.isPresent()) {
            Usuario usuario = cliente.get();
            System.out.println("Cliente encontrado: ");
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Nome Social: " + usuario.getNomeSocial());
            System.out.println("Endereço: ");
            if (usuario.getEndereco() != null) {
                System.out.println("Rua: " + usuario.getEndereco().getRua());
                System.out.println("Número: " + usuario.getEndereco().getNumero());
                System.out.println("Bairro: " + usuario.getEndereco().getBairro());
                System.out.println("Cidade: " + usuario.getEndereco().getCidade());
                System.out.println("Estado: " + usuario.getEndereco().getEstado());
                System.out.println("CEP: " + usuario.getEndereco().getCep());
            } else {
                System.out.println("Nenhum endereço cadastrado.");
            }
            System.out.println("Emails: ");
            usuario.getEmails().forEach(e -> System.out.println("- " + e.getEndereco()));
            System.out.println("Documentos: ");
            usuario.getDocumentos().forEach(d -> System.out.println("Tipo: " + d.getTipo() + ", Número: " + d.getNumero()));
            System.out.println("Automóveis: ");
            usuario.getVeiculos().forEach(v -> System.out.println("Marca: " + v.getMarca() + ", Modelo: " + v.getModelo() + ", Cor: " + v.getCor() + ", Placa: " + v.getPlaca()));
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void cadastrarAutomovelCliente() {
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        Optional<Usuario> cliente = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (cliente.isPresent()) {
            Usuario usuario = cliente.get();
            Veiculo veiculo = new Veiculo();
            System.out.print("Tipo de veículo (HATCH, SEDA, SUV, PICKUP, SW): ");
            try {
                veiculo.setTipo(TipoVeiculo.valueOf(scanner.nextLine().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de veículo inválido. Cadastro cancelado.");
                return;
            }
            System.out.print("Marca do automóvel: ");
            veiculo.setMarca(scanner.nextLine());
            System.out.print("Modelo do automóvel: ");
            veiculo.setModelo(scanner.nextLine());
            System.out.print("Cor do automóvel: ");
            veiculo.setCor(scanner.nextLine());
            System.out.print("Placa do automóvel: ");
            veiculo.setPlaca(scanner.nextLine());
            usuario.getVeiculos().add(veiculo);
            repositorioUsuario.save(usuario);
            System.out.println("Automóvel cadastrado com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void consultarAutomoveisCliente() {
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        Optional<Usuario> cliente = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (cliente.isPresent()) {
            Usuario usuario = cliente.get();
            System.out.println("Automóveis do cliente: ");
            usuario.getVeiculos().forEach(v -> System.out.println("Marca: " + v.getMarca() + ", Modelo: " + v.getModelo() + ", Cor: " + v.getCor() + ", Placa: " + v.getPlaca()));
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void editarAutomovelCliente() {
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        Optional<Usuario> cliente = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (cliente.isPresent()) {
            Usuario usuario = cliente.get();
            System.out.print("Digite a placa do automóvel a ser editado: ");
            String placa = scanner.nextLine();
            Optional<Veiculo> veiculo = usuario.getVeiculos().stream()
                .filter(v -> v.getPlaca().equals(placa))
                .findFirst();

            if (veiculo.isPresent()) {
                Veiculo v = veiculo.get();
                System.out.print("Digite o novo tipo (HATCH, SEDA, SUV, PICKUP, SW): ");
                try {
                    v.setTipo(TipoVeiculo.valueOf(scanner.nextLine().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo de veículo inválido. Edição cancelada.");
                    return;
                }
                System.out.print("Digite a nova marca: ");
                v.setMarca(scanner.nextLine());
                System.out.print("Digite o novo modelo: ");
                v.setModelo(scanner.nextLine());
                System.out.print("Digite a nova cor: ");
                v.setCor(scanner.nextLine());
                System.out.print("Digite a nova placa: ");
                v.setPlaca(scanner.nextLine());
                repositorioUsuario.save(usuario);
                System.out.println("Automóvel atualizado com sucesso!");
            } else {
                System.out.println("Automóvel não encontrado.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void apagarAutomovelCliente() {
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        Optional<Usuario> cliente = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (cliente.isPresent()) {
            Usuario usuario = cliente.get();
            System.out.print("Digite a placa do automóvel a ser removido: ");
            String placa = scanner.nextLine();
            usuario.getVeiculos().removeIf(v -> v.getPlaca().equals(placa));
            repositorioUsuario.save(usuario);
            System.out.println("Automóvel removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void editarCliente() {
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        Optional<Usuario> cliente = repositorioUsuario.findAll().stream()
            .filter(u -> u.getEmails().stream().anyMatch(e -> e.getEndereco().equals(email)))
            .findFirst();

        if (cliente.isPresent()) {
            Usuario usuario = cliente.get();
            boolean sair = false;
            while (!sair) {
                System.out.println("\n--- Editar Cliente ---");
                System.out.println("1 - Editar nome");
                System.out.println("2 - Editar nome social");
                System.out.println("3 - Editar email");
                System.out.println("4 - Editar documentos");
                System.out.println("5 - Editar endereço");
                System.out.println("6 - Editar telefone");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                String opcao = scanner.nextLine();

                switch (opcao) {
                    case "1":
                        System.out.print("Novo nome: ");
                        usuario.setNome(scanner.nextLine());
                        break;
                    case "2":
                        System.out.print("Novo nome social: ");
                        usuario.setNomeSocial(scanner.nextLine());
                        break;
                    case "3":
                        System.out.print("Novo email: ");
                        String novoEmail = scanner.nextLine();
                        usuario.getEmails().forEach(e -> e.setEndereco(novoEmail));
                        break;
                    case "4":
                        System.out.println("Documentos: ");
                        usuario.getDocumentos().forEach(d -> System.out.println("Tipo: " + d.getTipo() + ", Número: " + d.getNumero()));
                        System.out.print("Deseja adicionar ou editar um documento? (A/E): ");
                        String escolha = scanner.nextLine();
                        if (escolha.equalsIgnoreCase("A")) {
                            Documento novoDocumento = new Documento();
                            System.out.print("Tipo: ");
                            try {
                                novoDocumento.setTipo(TipoDocumento.valueOf(scanner.nextLine().toUpperCase()));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Tipo de documento inválido. Operação cancelada.");
                                return;
                            }
                            System.out.print("Número: ");
                            novoDocumento.setNumero(scanner.nextLine());
                            novoDocumento.setDataEmissao(new java.util.Date()); // Define a data de emissão atual
                            usuario.getDocumentos().add(novoDocumento);
                        } else if (escolha.equalsIgnoreCase("E")) {
                            System.out.print("Tipo do documento a editar: ");
                            try {
                                TipoDocumento tipoDocumento = TipoDocumento.valueOf(scanner.nextLine().toUpperCase());
                                Optional<Documento> doc = usuario.getDocumentos().stream().filter(d -> d.getTipo() == tipoDocumento).findFirst();
                                if (doc.isPresent()) {
                                    Documento documento = doc.get();
                                    System.out.print("Novo número: ");
                                    documento.setNumero(scanner.nextLine());
                                } else {
                                    System.out.println("Documento não encontrado.");
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println("Tipo de documento inválido. Operação cancelada.");
                            }
                        }
                        break;
                    case "5":
                        if (usuario.getEndereco() == null) {
                            usuario.setEndereco(new Endereco());
                        }
                        System.out.print("Nova rua: ");
                        usuario.getEndereco().setRua(scanner.nextLine());
                        System.out.print("Novo número: ");
                        usuario.getEndereco().setNumero(scanner.nextLine());
                        System.out.print("Novo bairro: ");
                        usuario.getEndereco().setBairro(scanner.nextLine());
                        System.out.print("Nova cidade: ");
                        usuario.getEndereco().setCidade(scanner.nextLine());
                        System.out.print("Novo estado: ");
                        usuario.getEndereco().setEstado(scanner.nextLine());
                        System.out.print("Novo CEP: ");
                        usuario.getEndereco().setCodigoPostal(scanner.nextLine());
                        break;
                    case "6":
                        System.out.println("Telefones: ");
                        usuario.getTelefones().forEach(t -> System.out.println("Número: " + t.getNumero()));
                        System.out.print("Deseja adicionar ou editar um telefone? (A/E): ");
                        escolha = scanner.nextLine();
                        if (escolha.equalsIgnoreCase("A")) {
                            Telefone novoTelefone = new Telefone();
                            System.out.print("DDD: ");
                            novoTelefone.setDdd(scanner.nextLine());
                            System.out.print("Número: ");
                            novoTelefone.setNumero(scanner.nextLine());
                            usuario.getTelefones().add(novoTelefone);
                        } else if (escolha.equalsIgnoreCase("E")) {
                            System.out.print("Número do telefone a editar: ");
                            String numero = scanner.nextLine();
                            Optional<Telefone> tel = usuario.getTelefones().stream().filter(t -> t.getNumero().equals(numero)).findFirst();
                            if (tel.isPresent()) {
                                Telefone telefone = tel.get();
                                System.out.print("Novo DDD: ");
                                telefone.setDdd(scanner.nextLine());
                                System.out.print("Novo número: ");
                                telefone.setNumero(scanner.nextLine());
                            } else {
                                System.out.println("Telefone não encontrado.");
                            }
                        }
                        break;
                    case "0":
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
            repositorioUsuario.save(usuario);
            System.out.println("Cliente atualizado com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
}
