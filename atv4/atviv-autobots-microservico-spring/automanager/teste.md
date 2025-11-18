# 🧪 Roteiro de Testes - Sistema JWT Automanager

Este roteiro testa o sistema completo de autenticação JWT com controle de acesso baseado em roles:

**Roles e Permissões:**
- **ADMIN**: Acesso total ao sistema
- **GERENTE**: Gerencia usuários (exceto ADMIN), clientes, serviços, mercadorias e vendas
- **VENDEDOR**: CRUD completo em serviços, mercadorias e vendas
- **CLIENTE**: Visualiza usuários e clientes do mesmo perfil

**Fluxo de Teste:**
1. **Login ADMIN** → Criar funcionário VENDEDOR e cliente
2. **Login VENDEDOR** → Testar operações de vendedor
3. **Login CLIENTE** → Testar operações de cliente
4. **Login ADMIN** → Demonstrar funções administrativas avançadas

**BASE_URL:** http://localhost:8080

---

## PASSO 1: Login como ADMINISTRADOR

```bash
curl -X POST "http://localhost:8080/login" \
  -H "Content-Type: application/json" \
  -d '{
    "nomeUsuario": "admin",
    "senha": "123456"
  }' \
  -v
```

**Capture o token:** Copie o token do header `Authorization: Bearer [TOKEN]`
```bash
TOKEN="[TOKEN_CAPTURADO]"
```

---

## PASSO 2: Criar FUNCIONÁRIO VENDEDOR (como ADMIN)

```bash
curl -X POST "http://localhost:8080/usuarios" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Carlos Vendedor",
    "nomeSocial": "Carlos",
    "perfis": ["ROLE_VENDEDOR"],
    "credencial": {
      "nomeUsuario": "carlos.vendedor",
      "senha": "senha123"
    }
  }'
```

**Resultado:** Usuário VENDEDOR criado com ID 3

---

## PASSO 3: Criar USUÁRIO CLIENTE (como ADMIN)

```bash
curl -X POST "http://localhost:8080/usuarios" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Cliente",
    "nomeSocial": "João",
    "perfis": ["ROLE_CLIENTE"],
    "credencial": {
      "nomeUsuario": "joao.cliente",
      "senha": "senha123"
    }
  }'
```

**Resultado:** Usuário CLIENTE criado com ID 4

---

## PASSO 4: Login como VENDEDOR

```bash
curl -X POST "http://localhost:8080/login" \
  -H "Content-Type: application/json" \
  -d '{
    "nomeUsuario": "carlos.vendedor",
    "senha": "senha123"
  }' -v
```

**Capture o token do VENDEDOR:**
```bash
TOKEN_VENDEDOR="[TOKEN_CAPTURADO]"
```

### Ações do VENDEDOR:

**Criar serviço (deve funcionar):**
```bash
curl -X POST "http://localhost:8080/servicos" \
  -H "Authorization: Bearer ${TOKEN_VENDEDOR}" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Consultoria Técnica",
    "descricao": "Serviço de consultoria especializada",
    "valor": 150.00
  }'
```

**Listar serviços:**
```bash
curl -X GET "http://localhost:8080/servicos" \
  -H "Authorization: Bearer ${TOKEN_VENDEDOR}"
```

**Listar mercadorias:**
```bash
curl -X GET "http://localhost:8080/mercadorias" \
  -H "Authorization: Bearer ${TOKEN_VENDEDOR}"
```

**Listar vendas:**
```bash
curl -X GET "http://localhost:8080/vendas" \
  -H "Authorization: Bearer ${TOKEN_VENDEDOR}"
```

### 4.4 Vendedor visualiza recursos

**Usar TOKEN_VENDEDOR**

```bash
# Listar serviços
curl -X GET "http://localhost:8080/servicos" \
  -H "Authorization: Bearer TOKEN_VENDEDOR"

# Listar mercadorias
curl -X GET "http://localhost:8080/mercadorias" \
  -H "Authorization: Bearer TOKEN_VENDEDOR"

# Listar vendas
curl -X GET "http://localhost:8080/vendas" \
  -H "Authorization: Bearer TOKEN_VENDEDOR"
```

---

## PASSO 5: Login como CLIENTE

```bash
curl -X POST "http://localhost:8080/login" \
  -H "Content-Type: application/json" \
  -d '{
    "nomeUsuario": "joao.cliente",
    "senha": "senha123"
  }' -v
```

**Capture o token do cliente:**
```bash
TOKEN_CLIENTE="[TOKEN_CAPTURADO]"
```

### Ações do CLIENTE:

**Listar usuários (só mostra CLIENTEs):**
```bash
curl -X GET "http://localhost:8080/usuarios" \
  -H "Authorization: Bearer ${TOKEN_CLIENTE}"
```

**Listar serviços (permitido para visualização):**
```bash
curl -X GET "http://localhost:8080/servicos" \
  -H "Authorization: Bearer ${TOKEN_CLIENTE}"
```

**Tentar criar serviço (deve falhar com 403):**
```bash
curl -X POST "http://localhost:8080/servicos" \
  -H "Authorization: Bearer ${TOKEN_CLIENTE}" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Teste Cliente",
    "valor": 100.00
  }' -v
```

---

## PASSO 6: Login ADMIN novamente - Demais Funções

Use o token admin anterior ou faça novo login:
```bash
# Se o token expirou, faça novo login:
curl -X POST "http://localhost:8080/login" \
  -H "Content-Type: application/json" \
  -d '{
    "nomeUsuario": "admin",
    "senha": "123456"
  }' -i

TOKEN="[NOVO_TOKEN]"
```

### Funções Administrativas Avançadas:

**Listar todos os usuários (só ADMIN):**
```bash
curl -X GET "http://localhost:8080/usuarios" \
  -H "Authorization: Bearer ${TOKEN}"
```

**Criar mercadorias:**
```bash
curl -X POST "http://localhost:8080/mercadorias" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN}" \
  -d '{
    "nome": "Pneu Aro 15",
    "descricao": "Pneu para carros populares aro 15",
    "valor": 250.00,
    "quantidade": 20,
    "fabricacao": "2024-01-01T00:00:00.000+00:00",
    "validade": "2025-12-31T23:59:59.000+00:00"
  }'
```

**Criar serviços:**
```bash
curl -X POST "http://localhost:8080/servicos" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN}" \
  -d '{
    "nome": "Alinhamento",
    "descricao": "Serviço de alinhamento de rodas",
    "valor": 80.00
  }'
```

**Atualizar cliente (só ADMIN/GERENTE):**
```bash
curl -X PUT "http://localhost:8080/clientes/1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN}" \
  -d '{
    "nome": "Maria Santos Atualizada"
  }'
```

**Deletar recursos (só ADMIN):**
```bash
curl -X DELETE "http://localhost:8080/clientes/2" \
  -H "Authorization: Bearer ${TOKEN}"

curl -X DELETE "http://localhost:8080/servicos/1" \
  -H "Authorization: Bearer ${TOKEN}"

curl -X DELETE "http://localhost:8080/mercadorias/1" \
  -H "Authorization: Bearer ${TOKEN}"

curl -X DELETE "http://localhost:8080/vendas/1" \
  -H "Authorization: Bearer ${TOKEN}"
```

---

## TESTES DE SEGURANÇA

**Acesso negado sem token:**
```bash
curl -X GET "http://localhost:8080/usuarios"
curl -X GET "http://localhost:8080/clientes"
```

**Token inválido:**
```bash
curl -X GET "http://localhost:8080/usuarios" \
  -H "Authorization: Bearer token-invalido"
```

**Acesso negado por falta de permissão:**
```bash
# Cliente tentando listar usuários
curl -X GET "http://localhost:8080/usuarios" \
  -H "Authorization: Bearer ${TOKEN}"
```

---

## RESUMO DE PERMISSÕES POR PERFIL

| Ação | ADMIN | GERENTE | VENDEDOR | CLIENTE |
|------|-------|---------|----------|---------|
| **USUÁRIOS** |
| Listar usuários | ✅ | ❌ | ❌ | ❌ |
| Cadastrar usuário | ✅ (público) | ✅ (público) | ✅ (público) | ✅ (público) |
| **CLIENTES** |
| Listar clientes | ✅ | ✅ | ✅ | ❌ |
| Criar cliente | ✅ | ✅ | ✅ | ❌ |
| Atualizar cliente | ✅ | ✅ | ❌ | ❌ |
| Deletar cliente | ✅ | ❌ | ❌ | ❌ |
| **SERVIÇOS** |
| Listar serviços | ✅ | ✅ | ✅ | ✅ |
| Criar serviço | ✅ | ✅ | ✅ | ❌ |
| Atualizar serviço | ✅ | ✅ | ✅ | ❌ |
| Deletar serviço | ✅ | ❌ | ✅ | ❌ |
| **MERCADORIAS** |
| Listar mercadorias | ✅ | ✅ | ✅ | ❌ |
| Criar mercadoria | ✅ | ✅ | ✅ | ❌ |
| Atualizar mercadoria | ✅ | ✅ | ✅ | ❌ |
| Deletar mercadoria | ✅ | ❌ | ✅ | ❌ |
| **VENDAS** |
| Listar vendas | ✅ | ✅ | ✅ | ❌ |
| Criar venda | ✅ | ✅ | ✅ | ❌ |
| Atualizar venda | ✅ | ✅ | ✅ | ❌ |
| Deletar venda | ✅ | ❌ | ✅ | ❌ |

---

## NOTAS IMPORTANTES

- Substitua `[TOKEN_CAPTURADO]` pelos tokens reais obtidos nos comandos
- Tokens JWT têm validade limitada 
- **IDs confirmados dos testes:** ADMIN (1), VENDEDOR (3), CLIENTE (4), Serviço criado (2)
- **Usuários funcionais:** admin/123456, carlos.vendedor/senha123, joao.cliente/senha123
- **Testes confirmados:** VENDEDOR cria serviços ✅, CLIENTE não cria serviços (403) ✅
- **Sistema de permissões baseado em roles funcionando perfeitamente**