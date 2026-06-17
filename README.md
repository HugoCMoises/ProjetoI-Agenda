# Agenda Telefonica

Este projeto foi feito para o Projeto Integrador II-A. A ideia e criar uma agenda telefonica simples em Java, com menu no terminal e dados salvos em um banco PostgreSQL.

## O que o sistema faz

O programa permite:

- adicionar contato;
- remover contato pelo nome;
- buscar contato pelo nome;
- listar todos os contatos;
- sair do sistema.

Cada contato possui nome, telefone e email.

## Tecnologias usadas

- Java 17
- Maven
- JDBC
- PostgreSQL

## Organizacao das classes

```text
src/main/java/
|-- AgendaTeste.java
|-- dao/
|   `-- ContatoDAO.java
|-- exception/
|   |-- CampoVazioException.java
|   `-- ContatoNaoEncontradoException.java
|-- model/
|   `-- Contato.java
|-- service/
|   `-- AgendaTelefonica.java
`-- util/
    `-- DatabaseConnection.java
```

## Banco de dados

O banco utilizado se chama:

```text
agenda_telefonica
```

O arquivo `dump.sql` possui o comando para criar o banco e tambem a tabela `contatos`.

No DBeaver, primeiro execute:

```sql
CREATE DATABASE agenda_telefonica;
```

Depois abra uma conexao com o banco `agenda_telefonica` e execute o restante do script.

## Configuracao da conexao

A conexao com o banco esta na classe `DatabaseConnection.java`:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/agenda_telefonica";
private static final String USUARIO = "postgres";
private static final String SENHA = "123456";
```

Se a senha do PostgreSQL for diferente no computador onde o projeto for rodar, basta alterar o valor da constante `SENHA`.

## Como executar

Na pasta do projeto, use:

```powershell
mvn compile exec:java
```

Ao iniciar, o programa mostra este menu:

```text
1. Adicionar contato
2. Remover contato
3. Buscar contato
4. Listar contatos
5. Sair
```

## Tratamento de excecoes

O projeto trata os principais erros pedidos no enunciado:

- contato nao encontrado;
- campos obrigatorios vazios;
- opcao invalida no menu;
- erro de conexao ou operacao com o banco de dados.

## Observacao

O projeto foi mantido simples de proposito, pois o objetivo do trabalho e usar Java, orientacao a objetos, JDBC e banco de dados com interacao pelo terminal.
