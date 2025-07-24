CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE perfis (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuario_perfis (
    usuario_id BIGINT NOT NULL,
    perfil_id   BIGINT NOT NULL,
    PRIMARY KEY(usuario_id, perfil_id),
    CONSTRAINT fk_usuario FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_perfil  FOREIGN KEY(perfil_id) REFERENCES perfis(id)
);
