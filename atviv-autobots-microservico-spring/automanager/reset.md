# 🔄 Reset Simples do Sistema

## 1. VERIFICAR se existe usuário além do ADMIN

```bash
# Login ADMIN
TOKEN=$(curl -s -X POST "http://localhost:8080/login" \
  -H "Content-Type: application/json" \
  -d '{"nomeUsuario": "admin", "senha": "123456"}' | \
  grep -o 'Bearer [^"]*' | cut -d' ' -f2)

# Verificar usuários
curl -X GET "http://localhost:8080/usuarios" \
  -H "Authorization: Bearer $TOKEN"
```

**✅ OK:** Se retornar só 1 usuário (ADMIN)  
**⚠️  RESET:** Se retornar mais de 1 usuário, continue para passo 2

---

## 2. RESET TOTAL (se necessário)

```bash
# Parar aplicação
Ctrl + C

# Limpar cache
./mvnw clean

# Reiniciar
./mvnw spring-boot:run
```

**Pronto!** Sistema limpo com apenas ADMIN padrão.