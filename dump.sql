-- Pelo terminal psql, este arquivo pode ser rodado inteiro.
-- No pgAdmin, rode primeiro o CREATE DATABASE, depois conecte no banco
-- agenda_telefonica e execute a partir do DROP TABLE.

CREATE DATABASE agenda_telefonica;

\c agenda_telefonica;

DROP TABLE IF EXISTS contatos;

CREATE TABLE contatos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL
);

INSERT INTO contatos (nome, telefone, email) VALUES
('Ana Silva', '11999991111', 'ana@email.com'),
('Bruno Souza', '21988882222', 'bruno@email.com'),
('Carla Mendes', '31977773333', 'carla@email.com'),
('Diego Lima', '41966664444', 'diego@email.com');
