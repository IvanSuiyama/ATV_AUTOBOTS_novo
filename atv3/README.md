# 🚗 ATV3 - Automanager Sistema Completo com HATEOAS

Sistema completo de gerenciamento automotivo usando Spring Boot com JPA, H2, HATEOAS e funcionalidades avançadas como usuários, veículos, serviços, mercadorias e vendas.

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

## 🛠️ Dependências do Projeto

### Framework Principal
- **Spring Boot 2.6.7**
- **Spring Data JPA 2.6.7**
- **Spring HATEOAS 2.6.7** - Links autodescritivos
- **Spring Web** - REST API

### Banco de Dados
- **H2 Database** (in-memory para desenvolvimento)
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

## 🗄️ Banco de Dados H2

### Console Web
- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

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

## 📁 Estrutura do Projeto

```
src/main/java/com/autobots/automanager/
├── controller/          # REST Controllers
├── entitades/          # JPA Entities
├── enumeracoes/        # Enums (PerfilUsuario, TipoDocumento, etc.)
├── menus/             # Classes de menu (Terminal UI)
├── modelo/            # Business Logic Classes
└── repositorio/       # JPA Repositories

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

- ✅ Sistema completo de usuários com perfis
- ✅ Gerenciamento de veículos por cliente
- ✅ Catálogo de serviços automotivos
- ✅ Controle de estoque (mercadorias)
- ✅ Sistema de vendas integrado
- ✅ HATEOAS em todos os endpoints
- ✅ Relacionamentos complexos entre entidades
- ✅ Interface de terminal (menus)

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

- Spring Boot 2.6.7
- Spring Data JPA
- Spring HATEOAS
- H2 Database
- Hibernate
- Lombok
- Maven

---

🏢 **Sistema Automotivo Completo** - Solução integrada para gestão de oficina automotiva com HATEOAS.