# 🧪 Guia de Testes - Sistema JWT Automanager

Este documento contém todos os comandos curl para testar o sistema de autenticação JWT e CRUD com diferentes níveis de permissão.

## 🔐 1. AUTENTICAÇÃO

### Login Admin (usuário padrão)
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "nomeUsuario": "admin",
    "senha": "123456"
  }' \
  -v
```

### Login usuário personalizado
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "nomeUsuario": "carlos",
    "senha": "123456"
  }' \
  -v
```

**📝 Copie o token do header `Authorization: Bearer [TOKEN]` e substitua `[TOKEN_AQUI]` nos comandos abaixo!**

---

## 👥 2. USUÁRIOS

### Cadastrar Usuário (PÚBLICO - sem token)
```bash
# Admin
curl -X POST http://localhost:8080/usuarios/cadastrar \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Carlos Admin",
    "credencial": {
      "nomeUsuario": "carlos",
      "senha": "123456"
    },
    "perfis": ["ROLE_ADMIN"]
  }'

# Gerente
curl -X POST http://localhost:8080/usuarios/cadastrar \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Gerente",
    "credencial": {
      "nomeUsuario": "maria",
      "senha": "123456"
    },
    "perfis": ["ROLE_GERENTE"]
  }'

# Vendedor
curl -X POST http://localhost:8080/usuarios/cadastrar \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Vendedor",
    "credencial": {
      "nomeUsuario": "joao",
      "senha": "123456"
    },
    "perfis": ["ROLE_VENDEDOR"]
  }'

# Cliente
curl -X POST http://localhost:8080/usuarios/cadastrar \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Pedro Cliente",
    "credencial": {
      "nomeUsuario": "pedro",
      "senha": "123456"
    },
    "perfis": ["ROLE_CLIENTE"]
  }'
```

### Listar Usuários (ADMIN apenas)
```bash
curl -X GET http://localhost:8080/usuarios \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

---

## 🏢 3. CLIENTES

### Criar Cliente (ADMIN, GERENTE, VENDEDOR)
```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "nome": "Maria Santos"
  }'

curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "nome": "José Silva"
  }'
```

### Listar Clientes (ADMIN, GERENTE, VENDEDOR)
```bash
curl -X GET http://localhost:8080/clientes \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

### Buscar Cliente por ID (ADMIN, GERENTE, VENDEDOR)
```bash
curl -X GET http://localhost:8080/clientes/1 \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

### Atualizar Cliente (ADMIN, GERENTE)
```bash
curl -X PUT http://localhost:8080/clientes/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "nome": "Maria Santos Silva"
  }'
```

### Deletar Cliente (ADMIN apenas)
```bash
curl -X DELETE http://localhost:8080/clientes/1 \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

---

## 🔧 4. SERVIÇOS

### Criar Serviço (ADMIN, GERENTE)
```bash
curl -X POST http://localhost:8080/servicos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "nome": "Troca de Óleo",
    "descricao": "Serviço completo de troca de óleo do motor",
    "valor": 50.0
  }'

curl -X POST http://localhost:8080/servicos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "nome": "Alinhamento",
    "descricao": "Alinhamento e balanceamento das rodas",
    "valor": 80.0
  }'
```

### Listar Serviços (todos os roles)
```bash
curl -X GET http://localhost:8080/servicos \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

### Buscar Serviço por ID (todos os roles)
```bash
curl -X GET http://localhost:8080/servicos/1 \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

### Atualizar Serviço (ADMIN, GERENTE)
```bash
curl -X PUT http://localhost:8080/servicos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "nome": "Troca de Óleo Premium",
    "descricao": "Serviço completo de troca de óleo sintético",
    "valor": 75.0
  }'
```

### Deletar Serviço (ADMIN apenas)
```bash
curl -X DELETE http://localhost:8080/servicos/1 \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

---

## 📦 5. MERCADORIAS

### Criar Mercadoria (ADMIN, GERENTE)
```bash
curl -X POST http://localhost:8080/mercadorias \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "nome": "Filtro de Óleo",
    "descricao": "Filtro de óleo original",
    "valor": 25.0,
    "quantidade": 50,
    "fabricacao": "2024-01-01T00:00:00.000+00:00",
    "validade": "2025-12-31T23:59:59.000+00:00"
  }'

curl -X POST http://localhost:8080/mercadorias \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "nome": "Pneu 185/60R15",
    "descricao": "Pneu radial aro 15",
    "valor": 280.0,
    "quantidade": 20,
    "fabricacao": "2024-06-01T00:00:00.000+00:00",
    "validade": "2029-06-01T00:00:00.000+00:00"
  }'
```

### Listar Mercadorias (todos os roles)
```bash
curl -X GET http://localhost:8080/mercadorias \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

### Buscar Mercadoria por ID (todos os roles)
```bash
curl -X GET http://localhost:8080/mercadorias/1 \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

### Atualizar Mercadoria (ADMIN, GERENTE)
```bash
curl -X PUT http://localhost:8080/mercadorias/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "nome": "Filtro de Óleo Premium",
    "descricao": "Filtro de óleo sintético premium",
    "valor": 35.0,
    "quantidade": 30,
    "fabricacao": "2024-01-01T00:00:00.000+00:00",
    "validade": "2025-12-31T23:59:59.000+00:00"
  }'
```

### Deletar Mercadoria (ADMIN apenas)
```bash
curl -X DELETE http://localhost:8080/mercadorias/1 \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

---

## 💰 6. VENDAS

### Criar Venda (todos os roles)
```bash
curl -X POST http://localhost:8080/vendas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "identificacao": "VENDA-001",
    "cliente": {
      "id": 1
    },
    "funcionario": {
      "id": 1
    },
    "servicos": [
      {
        "id": 1
      }
    ],
    "mercadorias": [
      {
        "id": 1
      }
    ]
  }'
```

### Listar Vendas (todos os roles)
```bash
curl -X GET http://localhost:8080/vendas \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

### Buscar Venda por ID (todos os roles)
```bash
curl -X GET http://localhost:8080/vendas/1 \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

### Atualizar Venda (ADMIN, GERENTE)
```bash
curl -X PUT http://localhost:8080/vendas/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer [TOKEN_AQUI]" \
  -d '{
    "identificacao": "VENDA-001-ATUALIZADA",
    "cliente": {
      "id": 1
    },
    "funcionario": {
      "id": 1
    },
    "servicos": [
      {
        "id": 1
      }
    ],
    "mercadorias": [
      {
        "id": 1
      }
    ]
  }'
```

### Deletar Venda (ADMIN apenas)
```bash
curl -X DELETE http://localhost:8080/vendas/1 \
  -H "Authorization: Bearer [TOKEN_AQUI]"
```

---

## 🚫 7. TESTES DE SEGURANÇA

### Tentar acessar sem token (deve retornar 403)
```bash
curl -X GET http://localhost:8080/usuarios
curl -X GET http://localhost:8080/clientes  
curl -X GET http://localhost:8080/servicos
```

### Tentar acessar com token inválido (deve retornar 403)
```bash
curl -X GET http://localhost:8080/usuarios \
  -H "Authorization: Bearer token-invalido"
```

---

## 📊 8. MATRIZ DE PERMISSÕES

| Endpoint | ADMIN | GERENTE | VENDEDOR | CLIENTE |
|----------|-------|---------|----------|---------|
| **Usuários** |
| GET /usuarios | ✅ | ❌ | ❌ | ❌ |
| POST /usuarios/cadastrar | ✅ (público) | ✅ (público) | ✅ (público) | ✅ (público) |
| **Clientes** |
| GET /clientes | ✅ | ✅ | ✅ | ❌ |
| POST /clientes | ✅ | ✅ | ✅ | ❌ |
| PUT /clientes/{id} | ✅ | ✅ | ❌ | ❌ |
| DELETE /clientes/{id} | ✅ | ❌ | ❌ | ❌ |
| **Serviços** |
| GET /servicos | ✅ | ✅ | ✅ | ✅ |
| POST /servicos | ✅ | ✅ | ❌ | ❌ |
| PUT /servicos/{id} | ✅ | ✅ | ❌ | ❌ |
| DELETE /servicos/{id} | ✅ | ❌ | ❌ | ❌ |
| **Mercadorias** |
| GET /mercadorias | ✅ | ✅ | ✅ | ✅ |
| POST /mercadorias | ✅ | ✅ | ❌ | ❌ |
| PUT /mercadorias/{id} | ✅ | ✅ | ❌ | ❌ |
| DELETE /mercadorias/{id} | ✅ | ❌ | ❌ | ❌ |
| **Vendas** |
| GET /vendas | ✅ | ✅ | ✅ | ✅ |
| POST /vendas | ✅ | ✅ | ✅ | ✅ |
| PUT /vendas/{id} | ✅ | ✅ | ❌ | ❌ |
| DELETE /vendas/{id} | ✅ | ❌ | ❌ | ❌ |

---

## 🔧 9. ACESSO AO BANCO H2

Para visualizar os dados no banco:

1. Acesse: http://localhost:8080/h2-console
2. **JDBC URL:** `jdbc:h2:mem:testdb`
3. **Username:** `sa`
4. **Password:** `password`

---

## 📝 NOTAS

- Todos os tokens JWT têm validade de 10 minutos (600000ms)
- Rotas públicas: `/`, `/usuarios/cadastrar`, `/login`, `/h2-console/**`
- O usuário admin padrão é criado automaticamente: `admin/123456`
- Senhas são criptografadas com BCrypt
- Aplicação roda na porta 8080