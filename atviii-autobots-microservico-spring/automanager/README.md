# Automanager

## Visão Geral
O Automanager é uma aplicação desenvolvida em Java com Spring Boot para gerenciar usuários, empresas, mercadorias, serviços, vendas, veículos, documentos, e credenciais de forma centralizada. O sistema é estruturado em microserviços e segue boas práticas de organização de código.

## Funcionalidades
- Cadastro, atualização, remoção e consulta de usuários
- Gerenciamento de empresas, mercadorias, serviços, vendas e veículos
- Associação e desassociação de documentos, e-mails, telefones, endereços e credenciais aos usuários
- API RESTful para integração com outros sistemas

## Como Executar
1. Certifique-se de ter o Java 17+ e o Maven instalados.
2. No terminal, navegue até a pasta do projeto e execute:
   ```bash
   ./mvnw spring-boot:run
   ```
   ou
   ```bash
   mvn spring-boot:run
   ```
3. A aplicação estará disponível em `http://localhost:8080`.

## Estrutura do Projeto
- `controller/`: Endpoints REST para cada entidade
- `entitades/`: Classes de domínio (modelos)
- `repositorios/`: Interfaces de acesso a dados (Spring Data JPA)
- `enumeracoes/`: Enums utilizados no sistema

## Observações
- O projeto utiliza banco de dados em memória H2 por padrão (configurável em `application.properties`).
- Para customizar endpoints ou adicionar novas entidades, utilize os padrões já existentes no projeto.

## Contato
Dúvidas ou sugestões? Entre em contato com o desenvolvedor responsável.
