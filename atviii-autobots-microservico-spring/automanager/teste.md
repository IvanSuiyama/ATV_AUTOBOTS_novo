# Teste das rotas da API Automanager

Base URL (local):

- http://localhost:8080


## Rotas Rápidas (Copy / Paste)

Cole e cole diretamente no Insomnia (ou use com curl). Para requests com body, copie o JSON após `body:` e cole no corpo da requisição com `Content-Type: application/json`.

-- SERVIÇOS
POST  - http://localhost:8080/servicos  body: {"nome":"Troca de oleo","valor":50.00,"descricao":"Troca de óleo sintético"}
GET   - http://localhost:8080/servicos
GET   - http://localhost:8080/servicos/{id}
PUT   - http://localhost:8080/servicos/{id}  body: {"nome":"Troca de oleo","valor":60.00,"descricao":"Atualizado"}
DELETE- http://localhost:8080/servicos/{id}

-- MERCADORIAS
POST  - http://localhost:8080/mercadorias  body: {"validade":"2026-11-19T00:00:00Z","fabricao":"2024-11-19T00:00:00Z","cadastro":"2025-11-19T00:00:00Z","nome":"Óleo 5W-30","quantidade":10,"valor":120.50,"descricao":"Óleo lubrificante"}
GET   - http://localhost:8080/mercadorias
GET   - http://localhost:8080/mercadorias/{id}
PUT   - http://localhost:8080/mercadorias/{id}  body: {"validade":"2027-01-01T00:00:00Z","fabricao":"2024-01-01T00:00:00Z","cadastro":"2025-11-19T00:00:00Z","nome":"Óleo 5W-30","quantidade":20,"valor":130.00,"descricao":"Atualizado"}
DELETE- http://localhost:8080/mercadorias/{id}

-- VEÍCULOS
POST  - http://localhost:8080/veiculos  body: {"tipo":"HATCH","marca":"Fiat","modelo":"Uno","cor":"Branco","placa":"BUU1413","proprietario":{"id":1}}
GET   - http://localhost:8080/veiculos
GET   - http://localhost:8080/veiculos/{id}
PUT   - http://localhost:8080/veiculos/{id}  body: {"tipo":"HATCH","marca":"Fiat","modelo":"Uno","cor":"Preto","placa":"BUU1413","proprietario":{"id":1}}
DELETE- http://localhost:8080/veiculos/{id}

-- EMAILS
POST  - http://localhost:8080/emails  body: {"endereco":"ivan@email.com"}
GET   - http://localhost:8080/emails
GET   - http://localhost:8080/emails/{id}
PUT   - http://localhost:8080/emails/{id}  body: {"endereco":"novo@email.com"}
DELETE- http://localhost:8080/emails/{id}

-- TELEFONES
POST  - http://localhost:8080/telefones  body: {"ddd":"11","numero":"999999999"}
GET   - http://localhost:8080/telefones
GET   - http://localhost:8080/telefones/{id}
PUT   - http://localhost:8080/telefones/{id}  body: {"ddd":"11","numero":"988888888"}
DELETE- http://localhost:8080/telefones/{id}

-- DOCUMENTOS
POST  - http://localhost:8080/documentos  body: {"tipo":"CPF","dataEmissao":"2020-01-01T00:00:00Z","numero":"12345678900"}
GET   - http://localhost:8080/documentos
GET   - http://localhost:8080/documentos/{id}
PUT   - http://localhost:8080/documentos/{id}  body: {"tipo":"CPF","dataEmissao":"2020-01-01T00:00:00Z","numero":"12345678900"}
DELETE- http://localhost:8080/documentos/{id}

-- CREDENCIAIS
POST  - http://localhost:8080/credenciais  body (usuario/senha): {"criacao":"2025-11-19T00:00:00Z","inativo":false,"nomeUsuario":"ivan","senha":"senha123"}
POST  - http://localhost:8080/credenciais  body (codigo barra): {"criacao":"2025-11-19T00:00:00Z","inativo":false,"codigo":1234567890}
GET   - http://localhost:8080/credenciais
GET   - http://localhost:8080/credenciais/{id}
PUT   - http://localhost:8080/credenciais/{id}  body: (mesmo formato do POST)
DELETE- http://localhost:8080/credenciais/{id}

-- ENDEREÇOS
POST  - http://localhost:8080/enderecos  body: {"estado":"SP","cidade":"São Paulo","bairro":"Centro","rua":"Rua A","numero":"123","codigoPostal":"01001000","informacoesAdicionais":""}
GET   - http://localhost:8080/enderecos
GET   - http://localhost:8080/enderecos/{id}
PUT   - http://localhost:8080/enderecos/{id}  body: (mesmo formato do POST)
DELETE- http://localhost:8080/enderecos/{id}

-- USUÁRIOS
POST  - http://localhost:8080/usuarios  body: {"nome":"Ivan","nomeSocial":"Ivan Silva","perfis":["CLIENTE"],"telefones":[{"ddd":"11","numero":"999999999"}],"emails":[{"endereco":"ivan@email.com"}],"endereco":{"estado":"SP","cidade":"São Paulo","bairro":"Centro","rua":"Rua A","numero":"123","codigoPostal":"01001000"}}
GET   - http://localhost:8080/usuarios
GET   - http://localhost:8080/usuarios/{id}
PUT   - http://localhost:8080/usuarios/{id}  body: (mesmo formato do POST)
DELETE- http://localhost:8080/usuarios/{id}

Associações de `Usuario` (copy/paste URLs):
POST  - http://localhost:8080/usuarios/{id}/emails/{emailId}
DELETE- http://localhost:8080/usuarios/{id}/emails/{emailId}
POST  - http://localhost:8080/usuarios/{id}/veiculos/{veiculoId}
DELETE- http://localhost:8080/usuarios/{id}/veiculos/{veiculoId}
POST  - http://localhost:8080/usuarios/{id}/vendas/{vendaId}
DELETE- http://localhost:8080/usuarios/{id}/vendas/{vendaId}
POST  - http://localhost:8080/usuarios/{id}/mercadorias/{mercadoriaId}
DELETE- http://localhost:8080/usuarios/{id}/mercadorias/{mercadoriaId}
POST  - http://localhost:8080/usuarios/{id}/documentos/{documentoId}
DELETE- http://localhost:8080/usuarios/{id}/documentos/{documentoId}
POST  - http://localhost:8080/usuarios/{id}/telefones/{telefoneId}
DELETE- http://localhost:8080/usuarios/{id}/telefones/{telefoneId}
POST  - http://localhost:8080/usuarios/{id}/credenciais/{credencialId}
DELETE- http://localhost:8080/usuarios/{id}/credenciais/{credencialId}

-- VENDAS
POST  - http://localhost:8080/vendas  body: {"cadastro":"2025-11-19T23:00:00Z","identificacao":"AUTOMOVEL-BUU1413-1","valor":1900.00,"cliente":{"id":1},"funcionario":{"id":2},"mercadorias":[],"servicos":[],"veiculo":{"id":1}}
GET   - http://localhost:8080/vendas
GET   - http://localhost:8080/vendas/{id}
PUT   - http://localhost:8080/vendas/{id}  body: (mesmo formato do POST)
DELETE- http://localhost:8080/vendas/{id}

-- Exemplo curl rápido (criar venda):
curl -X POST http://localhost:8080/vendas -H "Content-Type: application/json" -d '{"cadastro":"2025-11-19T23:00:00Z","identificacao":"AUTOMOVEL-BUU1413-1","valor":1900.00,"cliente":{"id":1},"funcionario":{"id":2},"veiculo":{"id":1}}'



---

## Rotas e bodies (por recurso)

Cada recurso tem as rotas padrão REST:
- GET /<recurso> — lista
- GET /<recurso>/{id} — busca por id
- POST /<recurso> — cria (body JSON)
- PUT /<recurso>/{id} — atualiza (body JSON)
- DELETE /<recurso>/{id} — apaga

Para todos os exemplos abaixo use `Content-Type: application/json`.

### /usuarios
Campos principais da entidade `Usuario`:
- id (Long)
- nome (String) — obrigatório
- nomeSocial (String)
- perfis (array de `PerfilUsuario`): `CLIENTE`, `FUNCIONARIO`, `FORNECEDOR`
- telefones (array de objetos `Telefone`)
- endereco (objeto `Endereco`)
- documentos (array `Documento`)
- emails (array `Email`)
- credenciais (array `CredencialUsuarioSenha` ou outros tipos)
- mercadorias, vendas, veiculos (arrays de objetos com `id`)

POST /usuarios — criar (exemplo mínimo):

```json
{
  "nome": "Ivan",
  "nomeSocial": "Ivan Silva",
  "perfis": ["CLIENTE"],
  "telefones": [{"ddd":"11","numero":"999999999"}],
  "emails": [{"endereco":"ivan@email.com"}],
  "endereco": {
    "estado":"SP",
    "cidade":"São Paulo",
    "bairro":"Centro",
    "rua":"Rua A",
    "numero":"123",
    "codigoPostal":"01001000"
  }
}
```

GET /usuarios — lista todos os usuários (resposta contém entidades com campos e links HATEOAS).

GET /usuarios/{id} — busca usuário por id.

PUT /usuarios/{id} — atualizar (envie o mesmo JSON do POST com as mudanças).

DELETE /usuarios/{id} — exclui usuário.

Rotas de associação (exemplos):
- POST /usuarios/{id}/emails/{emailId} — adiciona email existente ao usuário
- DELETE /usuarios/{id}/emails/{emailId}
- POST /usuarios/{id}/veiculos/{veiculoId}
- DELETE /usuarios/{id}/veiculos/{veiculoId}
- POST /usuarios/{id}/vendas/{vendaId}
- DELETE /usuarios/{id}/vendas/{vendaId}
- POST /usuarios/{id}/mercadorias/{mercadoriaId}
- DELETE /usuarios/{id}/mercadorias/{mercadoriaId}
- POST /usuarios/{id}/documentos/{documentoId}
- DELETE /usuarios/{id}/documentos/{documentoId}
- POST /usuarios/{id}/telefones/{telefoneId}
- DELETE /usuarios/{id}/telefones/{telefoneId}
- POST /usuarios/{id}/credenciais/{credencialId}
- DELETE /usuarios/{id}/credenciais/{credencialId}
- POST /usuarios/{id}/servicos/{servicoId} (não implementado — retorna 501)


### /servicos
Campos: `id`, `nome` (String, obrigatório), `valor` (double, obrigatório), `descricao` (String)

POST /servicos — criar exemplo:

```json
{
  "nome": "Troca de oleo",
  "valor": 50.00,
  "descricao": "Troca de óleo sintético"
}
```

GET /servicos — lista
GET /servicos/{id}
PUT /servicos/{id} — atualizar
DELETE /servicos/{id} — apagar


### /mercadorias
Campos: `id`, `validade` (Date ISO), `fabricao` (Date ISO), `cadastro` (Date ISO), `nome`, `quantidade` (long), `valor` (double), `descricao`

POST /mercadorias — exemplo:

```json
{
  "validade": "2026-11-19T00:00:00Z",
  "fabricao": "2024-11-19T00:00:00Z",
  "cadastro": "2025-11-19T00:00:00Z",
  "nome": "Óleo 5W-30",
  "quantidade": 10,
  "valor": 120.50,
  "descricao": "Óleo lubrificante"
}
```

GET /mercadorias, GET /mercadorias/{id}, PUT /mercadorias/{id}, DELETE /mercadorias/{id}


### /veiculos
Campos: `id`, `tipo` (enum: `HATCH, SEDA, SUV, PICKUP, SW`), `marca`, `modelo`, `cor`, `placa`, `proprietario` (objeto `Usuario` com `id`)

POST /veiculos — exemplo:

```json
{
  "tipo": "HATCH",
  "marca": "Fiat",
  "modelo": "Uno",
  "cor": "Branco",
  "placa": "BUU1413",
  "proprietario": { "id": 1 }
}
```

GET /veiculos, GET /veiculos/{id}, PUT /veiculos/{id}, DELETE /veiculos/{id}


### /emails
Campos: `id`, `endereco` (String obrigatório)

POST /emails — exemplo:

```json
{ "endereco": "ivan@email.com" }
```

GET /emails, GET /emails/{id}, PUT /emails/{id}, DELETE /emails/{id}


### /telefones
Campos: `id`, `ddd`, `numero`

POST /telefones — exemplo:

```json
{ "ddd": "11", "numero": "999999999" }
```

GET /telefones, GET /telefones/{id}, PUT /telefones/{id}, DELETE /telefones/{id}


### /documentos
Campos: `id`, `tipo` (enum: `CPF, CNPJ, RG, CNH, PASSAPORTE`), `dataEmissao` (Date), `numero` (String único)

POST /documentos — exemplo:

```json
{
  "tipo": "CPF",
  "dataEmissao": "2020-01-01T00:00:00Z",
  "numero": "12345678900"
}
```

GET /documentos, GET /documentos/{id}, PUT /documentos/{id}, DELETE /documentos/{id}


### /credenciais
A entidade `Credencial` é abstrata; as implementações disponíveis são `CredencialUsuarioSenha` e `CredencialCodigoBarra`.

Exemplo `CredencialUsuarioSenha` (POST /credenciais):

```json
{
  "criacao": "2025-11-19T00:00:00Z",
  "inativo": false,
  "nomeUsuario": "ivan",
  "senha": "senha123"
}
```

Exemplo `CredencialCodigoBarra` (POST /credenciais):

```json
{
  "criacao": "2025-11-19T00:00:00Z",
  "inativo": false,
  "codigo": 1234567890
}
```

> Observação: como `Credencial` usa herança `JOINED`, ao criar via API verifique o JSON aceito no momento (pode depender de como o Jackson está serializando tipos). Se houver problema, crie a credencial através do menu ou ajuste o JSON para incluir o campo `@class` conforme configuração do Jackson.


### /vendas
Campos principais: `id`, `cadastro` (Date), `identificacao` (String, único), `valor` (double), `cliente` (`{id:...}`), `funcionario` (`{id:...}`), `mercadorias` (array de `{id:...}`), `servicos` (array de `{id:...}`), `veiculo` (`{id:...}`)

Ao criar uma `Venda` referencie entidades já existentes por `id`. Exemplo POST /vendas:

```json
{
  "cadastro": "2025-11-19T23:00:00Z",
  "identificacao": "AUTOMOVEL-BUU1413-1668810000000",
  "valor": 1900.00,
  "cliente": { "id": 1 },
  "funcionario": { "id": 2 },
  "mercadorias": [],
  "servicos": [],
  "veiculo": { "id": 1 }
}
```

Se a venda for de serviço e você quiser adicionar um serviço existente:

```json
{
  "cadastro": "2025-11-19T23:05:00Z",
  "identificacao": "SERVICO-1-1668810300000",
  "valor": 50.00,
  "cliente": { "id": 1 },
  "funcionario": { "id": 2 },
  "servicos": [ { "id": 1 } ],
  "mercadorias": []
}
```

GET /vendas, GET /vendas/{id}, PUT /vendas/{id}, DELETE /vendas/{id}


---
