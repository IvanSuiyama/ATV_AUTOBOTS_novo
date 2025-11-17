# Comandos curl de teste para Automanager

Base URL assumida: `http://localhost:8080`

Atenção:
- Ajuste a porta se sua aplicação rodar em outra porta.
- Campos de data podem precisar de formato específico (ex.: `yyyy-MM-dd` ou ISO). Ajuste conforme seu mapeamento.

---

## Clientes

1) Listar todos os clientes

curl -i -X GET "http://localhost:8080/clientes"

---

2) Obter cliente por id

curl -i -X GET "http://localhost:8080/cliente/1"

(substitua 1 pelo id desejado)

---

3) Cadastrar cliente

Exemplo de payload (ajuste datas e conteúdo conforme necessário):

```json
{
  "nome": "João Silva",
  "nomeSocial": "J. Silva",
  "dataNascimento": "1990-01-01",
  "dataCadastro": "2025-11-17",
  "documentos": [
    { "tipo": "CPF", "numero": "12345678901" }
  ],
  "endereco": {
    "estado": "SP",
    "cidade": "São Paulo",
    "bairro": "Centro",
    "rua": "Rua A",
    "numero": "100",
    "codigoPostal": "01001000",
    "informacoesAdicionais": "Bloco 1"
  },
  "telefones": [
    { "ddd": "11", "numero": "999999999" }
  ]
}
```

curl -i -X POST "http://localhost:8080/cliente/cadastro" \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","nomeSocial":"J. Silva","dataNascimento":"1990-01-01","dataCadastro":"2025-11-17","documentos":[{"tipo":"CPF","numero":"12345678901"}],"endereco":{"estado":"SP","cidade":"São Paulo","bairro":"Centro","rua":"Rua A","numero":"100","codigoPostal":"01001000","informacoesAdicionais":"Bloco 1"},"telefones":[{"ddd":"11","numero":"999999999"}]}'

---

4) Atualizar cliente

O endpoint espera um objeto `Cliente` com `id` preenchido no corpo.

Exemplo (substitua id):

curl -i -X PUT "http://localhost:8080/cliente/atualizar" \
  -H "Content-Type: application/json" \
  -d '{"id":1,"nome":"João da Silva","nomeSocial":"J. Silva","dataNascimento":"1990-01-01"}'

---

5) Excluir cliente

Este endpoint espera um JSON com o campo `id` no corpo (ao contrário dos outros que usam path variable).

curl -i -X DELETE "http://localhost:8080/cliente/excluir" \
  -H "Content-Type: application/json" \
  -d '{"id":1}'

---

## Documentos (rota base: /documentos)

1) Listar todos

curl -i -X GET "http://localhost:8080/documentos"

2) Obter por id

curl -i -X GET "http://localhost:8080/documentos/1"

3) Cadastrar

Payload de exemplo:

```json
{
  "tipo": "CPF",
  "numero": "12345678901"
}
```

curl -i -X POST "http://localhost:8080/documentos" \
  -H "Content-Type: application/json" \
  -d '{"tipo":"CPF","numero":"12345678901"}'

4) Atualizar (usa path variable id)

curl -i -X PUT "http://localhost:8080/documentos/1" \
  -H "Content-Type: application/json" \
  -d '{"tipo":"CPF","numero":"10987654321"}'

5) Excluir por id

curl -i -X DELETE "http://localhost:8080/documentos/1"

---

## Endereços (rota base: /enderecos)

1) Listar todos

curl -i -X GET "http://localhost:8080/enderecos"

2) Obter por id

curl -i -X GET "http://localhost:8080/enderecos/1"

3) Cadastrar

Payload exemplo:

```json
{
  "estado": "SP",
  "cidade": "São Paulo",
  "bairro": "Centro",
  "rua": "Rua A",
  "numero": "100",
  "codigoPostal": "01001000",
  "informacoesAdicionais": "Bloco 1"
}
```

curl -i -X POST "http://localhost:8080/enderecos" \
  -H "Content-Type: application/json" \
  -d '{"estado":"SP","cidade":"São Paulo","bairro":"Centro","rua":"Rua A","numero":"100","codigoPostal":"01001000","informacoesAdicionais":"Bloco 1"}'

4) Atualizar

curl -i -X PUT "http://localhost:8080/enderecos/1" \
  -H "Content-Type: application/json" \
  -d '{"estado":"SP","cidade":"São Paulo","bairro":"Centro","rua":"Rua A","numero":"101","codigoPostal":"01001000","informacoesAdicionais":"Apto 2"}'

5) Excluir por id

curl -i -X DELETE "http://localhost:8080/enderecos/1"

---

## Telefones (rota base: /telefones)

1) Listar todos

curl -i -X GET "http://localhost:8080/telefones"

2) Obter por id

curl -i -X GET "http://localhost:8080/telefones/1"

3) Cadastrar

Payload exemplo:

```json
{
  "ddd": "11",
  "numero": "999999999"
}
```

curl -i -X POST "http://localhost:8080/telefones" \
  -H "Content-Type: application/json" \
  -d '{"ddd":"11","numero":"999999999"}'

4) Atualizar

curl -i -X PUT "http://localhost:8080/telefones/1" \
  -H "Content-Type: application/json" \
  -d '{"ddd":"11","numero":"988888888"}'

5) Excluir por id

curl -i -X DELETE "http://localhost:8080/telefones/1"

---

