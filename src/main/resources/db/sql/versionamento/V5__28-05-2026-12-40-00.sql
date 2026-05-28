CREATE TABLE public.tabela_acesso_end_point
(
  id bigint NOT NULL DEFAULT nextval('tabela_acesso_end_point_id_seq'::regclass),
  nome_end_point character varying(255),
  qtd_acesso bigint NOT NULL DEFAULT nextval('tabela_acesso_end_point_qtd_acesso_seq'::regclass)
);

ALTER TABLE tabela_acesso_end_point
ADD CONSTRAINT uk_nome_end_point UNIQUE (nome_end_point);