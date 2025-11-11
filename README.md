# 🚗 ATV2 - Automanager Spring Boot com HATEOAS

Sistema de gerenciamento de clientes usando Spring Boot com JPA, H2 e **implementação HATEOAS** para links autodescritivos.

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
- **Spring Boot 2.6.4**
- **Spring Data JPA 2.6.4**
- **Spring HATEOAS 2.6.4** - Links autodescritivos

### Banco de Dados
- **H2 Database** (in-memory para desenvolvimento)
- **Hibernate** (JPA provider)

### Utilitários
- **Lombok** - Redução de código boilerplate

## 🚀 Como Executar

### 1. Navegar para o diretório do projeto
```bash
cd atv2/atvii-autobots-microservico-spring/automanager
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

## 🔗 HATEOAS - Links Autodescritivos

Este projeto implementa HATEOAS (Hypermedia as the Engine of Application State), onde cada resposta inclui links para ações possíveis.

### Exemplo de Resposta HATEOAS:
```json
{
  "id": 1,
  "nome": "João Silva",
  "dataNascimento": "1990-01-01",
  "_links": {
    "self": {
      "href": "http://localhost:8080/clientes/1"
    },
    "update": {
      "href": "http://localhost:8080/clientes/1"
    },
    "delete": {
      "href": "http://localhost:8080/clientes/1"
    },
    "documentos": {
      "href": "http://localhost:8080/clientes/1/documentos"
    },
    "telefones": {
      "href": "http://localhost:8080/clientes/1/telefones"
    }
  }
}
```

## 📡 Endpoints da API

### Clientes
- `GET /clientes` - Listar todos os clientes (com links HATEOAS)
- `POST /clientes` - Criar cliente
- `GET /clientes/{id}` - Buscar cliente por ID (com links)
- `PUT /clientes/{id}` - Atualizar cliente
- `DELETE /clientes/{id}` - Deletar cliente

### Documentos
- `GET /documentos` - Listar documentos (com links HATEOAS)
- `POST /documentos` - Criar documento
- `PUT /documentos/{id}` - Atualizar documento
- `DELETE /documentos/{id}` - Deletar documento

### Telefones
- `GET /telefones` - Listar telefones (com links HATEOAS)
- `POST /telefones` - Criar telefone
- `PUT /telefones/{id}` - Atualizar telefone
- `DELETE /telefones/{id}` - Deletar telefone

### Endereços
- `GET /enderecos` - Listar endereços (com links HATEOAS)
- `POST /enderecos` - Criar endereço
- `PUT /enderecos/{id}` - Atualizar endereço
- `DELETE /enderecos/{id}` - Deletar endereço

## 🧪 Testes com HATEOAS

### Listar Clientes (com links)
```bash
curl -X GET http://localhost:8080/clientes \
  -H "Accept: application/hal+json"
```

### Criar Cliente
```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "dataNascimento": "1985-05-15",
    "nomeSocial": "Maria"
  }'
```

### Buscar Cliente por ID (com links autodescritivos)
```bash
curl -X GET http://localhost:8080/clientes/1 \
  -H "Accept: application/hal+json"
```

## 🗄️ Banco de Dados H2

### Console Web
- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

## 📁 Estrutura do Projeto

```
src/main/java/com/autobots/automanager/
├── controles/            # REST Controllers com HATEOAS
├── entidades/           # JPA Entities
├── modelos/            # Business Logic e Link Adders
│   ├── AdicionadorLink*.java     # Classes para adicionar links HATEOAS
│   └── *Atualizador.java        # Classes para atualização
└── repositorios/        # JPA Repositories

src/main/resources/
└── application.properties # Application configuration
```

## 🔗 Classes HATEOAS Específicas

- **AdicionadorLinkCliente** - Adiciona links autodescritivos para Cliente
- **AdicionadorLinkTelefone** - Adiciona links para Telefone
- **AdicionadorLinkEndereco** - Adiciona links para Endereço
- **AdicionadorLinkDocumento** - Adiciona links para Documento

## ⚙️ Configuração HATEOAS

O projeto utiliza:
- `RepresentationModel` para entidades com links
- `WebMvcLinkBuilder` para geração automática de links
- Controladores que estendem funcionalidades HATEOAS

## 🐛 Solução de Problemas

### Porta 8080 em Uso
```bash
lsof -i :8080
kill -9 <PID>
```

### Links HATEOAS não aparecem
- Verifique se está usando o header: `Accept: application/hal+json`
- Confirme se as classes AdicionadorLink estão funcionando

### Erro de Permissão Maven
```bash
chmod +x mvnw
```

## 📚 Tecnologias Utilizadas

- Spring Boot 2.6.4
- Spring Data JPA
- **Spring HATEOAS** - Links autodescritivos
- H2 Database
- Lombok
- Maven

## 🎯 Recursos HATEOAS

- ✅ Links autodescritivos em todas as respostas
- ✅ Navegação hipermídia entre recursos
- ✅ Descoberta automática de ações possíveis
- ✅ Conformidade com REST Level 3 (Richardson Maturity Model)

---

🔗 **Sistema CRUD com HATEOAS** - API autodescritiva com navegação por hipermídia.