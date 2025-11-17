# 🚗 ATV3 - Automanager Sistema Completo com HATEOAS

Sistema completo de gerenciamento automotivo usando Spring Boot com JPA, H2, HATEOAS e funcionalidades avançadas como usuários, veículos, serviços, mercadorias e vendas.

## 📖 Visão Geral do Sistema
O Automanager é um sistema completo de gestão automotiva desenvolvido em Java com Spring Boot. O sistema oferece tanto uma interface de terminal interativa quanto uma API REST completa com HATEOAS para gerenciar clientes, funcionários, fornecedores, serviços, vendas e veículos de forma integrada e profissional.

## 📋 Pré-requisitos

### ☕ Java
- **Java 17** ou superior
- Verificar instalação:
```bash
java -version
```

### 🔧 Maven
- **Maven 3.6+** ou usar o wrapper incluído (`./mvnw`)
- Verificar instalação:
```bash
mvn -version
```

## � Funcionalidades Principais

### 🔐 Sistema de Autenticação
- Login de funcionário com credenciais
- Primeiro acesso: criação automática de conta funcionário
- Controle de sessão e segurança

### 👥 Gestão de Clientes
- **CRUD Completo**: Cadastro, consulta, edição e exclusão
- **Dados Pessoais**: Nome, nome social, email (obrigatório)
- **Documentos**: CPF, CNPJ, RG, CNH, Passaporte com validação de tipos
- **Contato**: Telefones com DDD obrigatório
- **Endereço**: Completo com todos os campos obrigatórios
- **Veículos**: Gestão de automóveis do cliente

### 🚗 Gestão de Veículos
- **Informações Completas**: Tipo, marca, modelo, cor, placa
- **Tipos Suportados**: HATCH, SEDA, SUV, PICKUP, SW
- **Cadastro por Cliente**: Vinculação automática ao proprietário
- **Edição Completa**: Todos os campos editáveis

### 🔧 Gestão de Serviços
- **CRUD de Serviços**: Nome, valor, descrição
- **Busca e Consulta**: Por nome do serviço
- **Precificação**: Valor configurável por serviço

### 💰 Sistema de Vendas Inteligente
- **Tipos de Venda**: Serviços ou Automóveis
- **Seleção Dinâmica**: Lista numerada de itens disponíveis
- **Informações Detalhadas**: 
  - Serviços: Nome e valor
  - Automóveis: Marca, modelo, cor e placa
- **Valor Personalizável**: Preço específico por venda
- **Vinculação de Cliente**: Busca por email
- **Histórico**: Consulta completa de vendas com cliente e valor

### 👔 Gestão de Funcionários
- **CRUD Básico**: Nome e nome social
- **Perfis**: Sistema de perfis de usuário

### 🏢 Gestão de Fornecedores
- **Sistema Integrado**: Cadastro e gerenciamento

## �🛠️ Dependências do Projeto

### Framework Principal
- **Spring Boot 2.6.7**
- **Spring Data JPA 2.6.7**
- **Spring HATEOAS 2.6.7** - Links autodescritivos
- **Spring Web** - REST API

### Banco de Dados
- **H2 Database** (in-memory para desenvolvimento)
- **MySQL** (configurável para produção)
- **Hibernate** (JPA provider)

### Utilitários
- **Lombok** - Redução de código boilerplate

## 🚀 Como Executar

### 1. Navegar para o diretório do projeto
```bash
cd atv3/atviii-autobots-microservico-spring/automanager
```

### 2. Dar Permissão ao Maven Wrapper (Linux/Mac)
```bash
chmod +x mvnw
```

### 3. Executar a Aplicação

#### Opção A: Com Maven Wrapper (Recomendado)
```bash
./mvnw spring-boot:run
```

#### Opção B: Com Maven Instalado
```bash
mvn spring-boot:run
```

### 4. Verificar se Subiu
A aplicação estará disponível em: **http://localhost:8080**

### 5. Primeiro Acesso
1. Na primeira execução, escolha "1 - Criar conta de Funcionário" no terminal
2. Cadastre suas credenciais (usuário e senha)
3. O sistema fará login automático e exibirá o menu principal

## 🎯 Destaques Técnicos

### ✅ Validações Robustas
- **Campos Obrigatórios**: Todos os campos nullable=false validados
- **Tipos de Documento**: Validação por enum
- **Tipos de Veículo**: Validação por enum
- **Email Obrigatório**: Validação em tempo real
- **Data de Emissão**: Automática para documentos

### 🔄 Relacionamentos Complexos
```
Usuario ↔ Documentos, Telefones, Endereços, Veículos, Emails
Venda ↔ Cliente, Funcionário, Serviços, Veículo, Valor
Veiculo ↔ Proprietário, Vendas
```

### 🎮 Interface de Terminal Profissional
- **Menu Hierárquico**: Navegação intuitiva entre módulos
- **Sub-menus Organizados**: Cada entidade com seu menu específico
- **Feedback ao Usuário**: Mensagens de sucesso/erro claras
- **Validação em Tempo Real**: Tratamento de dados inválidos

## 🏢 Sistema Completo - Funcionalidades

### 👥 Gerenciamento de Usuários
- Cadastro de usuários com perfis (Admin, Gerente, Vendedor, Cliente)
- Sistema de credenciais
- Controle de acesso por perfil

### 🚗 Gerenciamento de Veículos
- Cadastro de veículos dos clientes
- Tipos de veículo (Carro, Moto, Caminhão, etc.)
- Histórico de serviços por veículo

### 🔧 Serviços Automotivos
- Catálogo de serviços oferecidos
- Preços e descrições
- Controle de execução de serviços

### 📦 Mercadorias/Peças
- Estoque de peças e produtos
- Controle de quantidade
- Preços e fornecedores

### 💰 Sistema de Vendas
- Registro de vendas de serviços e mercadorias
- Controle por cliente e funcionário
- Histórico completo de transações

## 📡 Endpoints da API

### 👤 Usuários
- `GET /usuarios` - Listar usuários
- `POST /usuarios` - Criar usuário
- `GET /usuarios/{id}` - Buscar usuário por ID
- `PUT /usuarios/{id}` - Atualizar usuário
- `DELETE /usuarios/{id}` - Deletar usuário

### 🔐 Credenciais
- `GET /credenciais` - Listar credenciais
- `POST /credenciais` - Criar credencial
- `PUT /credenciais/{id}` - Atualizar credencial
- `DELETE /credenciais/{id}` - Deletar credencial

### 🚗 Veículos
- `GET /veiculos` - Listar veículos
- `POST /veiculos` - Cadastrar veículo
- `GET /veiculos/{id}` - Buscar veículo por ID
- `PUT /veiculos/{id}` - Atualizar veículo
- `DELETE /veiculos/{id}` - Deletar veículo

### 🔧 Serviços
- `GET /servicos` - Listar serviços
- `POST /servicos` - Criar serviço
- `GET /servicos/{id}` - Buscar serviço por ID
- `PUT /servicos/{id}` - Atualizar serviço
- `DELETE /servicos/{id}` - Deletar serviço

### 📦 Mercadorias
- `GET /mercadorias` - Listar mercadorias
- `POST /mercadorias` - Criar mercadoria
- `GET /mercadorias/{id}` - Buscar mercadoria por ID
- `PUT /mercadorias/{id}` - Atualizar mercadoria
- `DELETE /mercadorias/{id}` - Deletar mercadoria

### 💰 Vendas
- `GET /vendas` - Listar vendas
- `POST /vendas` - Criar venda
- `GET /vendas/{id}` - Buscar venda por ID
- `PUT /vendas/{id}` - Atualizar venda
- `DELETE /vendas/{id}` - Deletar venda

### 📞 Telefones
- `GET /telefones` - Listar telefones
- `POST /telefones` - Criar telefone
- `PUT /telefones/{id}` - Atualizar telefone
- `DELETE /telefones/{id}` - Deletar telefone

### 📍 Endereços
- `GET /enderecos` - Listar endereços
- `POST /enderecos` - Criar endereço
- `PUT /enderecos/{id}` - Atualizar endereço
- `DELETE /enderecos/{id}` - Deletar endereço

### 📄 Documentos
- `GET /documentos` - Listar documentos
- `POST /documentos` - Criar documento
- `PUT /documentos/{id}` - Atualizar documento
- `DELETE /documentos/{id}` - Deletar documento

### 📧 Emails
- `GET /emails` - Listar emails
- `POST /emails` - Criar email
- `PUT /emails/{id}` - Atualizar email
- `DELETE /emails/{id}` - Deletar email

## 🧪 Exemplos de Teste

### Criar Usuário
```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Vendedor",
    "nomeSocial": "João",
    "perfil": "VENDEDOR"
  }'
```

### Criar Serviço
```bash
curl -X POST http://localhost:8080/servicos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Troca de Óleo",
    "descricao": "Troca completa do óleo do motor",
    "valor": 80.00
  }'
```

### Criar Mercadoria
```bash
curl -X POST http://localhost:8080/mercadorias \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Filtro de Óleo",
    "descricao": "Filtro original",
    "valor": 25.00,
    "quantidade": 50
  }'
```

### Registrar Venda
```bash
curl -X POST http://localhost:8080/vendas \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": {"id": 1},
    "funcionario": {"id": 1},
    "servicos": [{"id": 1}],
    "mercadorias": [{"id": 1}]
  }'
```

## ⚙️ Configurações

### Banco de Dados
- **Padrão**: MySQL (configurado em `application.properties`)
- **Alternativo**: H2 em memória (para desenvolvimento)

### Console Web H2 (Desenvolvimento)
- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

### Seed Data
- Funcionário padrão: usuário "ivan", senha "1234"
- Empresa exemplo já cadastrada

### Tabelas Principais
- `USUARIO` - Dados dos usuários
- `CREDENCIAL` - Credenciais de acesso
- `CLIENTE` - Dados dos clientes  
- `VEICULO` - Veículos dos clientes
- `SERVICO` - Catálogo de serviços
- `MERCADORIA` - Estoque de mercadorias
- `VENDA` - Registro de vendas
- `TELEFONE` - Contatos telefônicos
- `ENDERECO` - Endereços
- `DOCUMENTO` - Documentos (CPF, RG, etc.)
- `EMAIL` - Emails de contato

## 🎮 Como Usar o Sistema

### Menu Principal
```
--- Menu Principal ---
1 - Menu de Clientes
2 - Menu de Funcionários  
3 - Menu de Fornecedores
4 - Menu de Serviços
0 - Sair
```

### Exemplo: Cadastrar Cliente Completo
1. Escolha "1 - Menu de Clientes"
2. Escolha "1 - Cadastrar cliente"
3. Preencha:
   - Nome: "João Silva"
   - Nome social: (opcional)
   - Documento: S → CPF → "12345678901"
   - Telefone: S → DDD: "11" → Número: "999887766"
   - Endereço: S → (todos os campos)
   - Automóvel: S → HATCH → Toyota → Corolla → Branco → ABC1234
   - Email: "joao@email.com" ✅ (obrigatório)

### Exemplo: Registrar Venda
1. Menu Serviços → "3 - Registrar venda"
2. Tipo: "1 - Serviço" ou "2 - Automóvel"
3. Escolher da lista numerada
4. Informar valor: "R$ 150,00"
5. Email do cliente: "joao@email.com"
6. ✅ Venda registrada!

## 📁 Estrutura do Projeto

```
src/main/java/com/autobots/automanager/
├── controller/          # REST Controllers
│   ├── CredencialController.java
│   ├── DocumentoController.java
│   ├── EmailController.java
│   ├── EnderecoController.java
│   ├── MercadoriaController.java
│   ├── ServicoController.java
│   ├── TelefoneController.java
│   ├── UsuarioController.java
│   ├── VeiculoController.java
│   └── VendaController.java
├── entitades/          # JPA Entities (Modelos de dados)
│   ├── Usuario.java    # Funcionários e Clientes
│   ├── Veiculo.java    # Automóveis com marca, modelo, cor
│   ├── Venda.java      # Vendas com valor e relacionamentos  
│   ├── Servico.java    # Serviços oferecidos
│   ├── Mercadoria.java # Produtos e peças
│   ├── Documento.java  # CPF, RG, CNH, etc.
│   ├── Telefone.java   # Telefones com DDD
│   ├── Endereco.java   # Endereços completos
│   ├── Email.java      # Emails dos usuários
│   ├── Credencial.java # Sistema de autenticação
│   └── Empresa.java    # Dados da empresa
├── enumeracoes/        # Enums (PerfilUsuario, TipoDocumento, etc.)
│   ├── PerfilUsuario.java
│   ├── TipoDocumento.java
│   └── TipoVeiculo.java
├── menus/             # Interface de terminal
│   ├── MenuTerminal.java    # Menu principal e autenticação
│   ├── MenuCliente.java     # CRUD completo de clientes
│   ├── MenuFuncionario.java # Gestão de funcionários
│   ├── MenuFornecedor.java  # Gestão de fornecedores
│   └── MenuServico.java     # Serviços e sistema de vendas
├── repositorios/       # Repositories (Spring Data JPA)
└── AutomanagerApplication.java # Ponto de entrada + seed data

src/main/resources/
└── application.properties # Application configuration
```

## ⚙️ Enumerações Principais

### Perfil de Usuário
- `ADMINISTRADOR` - Acesso total
- `GERENTE` - Gerenciamento operacional
- `VENDEDOR` - Vendas e atendimento
- `CLIENTE` - Acesso limitado

### Tipo de Documento
- `CPF`, `RG`, `CNH`, `PASSAPORTE`

### Tipo de Veículo
- `AUTOMOVEL`, `MOTOCICLETA`, `CAMINHAO`, `ONIBUS`

## 🔗 HATEOAS Implementado

Todas as entidades possuem links autodescritivos:
- Links de navegação entre recursos relacionados
- Ações possíveis (update, delete, etc.)
- Descoberta automática da API

## 🎯 Funcionalidades Avançadas

### Relacionamentos Automáticos
- Cliente → Múltiplos documentos, telefones, veículos
- Venda → Cliente + (Serviço OU Veículo) + Valor
- Histórico completo de vendas por cliente

### Validações Inteligentes
- Email obrigatório em clientes
- DDD obrigatório em telefones  
- Data de emissão automática em documentos
- Tipos de enum validados (documento/veículo)

### Sistema de Busca
- Clientes por email
- Serviços por nome
- Funcionários por nome
- Veículos por placa

### Recursos do Sistema
- ✅ Sistema completo de usuários com perfis
- ✅ Gerenciamento de veículos por cliente
- ✅ Catálogo de serviços automotivos
- ✅ Controle de estoque (mercadorias)
- ✅ Sistema de vendas integrado
- ✅ HATEOAS em todos os endpoints
- ✅ Relacionamentos complexos entre entidades
- ✅ Interface de terminal (menus)
- ✅ Sistema de autenticação robusto
- ✅ Validações completas de dados

## 📊 Relatórios Disponíveis
- Lista completa de clientes com todos os dados
- Histórico de vendas com cliente e valor
- Consulta de automóveis por cliente
- Inventário de serviços disponíveis

## 🐛 Solução de Problemas

### Porta 8080 em Uso
```bash
lsof -i :8080
kill -9 <PID>
```

### Erro de Permissão Maven
```bash
chmod +x mvnw
```

### Problemas com Relacionamentos
- Verifique se as entidades relacionadas existem antes de criar vínculos
- Use IDs válidos nas requisições

## 📚 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 2.6.7**
- **Spring Data JPA**
- **Spring HATEOAS**
- **H2 Database** (desenvolvimento)
- **MySQL** (configurável para produção)
- **Hibernate** (JPA provider)
- **Lombok** (redução de boilerplate)
- **Maven** (gerenciamento de dependências)

## 📝 Observações Técnicas
- Todos os campos obrigatórios (`nullable = false`) devidamente validados
- Cascade configurado para operações em entidades relacionadas
- FetchType.EAGER para carregamento completo dos dados
- Tratamento robusto de exceções e validações
- Sistema HATEOAS implementado em todos os endpoints
- Interface de terminal interativa para demonstração

---

🏢 **Sistema Automotivo Completo** - Solução integrada para gestão de oficina automotiva com HATEOAS e interface de terminal.  
*Um sistema completo que vai muito além das expectativas! 🚀*