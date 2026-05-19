--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.25
-- Dumped by pg_dump version 9.5.25

-- Started on 2026-05-19 08:19:38

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY public.status_rastreio DROP CONSTRAINT venda_compra_loja_virtual_fk;
ALTER TABLE ONLY public.nota_fiscal_venda DROP CONSTRAINT venda_compra_loja_virtual_fk;
ALTER TABLE ONLY public.item_venda_loja DROP CONSTRAINT venda_compra_loja_virtual_fk;
ALTER TABLE ONLY public.usuario_acesso DROP CONSTRAINT usuario_fk;
ALTER TABLE ONLY public.avaliacao_produto DROP CONSTRAINT produto_fk;
ALTER TABLE ONLY public.nota_item_produto DROP CONSTRAINT produto_fk;
ALTER TABLE ONLY public.item_venda_loja DROP CONSTRAINT produto_fk;
ALTER TABLE ONLY public.imagem_produto DROP CONSTRAINT produto_fk;
ALTER TABLE ONLY public.produto DROP CONSTRAINT notaitemproduto_fk;
ALTER TABLE ONLY public.venda_compra_loja_virtual DROP CONSTRAINT nota_fiscal_venda_fk;
ALTER TABLE ONLY public.nota_item_produto DROP CONSTRAINT nota_fiscal_compra_fk;
ALTER TABLE ONLY public.venda_compra_loja_virtual DROP CONSTRAINT forma_pagamento_fk;
ALTER TABLE ONLY public.venda_compra_loja_virtual DROP CONSTRAINT endereco_entrega_fk;
ALTER TABLE ONLY public.venda_compra_loja_virtual DROP CONSTRAINT endereco_cobranca_fk;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.venda_compra_loja_virtual DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.status_rastreio DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.produto DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.pessoa_fisica DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.nota_item_produto DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.nota_fiscal_venda DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.nota_fiscal_compra DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.marca_produto DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.item_venda_loja DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.imagem_produto DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.forma_pagamento DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.endereco DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.cupom_desconto DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.categoria_produto DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.conta_pagar DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.conta_receber DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.avaliacao_produto DROP CONSTRAINT empresa_fk;
ALTER TABLE ONLY public.venda_compra_loja_virtual DROP CONSTRAINT cupom_desconto_fk;
ALTER TABLE ONLY public.nota_fiscal_compra DROP CONSTRAINT conta_pagar_fk;
ALTER TABLE ONLY public.usuario_acesso DROP CONSTRAINT acesso_fk;
DROP TRIGGER validachavepessoaupdate ON public.venda_compra_loja_virtual;
DROP TRIGGER validachavepessoaupdate ON public.nota_fiscal_compra;
DROP TRIGGER validachavepessoaupdate ON public.endereco;
DROP TRIGGER validachavepessoaupdate ON public.conta_receber;
DROP TRIGGER validachavepessoaupdate ON public.conta_pagar;
DROP TRIGGER validachavepessoaupdate ON public.avaliacao_produto;
DROP TRIGGER validachavepessoainsert ON public.venda_compra_loja_virtual;
DROP TRIGGER validachavepessoainsert ON public.nota_fiscal_compra;
DROP TRIGGER validachavepessoainsert ON public.endereco;
DROP TRIGGER validachavepessoainsert ON public.conta_receber;
DROP TRIGGER validachavepessoainsert ON public.conta_pagar;
DROP TRIGGER validachavepessoainsert ON public.avaliacao_produto;
DROP TRIGGER validachavepessoafornecedorupdate ON public.conta_pagar;
DROP TRIGGER validachavepessoafornecedorinsert ON public.conta_pagar;
DROP TRIGGER validachavepessoafornecedordelete ON public.conta_pagar;
DROP TRIGGER validachavepessoadelete ON public.venda_compra_loja_virtual;
DROP TRIGGER validachavepessoadelete ON public.nota_fiscal_compra;
DROP TRIGGER validachavepessoadelete ON public.endereco;
DROP TRIGGER validachavepessoadelete ON public.conta_receber;
DROP TRIGGER validachavepessoadelete ON public.conta_pagar;
DROP TRIGGER validachavepessoadelete ON public.avaliacao_produto;
ALTER TABLE ONLY public.venda_compra_loja_virtual DROP CONSTRAINT venda_compra_loja_virtual_pkey;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
ALTER TABLE ONLY public.usuario_acesso DROP CONSTRAINT unique_acesso_user;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT uk_pm3f4m4fqv89oeeeac4tbe2f4;
ALTER TABLE ONLY public.pessoa_fisica DROP CONSTRAINT uk_p3d8co8s4y5h7y18fpqco1wv6;
ALTER TABLE ONLY public.pessoa_fisica DROP CONSTRAINT uk_d70aayxv20yf3y8kofcx7fhbg;
ALTER TABLE ONLY public.pessoa_juridica DROP CONSTRAINT uk_6f8dnvy9aakthuofgyrj66lyf;
ALTER TABLE ONLY public.pessoa_juridica DROP CONSTRAINT uk_3h78rtw3ei11cb43k77af5nhl;
ALTER TABLE ONLY public.status_rastreio DROP CONSTRAINT status_rastreio_pkey;
ALTER TABLE ONLY public.produto DROP CONSTRAINT produto_pkey;
ALTER TABLE ONLY public.pessoa_juridica DROP CONSTRAINT pessoa_juridica_pkey;
ALTER TABLE ONLY public.pessoa_fisica DROP CONSTRAINT pessoa_fisica_pkey;
ALTER TABLE ONLY public.nota_item_produto DROP CONSTRAINT nota_item_produto_pkey;
ALTER TABLE ONLY public.nota_fiscal_venda DROP CONSTRAINT nota_fiscal_venda_pkey;
ALTER TABLE ONLY public.nota_fiscal_compra DROP CONSTRAINT nota_fiscal_compra_pkey;
ALTER TABLE ONLY public.marca_produto DROP CONSTRAINT marca_produto_pkey;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT login_unique;
ALTER TABLE ONLY public.item_venda_loja DROP CONSTRAINT item_venda_loja_pkey;
ALTER TABLE ONLY public.imagem_produto DROP CONSTRAINT imagem_produto_pkey;
ALTER TABLE ONLY public.forma_pagamento DROP CONSTRAINT forma_pagamento_pkey;
ALTER TABLE ONLY public.endereco DROP CONSTRAINT endereco_pkey;
ALTER TABLE ONLY public.cupom_desconto DROP CONSTRAINT cupom_desconto_pkey;
ALTER TABLE ONLY public.conta_receber DROP CONSTRAINT conta_receber_pkey;
ALTER TABLE ONLY public.conta_pagar DROP CONSTRAINT conta_pagar_pkey;
ALTER TABLE ONLY public.categoria_produto DROP CONSTRAINT categoria_produto_pkey;
ALTER TABLE ONLY public.avaliacao_produto DROP CONSTRAINT avaliacao_produto_pkey;
ALTER TABLE ONLY public.acesso DROP CONSTRAINT acesso_pkey;
DROP TABLE public.venda_compra_loja_virtual;
DROP TABLE public.usuario_acesso;
DROP TABLE public.usuario;
DROP TABLE public.status_rastreio;
DROP SEQUENCE public.seq_venda_compra_loja_virtual;
DROP SEQUENCE public.seq_usuario;
DROP SEQUENCE public.seq_status_rastreio;
DROP SEQUENCE public.seq_produto;
DROP SEQUENCE public.seq_pessoa;
DROP SEQUENCE public.seq_nota_item_produto;
DROP SEQUENCE public.seq_nota_fiscal_venda;
DROP SEQUENCE public.seq_nota_fiscal_compra;
DROP SEQUENCE public.seq_marca_produto;
DROP SEQUENCE public.seq_item_venda_loja;
DROP SEQUENCE public.seq_imagem_produto;
DROP SEQUENCE public.seq_forma_pagamento;
DROP SEQUENCE public.seq_endereco;
DROP SEQUENCE public.seq_cupom_desconto;
DROP SEQUENCE public.seq_conta_receber;
DROP SEQUENCE public.seq_conta_pagar;
DROP SEQUENCE public.seq_categoria_produto;
DROP SEQUENCE public.seq_avaliacao_produto;
DROP SEQUENCE public.seq_acesso;
DROP TABLE public.produto;
DROP TABLE public.pessoa_juridica;
DROP TABLE public.pessoa_fisica;
DROP TABLE public.nota_item_produto;
DROP TABLE public.nota_fiscal_venda;
DROP TABLE public.nota_fiscal_compra;
DROP TABLE public.marca_produto;
DROP TABLE public.item_venda_loja;
DROP TABLE public.imagem_produto;
DROP TABLE public.forma_pagamento;
DROP TABLE public.endereco;
DROP TABLE public.cupom_desconto;
DROP TABLE public.conta_receber;
DROP TABLE public.conta_pagar;
DROP TABLE public.categoria_produto;
DROP TABLE public.avaliacao_produto;
DROP TABLE public.acesso;
DROP FUNCTION public.validachavepessoafornecedor();
DROP FUNCTION public.validachavepessoa();
DROP EXTENSION plpgsql;
DROP SCHEMA public;

CREATE SCHEMA public;

ALTER SCHEMA public OWNER TO postgres;

COMMENT ON SCHEMA public IS 'standard public schema';

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


CREATE FUNCTION public.validachavepessoa() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE existe INTEGER;

BEGIN

	existe = (SELECT COUNT(1) FROM pessoa_fisica WHERE id = NEW.pessoa_id);
	IF (existe <= 0) THEN
	 existe = (SELECT COUNT(1) FROM pessoa_juridica WHERE id = NEW.pessoa_id);
	 IF (existe <= 0) THEN
	 RAISE EXCEPTION 'Não foi encontrado o ID e PK da pessoa para realizar a associação do cadastro';
	 END IF;
	END IF;
	RETURN NEW;
END;
$$;

ALTER FUNCTION public.validachavepessoa() OWNER TO postgres;


CREATE FUNCTION public.validachavepessoafornecedor() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE existe INTEGER;

BEGIN

	existe = (SELECT COUNT(1) FROM pessoa_fisica WHERE id = NEW.pessoa_fornecedor_id);
	IF (existe <= 0) THEN
	 existe = (SELECT COUNT(1) FROM pessoa_juridica WHERE id = NEW.pessoa_fornecedor_id);
	 IF (existe <= 0) THEN
	 RAISE EXCEPTION 'Não foi encontrado o ID e PK da pessoa para realizar a associação do cadastro';
	 END IF;
	END IF;
	RETURN NEW;
END;
$$;

ALTER FUNCTION public.validachavepessoafornecedor() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE public.acesso (
    id bigint NOT NULL,
    role_user character varying(255) NOT NULL
);

ALTER TABLE public.acesso OWNER TO postgres;

CREATE TABLE public.avaliacao_produto (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    nota integer NOT NULL,
    pessoa_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.avaliacao_produto OWNER TO postgres;

CREATE TABLE public.categoria_produto (
    id bigint NOT NULL,
    nome_descricao character varying(255) NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.categoria_produto OWNER TO postgres;

CREATE TABLE public.conta_pagar (
    id bigint NOT NULL,
    data_pagamento date,
    data_vencimento date NOT NULL,
    descricao character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    valor_desconto numeric(19,2),
    valor_total numeric(19,2) NOT NULL,
    pessoa_id bigint NOT NULL,
    pessoa_fornecedor_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.conta_pagar OWNER TO postgres;

CREATE TABLE public.conta_receber (
    id bigint NOT NULL,
    data_pagamento date,
    data_vencimento date NOT NULL,
    descricao character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    valor_desconto numeric(19,2),
    valor_total numeric(19,2) NOT NULL,
    pessoa_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.conta_receber OWNER TO postgres;

CREATE TABLE public.cupom_desconto (
    id bigint NOT NULL,
    codigo_descricao character varying(255) NOT NULL,
    data_validade date NOT NULL,
    valor_porcentagem_desconto numeric(19,2),
    valor_real_desconto numeric(19,2),
    empresa_id bigint NOT NULL
);

ALTER TABLE public.cupom_desconto OWNER TO postgres;

CREATE TABLE public.endereco (
    id bigint NOT NULL,
    bairro character varying(255) NOT NULL,
    cep character varying(255) NOT NULL,
    cidade character varying(255) NOT NULL,
    complemento character varying(255),
    numero character varying(255) NOT NULL,
    rua character varying(255) NOT NULL,
    tipo_endereco character varying(255) NOT NULL,
    uf character varying(255) NOT NULL,
    pessoa_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.endereco OWNER TO postgres;

CREATE TABLE public.forma_pagamento (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    tipo_pagamento character varying(255) NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.forma_pagamento OWNER TO postgres;

CREATE TABLE public.imagem_produto (
    id bigint NOT NULL,
    imagem_miniatura text NOT NULL,
    imagem_original text NOT NULL,
    produto_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.imagem_produto OWNER TO postgres;

CREATE TABLE public.item_venda_loja (
    id bigint NOT NULL,
    quantidade double precision NOT NULL,
    produto_id bigint NOT NULL,
    venda_compra_loja_virtual_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.item_venda_loja OWNER TO postgres;

CREATE TABLE public.marca_produto (
    id bigint NOT NULL,
    nome_descricao character varying(255) NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.marca_produto OWNER TO postgres;

CREATE TABLE public.nota_fiscal_compra (
    id bigint NOT NULL,
    data_compra date NOT NULL,
    descricao_observacao character varying(255),
    numero_nota character varying(255) NOT NULL,
    serie_nota character varying(255) NOT NULL,
    valor_desconto numeric(19,2),
    valor_icms numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    conta_pagar_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.nota_fiscal_compra OWNER TO postgres;

CREATE TABLE public.nota_fiscal_venda (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    numero_nota character varying(255) NOT NULL,
    pdf text NOT NULL,
    serie_nota character varying(255) NOT NULL,
    tipo character varying(255) NOT NULL,
    valor_desconto double precision,
    valor_icms double precision NOT NULL,
    valor_total double precision NOT NULL,
    xml text NOT NULL,
    venda_compra_loja_virtual_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.nota_fiscal_venda OWNER TO postgres;

CREATE TABLE public.nota_item_produto (
    id bigint NOT NULL,
    quantidade double precision NOT NULL,
    nota_fiscal_compra_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.nota_item_produto OWNER TO postgres;

CREATE TABLE public.pessoa_fisica (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    cpf character varying(255) NOT NULL,
    data_nascimento date,
    tipo_pessoa character varying(255),
    empresa_id bigint NOT NULL
);

ALTER TABLE public.pessoa_fisica OWNER TO postgres;

CREATE TABLE public.pessoa_juridica (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    categoria character varying(255),
    cnpj character varying(255) NOT NULL,
    inscricao_estadual character varying(255),
    inscricao_municipal character varying(255),
    nome_fantasia character varying(255) NOT NULL,
    razao_social character varying(255) NOT NULL,
    tipo_pessoa character varying(255),
    empresa_id bigint
);

ALTER TABLE public.pessoa_juridica OWNER TO postgres;


CREATE TABLE public.produto (
    id bigint NOT NULL,
    alerta_estoque boolean,
    altura double precision NOT NULL,
    ativo boolean NOT NULL,
    descricao text NOT NULL,
    largura double precision NOT NULL,
    link_youtube character varying(255),
    nome character varying(255) NOT NULL,
    peso double precision NOT NULL,
    profundidade double precision NOT NULL,
    qtd_click_produto integer,
    qtd_estoque integer NOT NULL,
    qtd_estoque_minimo integer,
    tipo_unidade character varying(255) NOT NULL,
    valor_venda numeric(19,2) NOT NULL,
    nota_item_produto_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.produto OWNER TO postgres;

CREATE SEQUENCE public.seq_acesso
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_acesso OWNER TO postgres;

CREATE SEQUENCE public.seq_avaliacao_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_avaliacao_produto OWNER TO postgres;

CREATE SEQUENCE public.seq_categoria_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_categoria_produto OWNER TO postgres;

CREATE SEQUENCE public.seq_conta_pagar
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_conta_pagar OWNER TO postgres;

CREATE SEQUENCE public.seq_conta_receber
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_conta_receber OWNER TO postgres;

CREATE SEQUENCE public.seq_cupom_desconto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_cupom_desconto OWNER TO postgres;

CREATE SEQUENCE public.seq_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_endereco OWNER TO postgres;

CREATE SEQUENCE public.seq_forma_pagamento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_forma_pagamento OWNER TO postgres;

CREATE SEQUENCE public.seq_imagem_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_imagem_produto OWNER TO postgres;

CREATE SEQUENCE public.seq_item_venda_loja
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_item_venda_loja OWNER TO postgres;

CREATE SEQUENCE public.seq_marca_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_marca_produto OWNER TO postgres;

CREATE SEQUENCE public.seq_nota_fiscal_compra
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_nota_fiscal_compra OWNER TO postgres;

CREATE SEQUENCE public.seq_nota_fiscal_venda
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_nota_fiscal_venda OWNER TO postgres;

CREATE SEQUENCE public.seq_nota_item_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_nota_item_produto OWNER TO postgres;

CREATE SEQUENCE public.seq_pessoa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_pessoa OWNER TO postgres;

CREATE SEQUENCE public.seq_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_produto OWNER TO postgres;

CREATE SEQUENCE public.seq_status_rastreio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_status_rastreio OWNER TO postgres;

CREATE SEQUENCE public.seq_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_usuario OWNER TO postgres;

CREATE SEQUENCE public.seq_venda_compra_loja_virtual
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_venda_compra_loja_virtual OWNER TO postgres;

CREATE TABLE public.status_rastreio (
    id bigint NOT NULL,
    centro_distribuicao character varying(255),
    cidade character varying(255),
    codigo character varying(255),
    estado character varying(255),
    status character varying(255),
    venda_compra_loja_virtual_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.status_rastreio OWNER TO postgres;

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    login character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    empresa_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    create_at date,
    update_at date
);

ALTER TABLE public.usuario OWNER TO postgres;

CREATE TABLE public.usuario_acesso (
    usuario_id bigint NOT NULL,
    acesso_id bigint NOT NULL
);

ALTER TABLE public.usuario_acesso OWNER TO postgres;

CREATE TABLE public.venda_compra_loja_virtual (
    id bigint NOT NULL,
    data_entrega date NOT NULL,
    data_venda date NOT NULL,
    dias_entrega integer NOT NULL,
    valor_desconto numeric(19,2),
    valor_frete numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    cupom_desconto_id bigint,
    endereco_cobranca_id bigint NOT NULL,
    endereco_entrega_id bigint NOT NULL,
    forma_pagamento_id bigint NOT NULL,
    nota_fiscal_venda_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

ALTER TABLE public.venda_compra_loja_virtual OWNER TO postgres;

SELECT pg_catalog.setval('public.seq_acesso', 1, false);

SELECT pg_catalog.setval('public.seq_avaliacao_produto', 1, false);

SELECT pg_catalog.setval('public.seq_categoria_produto', 1, false);

SELECT pg_catalog.setval('public.seq_conta_pagar', 1, false);

SELECT pg_catalog.setval('public.seq_conta_receber', 1, false);

SELECT pg_catalog.setval('public.seq_cupom_desconto', 1, false);

SELECT pg_catalog.setval('public.seq_endereco', 1, false);

SELECT pg_catalog.setval('public.seq_forma_pagamento', 1, false);

SELECT pg_catalog.setval('public.seq_imagem_produto', 1, false);

SELECT pg_catalog.setval('public.seq_item_venda_loja', 1, false);

SELECT pg_catalog.setval('public.seq_marca_produto', 1, false);

SELECT pg_catalog.setval('public.seq_nota_fiscal_compra', 1, false);

SELECT pg_catalog.setval('public.seq_nota_fiscal_venda', 1, false);

SELECT pg_catalog.setval('public.seq_nota_item_produto', 1, false);

SELECT pg_catalog.setval('public.seq_pessoa', 1, false);

SELECT pg_catalog.setval('public.seq_produto', 1, false);

SELECT pg_catalog.setval('public.seq_status_rastreio', 1, false);

SELECT pg_catalog.setval('public.seq_usuario', 1, false);

SELECT pg_catalog.setval('public.seq_venda_compra_loja_virtual', 1, false);

ALTER TABLE ONLY public.acesso
    ADD CONSTRAINT acesso_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT avaliacao_produto_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.categoria_produto
    ADD CONSTRAINT categoria_produto_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.conta_pagar
    ADD CONSTRAINT conta_pagar_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.conta_receber
    ADD CONSTRAINT conta_receber_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.cupom_desconto
    ADD CONSTRAINT cupom_desconto_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT imagem_produto_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT item_venda_loja_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT login_unique UNIQUE (login);

ALTER TABLE ONLY public.marca_produto
    ADD CONSTRAINT marca_produto_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT nota_fiscal_compra_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT nota_fiscal_venda_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_item_produto_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT pessoa_fisica_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT pessoa_juridica_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT status_rastreio_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT uk_3h78rtw3ei11cb43k77af5nhl UNIQUE (cnpj);

ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT uk_6f8dnvy9aakthuofgyrj66lyf UNIQUE (email);

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT uk_d70aayxv20yf3y8kofcx7fhbg UNIQUE (email);

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT uk_p3d8co8s4y5h7y18fpqco1wv6 UNIQUE (cpf);

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk_pm3f4m4fqv89oeeeac4tbe2f4 UNIQUE (login);

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT unique_acesso_user UNIQUE (usuario_id, acesso_id);

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT venda_compra_loja_virtual_pkey PRIMARY KEY (id);

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.avaliacao_produto FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.conta_receber FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.endereco FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.nota_fiscal_compra FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoafornecedordelete BEFORE DELETE ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoafornecedor();

CREATE TRIGGER validachavepessoafornecedorinsert BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoafornecedor();

CREATE TRIGGER validachavepessoafornecedorupdate BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoafornecedor();

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.avaliacao_produto FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.conta_receber FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.endereco FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.nota_fiscal_compra FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.avaliacao_produto FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.conta_receber FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.endereco FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.nota_fiscal_compra FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT acesso_fk FOREIGN KEY (acesso_id) REFERENCES public.acesso(id);

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT conta_pagar_fk FOREIGN KEY (conta_pagar_id) REFERENCES public.conta_pagar(id);

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT cupom_desconto_fk FOREIGN KEY (cupom_desconto_id) REFERENCES public.cupom_desconto(id);

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.conta_receber
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.conta_pagar
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.categoria_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.cupom_desconto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.marca_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT endereco_cobranca_fk FOREIGN KEY (endereco_cobranca_id) REFERENCES public.endereco(id);

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT endereco_entrega_fk FOREIGN KEY (endereco_entrega_id) REFERENCES public.endereco(id);

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT forma_pagamento_fk FOREIGN KEY (forma_pagamento_id) REFERENCES public.forma_pagamento(id);

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_fiscal_compra_fk FOREIGN KEY (nota_fiscal_compra_id) REFERENCES public.nota_fiscal_compra(id);

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT nota_fiscal_venda_fk FOREIGN KEY (nota_fiscal_venda_id) REFERENCES public.nota_fiscal_venda(id);

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT notaitemproduto_fk FOREIGN KEY (nota_item_produto_id) REFERENCES public.nota_item_produto(id);

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;