# Agenda Telefonica

## Apresentacao

A Agenda Telefonica e um sistema simples para gerenciamento de contatos. O projeto foi desenvolvido em Java com Spring Boot, PostgreSQL e uma interface web feita com HTML, CSS e JavaScript.

O sistema permite cadastrar, listar, buscar, editar e remover contatos. Cada contato possui nome, telefone e e-mail, e os dados ficam salvos no banco PostgreSQL.

## Objetivo

O objetivo do projeto e praticar um CRUD completo, com conexao ao banco de dados e comunicacao entre frontend e backend. A aplicacao tambem possui validacoes para evitar dados incorretos.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring JDBC
- PostgreSQL
- Maven
- HTML
- CSS
- JavaScript

## Estrutura do Projeto

```text
src/
`-- main/
    |-- java/
    |   `-- br/com/agenda/
    |       |-- controller/
    |       |-- dao/
    |       |-- exception/
    |       |-- model/
    |       |-- service/
    |       |-- AgendaApplication.java
    |       `-- AgendaTeste.java
    `-- resources/
        `-- application.properties

frontend/
|-- index.html
|-- style.css
`-- script.js
```

- `model`: classe que representa o contato.
- `dao`: comunicacao com o banco de dados.
- `service`: regras e validacoes principais.
- `controller`: rotas da API.
- `exception`: erros tratados pelo sistema.
- `frontend`: tela da agenda.

## Banco de Dados

Banco utilizado:

```text
agenda_telefonica
```

Tabela:

```sql
CREATE TABLE contatos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL
);
```

O arquivo `dump.sql` cria a tabela e insere alguns contatos para teste.

## Configuracao

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE agenda_telefonica;
```

Depois conecte no banco criado e execute o restante do arquivo `dump.sql`.

A conexao com o banco fica configurada em:

```text
src/main/resources/application.properties
```

Configuracao usada:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/agenda_telefonica
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Se o seu usuario ou senha forem diferentes, altere esse arquivo.

## Execucao do Sistema

Para iniciar o backend, execute a classe principal da aplicacao:

```text
AgendaApplication.java
```

O backend ficara disponivel em:

```text
http://localhost:8080
```

Com o backend em execucao, abra o arquivo:

```text
frontend/index.html
```

## Funcionalidades

- Cadastrar contato.
- Listar contatos.
- Buscar contato pelo nome.
- Editar contato.
- Excluir contato.

## Validacoes

Nome:

- obrigatorio;
- minimo de 3 caracteres;
- nao pode conter numeros.

Telefone:

- obrigatorio;
- apenas numeros;
- entre 10 e 11 digitos.

E-mail:

- obrigatorio;
- precisa ter formato valido.

As validacoes existem no frontend e no backend. Assim, mesmo que alguem tente enviar dados invalidos direto para a API, o backend tambem bloqueia.

## Rotas da API

| Metodo | Rota | Funcao |
| --- | --- | --- |
| GET | `/contatos` | Lista todos os contatos |
| GET | `/contatos/buscar?nome=Ana` | Busca contato pelo nome |
| POST | `/contatos` | Cadastra contato |
| PUT | `/contatos/{id}` | Atualiza contato |
| DELETE | `/contatos/{nome}` | Remove contato pelo nome |

## Teste do Sistema

Para testar, abra a tela no navegador e use os botoes do sistema:

1. Cadastre um contato com dados validos.
2. Liste os contatos.
3. Busque um contato pelo nome.
4. Edite um contato cadastrado.
5. Exclua um contato.

Tambem e possivel testar as validacoes usando nome com numero, telefone com letras ou e-mail invalido.

## Conclusao

O projeto apresenta uma agenda telefonica funcional, com CRUD completo, interface web, API em Java e conexao com PostgreSQL. A estrutura foi mantida simples para facilitar o entendimento e a apresentacao do sistema.
