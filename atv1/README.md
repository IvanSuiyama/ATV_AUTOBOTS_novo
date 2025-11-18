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


## 📚 Tecnologias Utilizadas

- Spring Boot 2.6.3
- Spring Data JPA
- H2 Database
- Lombok
- Maven

---

🎯 **Sistema Básico de CRUD** - Gerenciamento simples de clientes sem HATEOAS.