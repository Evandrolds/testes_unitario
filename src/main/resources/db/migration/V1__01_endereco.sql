CREATE SCHEMA teste;
CREATE TABLE teste.endereco (
    id                      SERIAL                  PRIMARY KEY,
    rua                     VARCHAR(100)            NOT NULL,
    numero                  INTEGER                 NOT NULL,
    complemento             VARCHAR(30),
    cidade                  VARCHAR(50)             NOT NULL,
    estado                  CHAR(2)                 NOT NULL,
    cep                     CHAR(8)                 NOT NULL
);
