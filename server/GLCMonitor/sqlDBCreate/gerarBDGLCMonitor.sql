create database glcmonitor;

use glcmonitor;

create table usuario (
  login VARCHAR(255) NOT NULL,
  senha VARCHAR(30),
  nome VARCHAR(255),
  telefone BIGINT,
  email VARCHAR(255) UNIQUE,
  rg BIGINT UNIQUE,
  cpf BIGINT UNIQUE,
  sexo ENUM('M', 'F'),
  dataDeCadastramento DATE,
  dataDeNascimento DATE,
  PRIMARY KEY (login)
);

create table endereco (
  logradouro VARCHAR(255),
  numero INT,
  complemento VARCHAR(255),
  bairro VARCHAR(255),
  cidade VARCHAR(255),
  estado CHAR(2),
  cep BIGINT,
  usuario_login VARCHAR(255) NOT NULL,
  FOREIGN KEY (usuario_login) 
        REFERENCES usuario(login)
        ON DELETE CASCADE
);

create table sensor (
  codigo BIGINT NOT NULL,
  modelo VARCHAR(255),
  temperaturaMinima FLOAT,
  temperaturaMaxima FLOAT,
  erro FLOAT,
  usuario_login VARCHAR(255) NOT NULL,
  PRIMARY KEY (codigo),
  FOREIGN KEY (usuario_login) 
        REFERENCES usuario(login)
        ON DELETE CASCADE
);

create table registroDeTemperatura (
  temperatura FLOAT,
  momento DATETIME,
  sensor_codigo BIGINT NOT NULL,
  FOREIGN KEY (sensor_codigo) 
        REFERENCES sensor(codigo)
        ON DELETE CASCADE
);

create table usuarioSensor (
  temperaturaMinima FLOAT,
  temperaturaMaxima FLOAT,
  intervaloDeAtualizacaoDeDados INT,
  sensor_codigo BIGINT NOT NULL,
  usuario_login VARCHAR(255) NOT NULL,
  CONSTRAINT pk_usuarioSensorID PRIMARY KEY (sensor_codigo, usuario_login),
  FOREIGN KEY (sensor_codigo) 
        REFERENCES sensor(codigo)
        ON DELETE CASCADE,
  FOREIGN KEY (usuario_login) 
        REFERENCES usuario(login)
        ON DELETE CASCADE
);
