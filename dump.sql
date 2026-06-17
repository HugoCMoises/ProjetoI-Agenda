-- Primeiro execute esta linha em uma conexao normal do PostgreSQL.
CREATE DATABASE agenda_telefonica;

-- Depois conecte no banco agenda_telefonica e execute o restante.
DROP TABLE IF EXISTS contatos;

CREATE TABLE contatos (
    nome VARCHAR(100) PRIMARY KEY,
    telefone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL
);

INSERT INTO contatos (nome, telefone, email) VALUES
('Ana Silva', '11999991111', 'ana@email.com'),
('Bruno Souza', '21988882222', 'bruno@email.com'),
('Carla Mendes', '31977773333', 'carla@email.com'),
('Diego Lima', '41966664444', 'diego@email.com');
