DROP TABLE IF EXISTS tabela_acesso_end_point CASCADE;

CREATE TABLE tabela_acesso_end_point (
    id BIGSERIAL PRIMARY KEY,
    nome_end_point VARCHAR(255) NOT NULL UNIQUE,
    qtd_acesso BIGINT NOT NULL DEFAULT 1
);
