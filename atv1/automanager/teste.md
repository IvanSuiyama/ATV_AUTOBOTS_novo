# Comandos curl para testar a API Automanager

Observações:
- Ajuste a porta/host se sua aplicação não estiver em `localhost:8080`.
- Datas são enviadas em formato ISO (YYYY-MM-DD). O Jackson do Spring costuma aceitar esse formato.

---

## Clientes

# Obter cliente completo (DTO) pelo id
curl -i -X GET http://localhost:8080/cliente/cliente/1

# Listar todos os clientes
curl -i -X GET http://localhost:8080/cliente/clientes

# Cadastrar cliente (POST)
curl -i -X POST http://localhost:8080/cliente/cadastro \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "nomeSocial": "J. Silva",
    "dataNascimento": "1990-01-01",
    "dataCadastro": "2025-11-17"
  }'

# Atualizar cliente (PUT) — inclua o campo id
curl -i -X PUT http://localhost:8080/cliente/atualizar \
  -H "Content-Type: application/json" \
  -d '{
    "id": 2,
    "nome": "João da Silva",
    "nomeSocial": "J. Silva",
    "dataNascimento": "1990-01-01",
    "dataCadastro": "2025-11-17"
  }'

# Excluir cliente (DELETE) — envia objeto com id
curl -i -X DELETE http://localhost:8080/cliente/excluir \
  -H "Content-Type: application/json" \
  -d '{ "id": 2 }'

---

## Documentos

# Obter documento por id
curl -i -X GET http://localhost:8080/documento/documento/1

# Listar todos os documentos
curl -i -X GET http://localhost:8080/documento/documentos

# Cadastrar documento (relacionar ao cliente por id)
curl -i -X POST http://localhost:8080/documento/cadastro \
  -H "Content-Type: application/json" \
  -d '{
    "tipo": "CPF",
    "numero": "12345678901",
    "cliente": { "id": 2 }
  }'

# Atualizar documento (PUT)
curl -i -X PUT http://localhost:8080/documento/atualizar \
  -H "Content-Type: application/json" \
  -d '{
    "id": 2,
    "tipo": "CPF",
    "numero": "98765432100",
    "cliente": { "id": 1 }
  }'

# Excluir documento (DELETE)
curl -i -X DELETE http://localhost:8080/documento/excluir \
  -H "Content-Type: application/json" \
  -d '{ "id": 2 }'

---

## Endereços

# Obter endereço por id
curl -i -X GET http://localhost:8080/endereco/endereco/2

# Listar todos os endereços
curl -i -X GET http://localhost:8080/endereco/enderecos

# Cadastrar endereço (POST)
curl -i -X POST http://localhost:8080/endereco/cadastro \
  -H "Content-Type: application/json" \
  -d '{
    "estado": "SP",
    "cidade": "São Paulo",
    "bairro": "Centro",
    "rua": "Rua A",
    "numero": "123",
    "codigoPostal": "01000-000",
    "informacoesAdicionais": "Apartamento 12",
    "cliente": { "id": 2 }
  }'

# Atualizar endereço (PUT)
curl -i -X PUT http://localhost:8080/endereco/atualizar \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "estado": "SP",
    "cidade": "São Paulo",
    "bairro": "Centro",
    "rua": "Rua A",
    "numero": "123",
    "codigoPostal": "01000-000",
    "informacoesAdicionais": "Bloco B",
    "cliente": { "id": 2 }
  }'

# Excluir endereço (DELETE)
curl -i -X DELETE http://localhost:8080/endereco/excluir \
  -H "Content-Type: application/json" \
  -d '{ "id": 2 }'

---

## Telefones

# Obter telefone por id
curl -i -X GET http://localhost:8080/telefone/telefone/2

# Listar todos os telefones
curl -i -X GET http://localhost:8080/telefone/telefones

# Cadastrar telefone (POST)
curl -i -X POST http://localhost:8080/telefone/cadastro \
  -H "Content-Type: application/json" \
  -d '{
    "ddd": "11",
    "numero": "999988887",
    "cliente": { "id": 2 }
  }'

# Atualizar telefone (PUT)
curl -i -X PUT http://localhost:8080/telefone/atualizar \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "ddd": "11",
    "numero": "988776655",
    "cliente": { "id": 2 }
  }'

# Excluir telefone (DELETE)
curl -i -X DELETE http://localhost:8080/telefone/excluir \
  -H "Content-Type: application/json" \
  -d '{ "id": 2 }'

