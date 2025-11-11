package com.autobots.automanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@SpringBootApplication
public class AutomanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomanagerApplication.class, args);
    }

    @Component
    static class Runner implements ApplicationRunner {
        @Autowired
        public ClienteRepositorio clienteRepositorio;
        @Autowired
        public DocumentoRepositorio documentoRepositorio;
        @Autowired
        public TelefoneRepositorio telefoneRepositorio;
        @Autowired
        public EnderecoRepositorio enderecoRepositorio;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            // Cliente exemplo
            Cliente cliente = new Cliente();
            cliente.setNome("Ivan");
            cliente.setNomeSocial("Ivan Dev");
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.set(1995, java.util.Calendar.AUGUST, 29);
            cliente.setDataNascimento(cal.getTime());
            cliente.setDataCadastro(new java.util.Date());
            clienteRepositorio.save(cliente);

            // Documento exemplo
            Documento documento = new Documento();
            documento.setTipo("RG");
            documento.setNumero("123456789");
            documento.setCliente(cliente);
            documentoRepositorio.save(documento);

            // Telefone exemplo
            Telefone telefone = new Telefone();
            telefone.setDdd("11");
            telefone.setNumero("999999999");
            telefone.setCliente(cliente);
            telefoneRepositorio.save(telefone);

            // Endereco exemplo
            Endereco endereco = new Endereco();
            endereco.setEstado("SP");
            endereco.setCidade("São Paulo");
            endereco.setBairro("Centro");
            endereco.setRua("Rua Exemplo");
            endereco.setNumero("123");
            endereco.setCodigoPostal("01000-000");
            endereco.setInformacoesAdicionais("Próximo à praça");
            endereco.setCliente(cliente);
            enderecoRepositorio.save(endereco);
        }
    }
}