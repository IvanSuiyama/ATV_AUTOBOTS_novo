# 🚗 Automanager - Sistema de Autenticação JWT

Sistema Spring Boot com autenticação JWT e controle de acesso baseado em roles para gerenciamento de usuários, clientes, serviços, mercadorias e vendas.

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

### 🌐 Sistema Operacional
- Linux, Windows ou macOS
- Porta **8080** disponível

## 🛠️ Dependências do Projeto

### Framework Principal
- **Spring Boot 2.7.0**
- **Spring Security 5.7.1**
- **Spring Data JPA 2.7.0**

### Autenticação JWT
- **jjwt-api 0.11.5** - API JWT
- **jjwt-impl 0.11.5** - Implementação JWT  
- **jjwt-jackson 0.11.5** - Suporte JSON

### Banco de Dados
- **H2 Database** (in-memory para desenvolvimento)
- **Hibernate** (JPA provider)

### Utilitários
- **Lombok** - Redução de código boilerplate
- **Spring Boot DevTools** - Desenvolvimento

## 🚀 Como Executar

### 1. Clonar/Acessar o Projeto
```bash
cd "/caminho/para/seu/projeto/automanager"
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

Você verá no terminal:
```
Started AutomanagerApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

## 🔐 Configuração de Segurança

### Usuário Admin Padrão
- **Usuário:** `admin`  
- **Senha:** `123456`
- **Role:** `ROLE_ADMIN`

### Roles Disponíveis
- **ROLE_ADMIN** - Acesso total
- **ROLE_GERENTE** - Gerenciamento de serviços e mercadorias
- **ROLE_VENDEDOR** - Acesso a clientes e vendas
- **ROLE_CLIENTE** - Acesso limitado

## 🗄️ Banco de Dados H2

### Console Web
- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

### Configuração Automática
- Banco criado automaticamente na inicialização
- Tabelas geradas pelo Hibernate
- Dados de exemplo inseridos automaticamente

## 📡 Endpoints da API

### Públicos (sem autenticação)
- `POST /login` - Login para obter JWT
- `POST /usuarios/cadastrar` - Cadastro de usuários
- `/h2-console/**` - Console do banco H2

### Protegidos (requer JWT)
- `/usuarios/**` - Gerenciamento de usuários
- `/clientes/**` - Gerenciamento de clientes  
- `/servicos/**` - Gerenciamento de serviços
- `/mercadorias/**` - Gerenciamento de mercadorias
- `/vendas/**` - Gerenciamento de vendas

## 🧪 Testes

### Arquivo de Testes
Consulte o arquivo `teste.md` para comandos curl completos para testar todas as funcionalidades.

### Teste Básico de Login
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "nomeUsuario": "admin",
    "senha": "123456"
  }' \
  -v
```

## 📁 Estrutura do Projeto

```
src/main/java/com/autobots/automanager/
├── adaptadores/           # UserDetailsService implementation
├── configuracao/          # Security configuration
├── controles/            # REST Controllers
├── entidades/            # JPA Entities
├── filtros/              # JWT Security Filters
├── jwt/                  # JWT utilities
├── modelos/              # Enums and models
└── repositorios/         # JPA Repositories

src/main/resources/
└── application.properties # Application configuration
```

## ⚙️ Configurações Importantes

### JWT
- **Algoritmo:** HS512
- **Expiração:** 10 minutos (600000ms)
- **Secret:** Chave segura de 512+ bits

### Banco H2
- **Modo:** In-memory
- **DDL:** create-drop (recria a cada inicialização)
- **Show SQL:** Desabilitado (para saída limpa)

### CORS
- Configurado para permitir requisições cross-origin
- Headers JWT permitidos

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

### Erro JWT WeakKey
- Verifique se o `jwt.secret` tem pelo menos 64 caracteres
- Configuração já corrigida no projeto

### Banco H2 não Conecta
- Verifique se a aplicação está rodando
- URL correta: `jdbc:h2:mem:testdb`
- Usuário: `sa`, Senha: `password`

## 📚 Recursos Adicionais

### Documentação
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io/) - Decodificar tokens
- [H2 Database](http://www.h2database.com/)

### Ferramentas Recomendadas
- **Postman** ou **Insomnia** - Testes de API
- **VS Code** com extensões Java
- **IntelliJ IDEA** - IDE Java

## 👥 Contribuição

1. Fork do projeto
2. Criar branch para feature
3. Commit das mudanças
4. Push para o branch
5. Abrir Pull Request

## 📄 Licença

Este projeto está sob licença educacional para fins acadêmicos.

---

🎯 **Sistema 100% Funcional** - Autenticação JWT, Autorização por Roles, CRUD Completo, Segurança Configurada!