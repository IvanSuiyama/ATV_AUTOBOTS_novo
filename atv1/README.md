# 🚗 ATV1 - Automanager Spring Boot

Sistema básico de gerenciamento de clientes usando Spring Boot com JPA e banco H2.

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
- **Spring Boot 2.6.3**
- **Spring Data JPA 2.6.3**

### Banco de Dados
- **H2 Database** (in-memory para desenvolvimento)
- **Hibernate** (JPA provider)

### Utilitários
- **Lombok** - Redução de código boilerplate

## 🚀 Como Executar

### 1. Navegar para o diretório do projeto
```bash
cd atv1/automanager
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

## 🗄️ Banco de Dados H2

### Console Web
- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

## 📡 Endpoints da API

### Clientes
- `GET /clientes` - Listar todos os clientes
- `POST /clientes` - Criar cliente
- `GET /clientes/{id}` - Buscar cliente por ID
- `PUT /clientes/{id}` - Atualizar cliente
- `DELETE /clientes/{id}` - Deletar cliente

### Documentos
- `GET /documentos` - Listar documentos
- `POST /documentos` - Criar documento
- `PUT /documentos/{id}` - Atualizar documento
- `DELETE /documentos/{id}` - Deletar documento

### Telefones
- `GET /telefones` - Listar telefones
- `POST /telefones` - Criar telefone
- `PUT /telefones/{id}` - Atualizar telefone
- `DELETE /telefones/{id}` - Deletar telefone

### Endereços
- `GET /enderecos` - Listar endereços
- `POST /enderecos` - Criar endereço
- `PUT /enderecos/{id}` - Atualizar endereço
- `DELETE /enderecos/{id}` - Deletar endereço

## 🧪 Teste Básico

### Listar Clientes
```bash
curl -X GET http://localhost:8080/clientes
```

### Criar Cliente
```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "dataNascimento": "1990-01-01",
    "nomeSocial": "João"
  }'
```

## 📁 Estrutura do Projeto

```
src/main/java/com/autobots/automanager/
├── controles/            # REST Controllers
├── entidades/           # JPA Entities
├── repositorios/        # JPA Repositories
├── modelos/            # Business Logic Classes
└── dto/                # Data Transfer Objects

src/main/resources/
└── application.properties # Application configuration
```

## ⚙️ Entidades Principais

- **Cliente** - Dados pessoais do cliente
- **Documento** - CPF, RG, CNH, etc.
- **Telefone** - Contatos telefônicos
- **Endereco** - Endereços do cliente

## 🐛 Solução de Problemas

### Porta 8080 em Uso
```bash
# Verificar processos na porta 8080
lsof -i :8080

# Matar processo se necessário
kill -9 <PID>
```

### Erro de Permissão Maven
```bash
chmod +x mvnw
```

## 📚 Tecnologias Utilizadas

- Spring Boot 2.6.3
- Spring Data JPA
- H2 Database
- Lombok
- Maven

---

🎯 **Sistema Básico de CRUD** - Gerenciamento simples de clientes sem HATEOAS.