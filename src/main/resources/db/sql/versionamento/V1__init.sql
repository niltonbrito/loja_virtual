--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.25
-- Dumped by pg_dump version 9.5.25

-- Started on 2026-05-18 21:03:39

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
DROP INDEX public.flyway_schema_history_s_idx;
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
ALTER TABLE ONLY public.flyway_schema_history DROP CONSTRAINT flyway_schema_history_pk;
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
DROP TABLE public.flyway_schema_history;
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
--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2391 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2393 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 234 (class 1255 OID 17047)
-- Name: validachavepessoa(); Type: FUNCTION; Schema: public; Owner: postgres
--

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

--
-- TOC entry 235 (class 1255 OID 17063)
-- Name: validachavepessoafornecedor(); Type: FUNCTION; Schema: public; Owner: postgres
--

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

--
-- TOC entry 200 (class 1259 OID 16731)
-- Name: acesso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.acesso (
    id bigint NOT NULL,
    role_user character varying(255) NOT NULL
);


ALTER TABLE public.acesso OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17116)
-- Name: avaliacao_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.avaliacao_produto (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    nota integer NOT NULL,
    pessoa_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);


ALTER TABLE public.avaliacao_produto OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 16741)
-- Name: categoria_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria_produto (
    id bigint NOT NULL,
    nome_descricao character varying(255) NOT NULL,
    empresa_id bigint NOT NULL
);


ALTER TABLE public.categoria_produto OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16746)
-- Name: conta_pagar; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 203 (class 1259 OID 16754)
-- Name: conta_receber; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 204 (class 1259 OID 16762)
-- Name: cupom_desconto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cupom_desconto (
    id bigint NOT NULL,
    codigo_descricao character varying(255) NOT NULL,
    data_validade date NOT NULL,
    valor_porcentagem_desconto numeric(19,2),
    valor_real_desconto numeric(19,2),
    empresa_id bigint NOT NULL
);


ALTER TABLE public.cupom_desconto OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16767)
-- Name: endereco; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 221 (class 1259 OID 50278)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16775)
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pagamento (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    tipo_pagamento character varying(255) NOT NULL,
    empresa_id bigint NOT NULL
);


ALTER TABLE public.forma_pagamento OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16819)
-- Name: imagem_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.imagem_produto (
    id bigint NOT NULL,
    imagem_miniatura text NOT NULL,
    imagem_original text NOT NULL,
    produto_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);


ALTER TABLE public.imagem_produto OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16827)
-- Name: item_venda_loja; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item_venda_loja (
    id bigint NOT NULL,
    quantidade double precision NOT NULL,
    produto_id bigint NOT NULL,
    venda_compra_loja_virtual_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);


ALTER TABLE public.item_venda_loja OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16832)
-- Name: marca_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.marca_produto (
    id bigint NOT NULL,
    nome_descricao character varying(255) NOT NULL,
    empresa_id bigint NOT NULL
);


ALTER TABLE public.marca_produto OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16837)
-- Name: nota_fiscal_compra; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 211 (class 1259 OID 16845)
-- Name: nota_fiscal_venda; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 212 (class 1259 OID 16853)
-- Name: nota_item_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nota_item_produto (
    id bigint NOT NULL,
    quantidade double precision NOT NULL,
    nota_fiscal_compra_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);


ALTER TABLE public.nota_item_produto OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16904)
-- Name: pessoa_fisica; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 214 (class 1259 OID 16912)
-- Name: pessoa_juridica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa_juridica (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    categoria character varying(255),
    cnpj character varying(255) NOT NULL,
    nome_fantasia character varying(255) NOT NULL,
    razao_social character varying(255) NOT NULL,
    tipo_pessoa character varying(255),
    empresa_id bigint,
    inscricao_estadual character varying(255),
    inscricao_municipal character varying(255)
);


ALTER TABLE public.pessoa_juridica OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17083)
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 181 (class 1259 OID 16436)
-- Name: seq_acesso; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_acesso
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_acesso OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 16701)
-- Name: seq_avaliacao_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_avaliacao_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_avaliacao_produto OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 16438)
-- Name: seq_categoria_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_categoria_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_categoria_produto OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 16509)
-- Name: seq_conta_pagar; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_conta_pagar
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_conta_pagar OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16494)
-- Name: seq_conta_receber; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_conta_receber
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_conta_receber OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 16524)
-- Name: seq_cupom_desconto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_cupom_desconto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_cupom_desconto OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 16517)
-- Name: seq_endereco; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_endereco OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 16511)
-- Name: seq_forma_pagamento; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_forma_pagamento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_forma_pagamento OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 16557)
-- Name: seq_imagem_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_imagem_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_imagem_produto OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16684)
-- Name: seq_item_venda_loja; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_item_venda_loja
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_item_venda_loja OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 16440)
-- Name: seq_marca_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_marca_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_marca_produto OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 16559)
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_fiscal_compra
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_nota_fiscal_compra OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 16633)
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_fiscal_venda
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_nota_fiscal_venda OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 16561)
-- Name: seq_nota_item_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_item_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_nota_item_produto OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 16442)
-- Name: seq_pessoa; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_pessoa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_pessoa OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 16534)
-- Name: seq_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_produto OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 16591)
-- Name: seq_status_rastreio; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_status_rastreio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_status_rastreio OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16472)
-- Name: seq_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_usuario OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16613)
-- Name: seq_venda_compra_loja_virtual; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_venda_compra_loja_virtual
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_venda_compra_loja_virtual OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16961)
-- Name: status_rastreio; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 220 (class 1259 OID 41112)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    create_at date,
    login character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    empresa_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    update_at date
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16977)
-- Name: usuario_acesso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario_acesso (
    usuario_id bigint NOT NULL,
    acesso_id bigint NOT NULL
);


ALTER TABLE public.usuario_acesso OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16980)
-- Name: venda_compra_loja_virtual; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 2363 (class 0 OID 16731)
-- Dependencies: 200
-- Data for Name: acesso; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.acesso (id, role_user) VALUES (1, 'ROLE_USER');
INSERT INTO public.acesso (id, role_user) VALUES (2, 'ROLE_ADMIN');


--
-- TOC entry 2382 (class 0 OID 17116)
-- Dependencies: 219
-- Data for Name: avaliacao_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2364 (class 0 OID 16741)
-- Dependencies: 201
-- Data for Name: categoria_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2365 (class 0 OID 16746)
-- Dependencies: 202
-- Data for Name: conta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2366 (class 0 OID 16754)
-- Dependencies: 203
-- Data for Name: conta_receber; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2367 (class 0 OID 16762)
-- Dependencies: 204
-- Data for Name: cupom_desconto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2368 (class 0 OID 16767)
-- Dependencies: 205
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (3, 'Centro', '40000-123', 'Salvador', 'Sala 201', '150', 'Rua das Palmeiras', 'ENTREGA', 'BA', 21, 21);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (4, 'Barra', '40140-200', 'Salvador', NULL, '850', 'Avenida Oceânica', 'COBRANCA', 'BA', 21, 21);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (5, 'Centro', '40000-123', 'Salvador', 'Sala 201', '150', 'Rua das Palmeiras', 'ENTREGA', 'BA', 22, 22);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (6, 'Barra', '40140-200', 'Salvador', NULL, '850', 'Avenida Oceânica', 'COBRANCA', 'BA', 22, 22);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (9, 'Centro', '40000-123', 'Salvador', 'Sala 201', '150', 'Rua das Palmeiras', 'ENTREGA', 'BA', 24, 24);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (10, 'Barra', '40140-200', 'Salvador', NULL, '850', 'Avenida Oceânica', 'COBRANCA', 'BA', 24, 24);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (11, 'Centro', '40000-123', 'Salvador', 'Sala 201', '150', 'Rua das Palmeiras', 'ENTREGA', 'BA', 25, 25);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (12, 'Barra', '40140-200', 'Salvador', NULL, '850', 'Avenida Oceânica', 'COBRANCA', 'BA', 25, 25);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (13, 'Centro', '40000-123', 'Salvador', 'Sala 201', '150', 'Rua das Palmeiras', 'ENTREGA', 'BA', 26, 26);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (14, 'Barra', '40140-200', 'Salvador', NULL, '850', 'Avenida Oceânica', 'COBRANCA', 'BA', 26, 26);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (19, 'VAle dos Lagos', '41256255', 'Salvador', 'AP 02', '35', 'Rua do Ceu', 'ENTREGA', 'BA', 29, 29);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (20, 'VAle dos Lagos', '41256275', 'Salvador', 'AP 99', '95', 'Rua do Capim', 'COBRANCA', 'BA', 29, 29);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (23, 'VAle dos Lagos', '41256255', 'Salvador', 'AP 02', '35', 'Rua do Ceu', 'ENTREGA', 'BA', 31, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (24, 'VAle dos Lagos', '41256275', 'Salvador', 'AP 99', '95', 'Rua do Capim', 'COBRANCA', 'BA', 31, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (29, 'VAle dos Lagos', '41256255', 'Salvador', 'AP 02', '35', 'Rua do Ceu', 'ENTREGA', 'BA', 34, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (30, 'VAle dos Lagos', '41256275', 'Salvador', 'AP 99', '95', 'Rua do Capim', 'COBRANCA', 'BA', 34, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (35, 'VAle dos Lagos', '41256255', 'Salvador', 'AP 02', '35', 'Rua do Ceu', 'ENTREGA', 'BA', 37, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (36, 'VAle dos Lagos', '41256275', 'Salvador', 'AP 99', '95', 'Rua do Capim', 'COBRANCA', 'BA', 37, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (37, 'VAle dos Lagos', '41256255', 'Salvador', 'AP 02', '35', 'Rua do Ceu', 'ENTREGA', 'BA', 38, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (38, 'VAle dos Lagos', '41256275', 'Salvador', 'AP 99', '95', 'Rua do Capim', 'COBRANCA', 'BA', 38, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (39, 'VAle dos Lagos', '41256255', 'Salvador', 'AP 02', '35', 'Rua do Ceu', 'ENTREGA', 'BA', 39, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (40, 'VAle dos Lagos', '41256275', 'Salvador', 'AP 99', '95', 'Rua do Capim', 'COBRANCA', 'BA', 39, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (41, 'Pituba', '40000-500', 'Salvador', 'Apto 302', '120', 'Rua das Acácias', 'RESIDENCIAL', 'BA', 40, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (42, 'Caminho das Árvores', '41820-020', 'Salvador', 'Sala 1501', '800', 'Avenida Tancredo Neves', 'COMERCIAL', 'BA', 40, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (43, 'Centro', '40000-123', 'Salvador', 'Sala 201', '150', 'Rua das Palmeiras', 'ENTREGA', 'BA', 41, 41);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (44, 'Barra', '40140-200', 'Salvador', NULL, '850', 'Avenida Oceânica', 'COBRANCA', 'BA', 41, 41);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (45, 'Centro', '40000-123', 'Salvador', 'Sala 201', '150', 'Rua das Palmeiras', 'ENTREGA', 'BA', 42, 42);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (46, 'Barra', '40140-200', 'Salvador', NULL, '850', 'Avenida Oceânica', 'COBRANCA', 'BA', 42, 42);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (47, 'Pituba', '40000-500', 'Salvador', 'Apto 302', '120', 'Rua das Acácias', 'RESIDENCIAL', 'BA', 43, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (48, 'Caminho das Árvores', '41820-020', 'Salvador', 'Sala 1501', '800', 'Avenida Tancredo Neves', 'COMERCIAL', 'BA', 43, 31);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (53, 'Centro', '40000-123', 'Salvador', 'Sala 201', '150', 'Rua das Palmeiras', 'ENTREGA', 'BA', 46, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (54, 'Barra', '40140-200', 'Salvador', NULL, '850', 'Avenida Oceânica', 'COBRANCA', 'BA', 46, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (55, 'Pituba', '40000-500', 'Salvador', 'Apto 302', '120', 'Rua das Acácias', 'RESIDENCIAL', 'BA', 47, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (56, 'Caminho das Árvores', '41820-020', 'Salvador', 'Sala 1501', '800', 'Avenida Tancredo Neves', 'COMERCIAL', 'BA', 47, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (57, 'Pituba', '40000-500', 'Salvador', 'Apto 302', '120', 'Rua das Acácias', 'RESIDENCIAL', 'BA', 48, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (58, 'Caminho das Árvores', '41820-020', 'Salvador', 'Sala 1501', '800', 'Avenida Tancredo Neves', 'COMERCIAL', 'BA', 48, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (59, 'Pituba', '40000-500', 'Salvador', 'Apto 302', '120', 'Rua das Acácias', 'RESIDENCIAL', 'BA', 49, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (60, 'Caminho das Árvores', '41820-020', 'Salvador', 'Sala 1501', '800', 'Avenida Tancredo Neves', 'COMERCIAL', 'BA', 49, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (61, 'Pituba', '40000-500', 'Salvador', 'Apto 302', '120', 'Rua das Acácias', 'RESIDENCIAL', 'BA', 50, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (62, 'Caminho das Árvores', '41820-020', 'Salvador', 'Sala 1501', '800', 'Avenida Tancredo Neves', 'COMERCIAL', 'BA', 50, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (63, 'Pituba', '40000-500', 'Salvador', 'Apto 302', '120', 'Rua das Acácias', 'RESIDENCIAL', 'BA', 51, 46);
INSERT INTO public.endereco (id, bairro, cep, cidade, complemento, numero, rua, tipo_endereco, uf, pessoa_id, empresa_id) VALUES (64, 'Caminho das Árvores', '41820-020', 'Salvador', 'Sala 1501', '800', 'Avenida Tancredo Neves', 'COMERCIAL', 'BA', 51, 46);


--
-- TOC entry 2384 (class 0 OID 50278)
-- Dependencies: 221
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) VALUES (1, '1', '<< Flyway Baseline >>', 'BASELINE', '<< Flyway Baseline >>', NULL, 'null', '2026-05-18 21:01:21.74246', 0, true);


--
-- TOC entry 2369 (class 0 OID 16775)
-- Dependencies: 206
-- Data for Name: forma_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2370 (class 0 OID 16819)
-- Dependencies: 207
-- Data for Name: imagem_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2371 (class 0 OID 16827)
-- Dependencies: 208
-- Data for Name: item_venda_loja; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2372 (class 0 OID 16832)
-- Dependencies: 209
-- Data for Name: marca_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2373 (class 0 OID 16837)
-- Dependencies: 210
-- Data for Name: nota_fiscal_compra; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2374 (class 0 OID 16845)
-- Dependencies: 211
-- Data for Name: nota_fiscal_venda; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2375 (class 0 OID 16853)
-- Dependencies: 212
-- Data for Name: nota_item_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2376 (class 0 OID 16904)
-- Dependencies: 213
-- Data for Name: pessoa_fisica; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (34, 'nilton.brito@odfdfutlook.comm', 'Nilton Brito', '719920456500', '0236117351dfdfdf4', '2026-05-18', 'FISICA', 31);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (37, 'nilton.brito@outlook.cdsfsdfsdfomm', 'Nilton Brito', '719920456500', '02361173434343434343514', '2026-05-18', 'FISICA', 31);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (38, 'nilton.brito@outlook.comm', 'Nilton Brito', '719920456500', '02361173514', '2026-05-18', 'FISICA', 31);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (39, 'nilton.brito@outlooffk.com', 'Nilton Brito', '719920456500', '27928165031', '2026-05-18', 'FISICA', 31);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (40, 'nilton.brito@ddddoutlook.com', 'João da Silva', '(71) 98888-7777', '06561185048', '1985-10-07', 'FISICA', 31);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (43, 'nilton.brito@oudfdfdftlook.com', 'João da Silva', '(71) 98888-7777', 'dfdfdfdf', '1985-10-07', 'FISICA', 31);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (47, 'nilton.brito@outlook.com', 'João da Silva', '(71) 98888-7777', '46154033052', '1985-10-07', 'FISICA', 46);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (48, 'nilton.brito@out44look.com', 'João da Silva', '(71) 98888-7777', '44897569095', '1985-10-07', 'FISICA', 46);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (49, 'niltron.brito@out44look.com', 'João da Silva', '(71) 98888-7777', '04848765030', '1985-10-07', 'FISICA', 46);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (50, 'niltrofn.brito@out44look.com', 'João da Silva', '(71) 98888-7777', '69557773006', '1985-10-07', 'FISICA', 46);
INSERT INTO public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento, tipo_pessoa, empresa_id) VALUES (51, 'niltrofn.britoo4ut44look.com@c', 'Nilton', '(71) 98888-7777', '57213069047', '1985-10-07', 'FISICA', 46);


--
-- TOC entry 2377 (class 0 OID 16912)
-- Dependencies: 214
-- Data for Name: pessoa_juridica; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (11, 'nilton.brito@outlook.coghm', 'Nilton Brito', '719920456500', NULL, '1778798381571', 'Bandampla', 'BAndampla Sistemas', NULL, 11, '1778798381572', NULL);
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (14, 'nilton.brdi8to@outlook.com', 'Nilton Brito', '719920456500', NULL, '1778803174176', 'Bandampla', 'BAndampla Sistemas', NULL, 14, '1778803174176', '1778803174176');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (15, 'contato@comercialandrade.com.br', 'Comercial Andrade Ltda', '(71) 3456-7890', 'DISTRIBUIDORA', '12.345.678/0001-90', 'Andrade Distribuidora', 'Comercial Andrade Comércio e Serviços Ltda', 'JURIDICA', 15, '123456789', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (31, 'nilton.brito@rbdimagooem.com.br', 'Nilton Brito', '719920456500', NULL, '6491630600dfdf0175', 'Bandampla', 'BAndampla Sistemas', 'JURIDICA', 31, '1779111221048', '1779111221048');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (42, 'contato@xpto.com', 'Empresa XPTO LTDA', '7133334444', 'COMERCIO', '64.916.30776/0001-75', 'XPTO', 'XPTO COMÉRCIO E SERVIÇOS LTDA', 'JURIDICA', 42, '100886789', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (46, 'contato@xfpto.com', 'Empresa XPTO LTDA', '7133334444', 'COMERCIO', '64916306000175', 'XPTO', 'XPTO COMÉRCIO E SERVIÇOS LTDA', 'JURIDICA', 46, '10076', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (29, 'nilton.brito@rbdimagooedsfdsfdsfm.com.br', 'Nilton Brito', '719920456500', NULL, '64.916.306/000858581-75', 'Bandampla', 'BAndampla Sistemas', 'JURIDICA', 29, '1779109956691', '1779109956691');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (17, 'contato1@comercialandrade.com.br', 'Comercial Andrade Ltda SA', '(71) 3456-7890', 'DISTRIBUIDORA', '12.345.678/0001-91', 'Andrade Distribuidora', 'Comercial Andrade Comércio e Serviços Ltda', 'JURIDICA', 17, '12345678', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (19, 'faleconosco@comercialandrade.com.br', 'Comercial Andrade SPE', '(71) 3456-7890', 'DISTRIBUIDORA', '12.345.678/0003-91', 'Andrade Distribuidora', 'Comercial Andrade Comércio e Serviços Ltda', 'JURIDICA', 19, '12456789', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (21, 'contatoandrade@comercialandrade.com.br', 'Comercial Andrade Ltda', '(71) 3456-7890', 'DISTRIBUIDORA', '12.345.678/0051-90', 'Andrade Distribuidora', 'Comercial Andrade Comércio e Serviços Ltda', 'JURIDICA', 21, '1256789', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (25, 'nilton.brito@outlook.44', 'Comercial Andrade Ltda', '(71) 3456-7890', 'DISTRIBUIDORA', '12.345.678/00199-90', 'Andrade Distribuidora', 'Comercial Andrade Comércio e Serviços Ltda', 'JURIDICA', 25, '126789', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (24, 'nilton.brito@outlooggk.com', 'Comercial Andrade Ltda', '(71) 3456-7890', 'DISTRIBUIDORA', '12.345.678/0019-90', 'Andrade Distribuidora', 'Comercial Andrade Comércio e Serviços Ltda', 'JURIDICA', 24, '23456789', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (22, 'nilton.brito@outlook.coghghm', 'Comercial Andrade Ltda', '(71) 3456-7890', 'DISTRIBUIDORA', '12.345.678/008-90', 'Andrade Distribuidora', 'Comercial Andrade Comércio e Serviços Ltda', 'JURIDICA', 22, '3456789', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (18, 'contat@comercialandrade.com.br', 'Comercial Andrade SA', '(71) 3456-7890', 'DISTRIBUIDORA', '12.345.678/0002-91', 'Andrade Distribuidora', 'Comercial Andrade Comércio e Serviços Ltda', 'JURIDICA', 18, '123', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (26, 'nilton.brito@outlook.com', 'Comercial Andrade Ltda', '(71) 3456-7890', 'DISTRIBUIDORA', '12.345.678/00198-90', 'Andrade Distribuidora', 'Comercial Andrade Comércio e Serviços Ltda', 'JURIDICA', 26, '8868', '987654321');
INSERT INTO public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, nome_fantasia, razao_social, tipo_pessoa, empresa_id, inscricao_estadual, inscricao_municipal) VALUES (41, 'contato@xptooportrtp.com', 'Empresa XPTO LTDA', '7133334444', 'COMERCIO', '64.916.306opop/0001-75', 'XPTO', 'XPTO COMÉRCIO E SERVIÇOS LTDA', 'JURIDICA', 41, '12388886789', '987654321');


--
-- TOC entry 2381 (class 0 OID 17083)
-- Dependencies: 218
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2394 (class 0 OID 0)
-- Dependencies: 181
-- Name: seq_acesso; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_acesso', 159, true);


--
-- TOC entry 2395 (class 0 OID 0)
-- Dependencies: 199
-- Name: seq_avaliacao_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_avaliacao_produto', 1, false);


--
-- TOC entry 2396 (class 0 OID 0)
-- Dependencies: 182
-- Name: seq_categoria_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_categoria_produto', 1, false);


--
-- TOC entry 2397 (class 0 OID 0)
-- Dependencies: 187
-- Name: seq_conta_pagar; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_conta_pagar', 1, false);


--
-- TOC entry 2398 (class 0 OID 0)
-- Dependencies: 186
-- Name: seq_conta_receber; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_conta_receber', 1, false);


--
-- TOC entry 2399 (class 0 OID 0)
-- Dependencies: 190
-- Name: seq_cupom_desconto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_cupom_desconto', 1, false);


--
-- TOC entry 2400 (class 0 OID 0)
-- Dependencies: 189
-- Name: seq_endereco; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_endereco', 64, true);


--
-- TOC entry 2401 (class 0 OID 0)
-- Dependencies: 188
-- Name: seq_forma_pagamento; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_forma_pagamento', 1, false);


--
-- TOC entry 2402 (class 0 OID 0)
-- Dependencies: 192
-- Name: seq_imagem_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_imagem_produto', 1, false);


--
-- TOC entry 2403 (class 0 OID 0)
-- Dependencies: 198
-- Name: seq_item_venda_loja; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_item_venda_loja', 1, false);


--
-- TOC entry 2404 (class 0 OID 0)
-- Dependencies: 183
-- Name: seq_marca_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_marca_produto', 1, false);


--
-- TOC entry 2405 (class 0 OID 0)
-- Dependencies: 193
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_compra', 1, false);


--
-- TOC entry 2406 (class 0 OID 0)
-- Dependencies: 197
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_venda', 1, false);


--
-- TOC entry 2407 (class 0 OID 0)
-- Dependencies: 194
-- Name: seq_nota_item_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_item_produto', 1, false);


--
-- TOC entry 2408 (class 0 OID 0)
-- Dependencies: 184
-- Name: seq_pessoa; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_pessoa', 51, true);


--
-- TOC entry 2409 (class 0 OID 0)
-- Dependencies: 191
-- Name: seq_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_produto', 1, false);


--
-- TOC entry 2410 (class 0 OID 0)
-- Dependencies: 195
-- Name: seq_status_rastreio; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_status_rastreio', 1, false);


--
-- TOC entry 2411 (class 0 OID 0)
-- Dependencies: 185
-- Name: seq_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_usuario', 30, true);


--
-- TOC entry 2412 (class 0 OID 0)
-- Dependencies: 196
-- Name: seq_venda_compra_loja_virtual; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_venda_compra_loja_virtual', 1, false);


--
-- TOC entry 2378 (class 0 OID 16961)
-- Dependencies: 215
-- Data for Name: status_rastreio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2383 (class 0 OID 41112)
-- Dependencies: 220
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (18, '2026-05-18', 'nilton.brito@rbdimagooem.com.br', '$2a$10$MDAhh3Xpz9Q.CUZkLA227.iviBRl9DUSvS6XG27NMoAm3ZMPOuYIK', 31, 31, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (21, '2026-05-18', 'nilton.brito@outlook.comm', '$2a$10$7X2brdO0Ige7vEpe.BkXBOeY.CyFviWBmhtklQATdSpQkhb/8Izga', 31, 38, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (16, '2026-05-16', 'nilton.brito@ouddddtlook.com', '$2a$10$lkpmPI..47cDTw3frrqgHekbD0wZ9euTLVW7FvOygJ/bcwrjol/B.', 26, 26, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (22, '2026-05-18', 'nilton.brito@outloffffok.com', '$2a$10$dUKO9utBN03A9SteDtzIlO1Pp.ORpvO9vN24RDVGOrM56t0.iET9S', 31, 39, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (23, '2026-05-18', 'nilton.brito@outlook.com', '$2a$10$s/IeTdAcAIh0nfhUhZ5HgOjj9q6CSM1GGoq7ztw7GwgCk2RcxfrOW', 31, 40, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (24, '2026-05-18', 'contatoololol@xpto.com', '$2a$10$sZ1cL.DPPhUMlzSnnlbDJ.KVyKo8qhaYT6J3CMB6NNBCvKoHJfPiG', 41, 41, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (25, '2026-05-18', 'contato@xpto.com', '$2a$10$CDeeV9kAHy1NJoKSdoUsaOvUm9n0MCY1FOLVMpN9baTB4nEAXUeBK', 42, 42, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (26, '2026-05-18', 'contato@xfpto.com', '$2a$10$.KPlDnDeFNp/bm4XF2mGIe/VMLYyhGXno8dEVl.9bRLcmJTWFSi56', 46, 46, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (27, '2026-05-18', 'nilton.brito@out44look.com', '$2a$10$S8R5bNMK2rxqN4pmhlqfjOE/1K0ONZggY/8ROHfl11sHu5wmvnw6u', 46, 48, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (28, '2026-05-18', 'niltron.brito@out44look.com', '$2a$10$mCNGRK4miAw4I9YrOPfzTe3JhNzslKu.ptd/hb3tkla/CIqyuRJMu', 46, 49, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (29, '2026-05-18', 'niltrofn.brito@out44look.com', '$2a$10$D0NMkSzquJt3FwmvGpfHpuITsyDUK1Epfb7R.orbisF/HI1LRmWCm', 46, 50, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (30, '2026-05-18', 'niltrofn.britoo4ut44look.com@c', '$2a$10$OnMt5e9cuWlXPxlsZRUoyeFxmbwB96qZdsSl/8qILp6.36G8Uah0a', 46, 51, '2026-05-18');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (6, '2026-05-14', 'nilton.brito@outlook.cfgom', '$2a$10$g1pAwZ4omnpfWKzr8bsmKOspS76zYX5QnX4mKAally2V2Tb.gktWW', 11, 11, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (7, '2026-05-14', 'nilton.brdi8to@outlook.com', '$2a$10$g1pAwZ4omnpfWKzr8bsmKOspS76zYX5QnX4mKAally2V2Tb.gktWW', 14, 14, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (8, '2026-05-14', 'contato@comercialandrade.com.br', '$2a$10$g1pAwZ4omnpfWKzr8bsmKOspS76zYX5QnX4mKAally2V2Tb.gktWW', 15, 15, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (9, '2026-05-14', 'contato1@comercialandrade.com.br', '$2a$10$AnaKqF8zDTGmfHnLCkiB4eyHMwPdf/cbrwK3KftLcIrkGNIOhm8jy', 17, 17, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (10, '2026-05-14', 'contat@comercialandrade.com.br', '$2a$10$F0OL2IagHtJypeRheXzaWejUznqEALNAiCLz0bJBzFcF5YHhtz1PC', 18, 18, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (11, '2026-05-14', 'faleconosco@comercialandrade.com.br', '$2a$10$XkGwcUAdS5XwLeLu4orlIePLuPkXJkCXCYcSqdRx3doXksX6PaImq', 19, 19, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (12, '2026-05-14', 'contatoandrade@comercialandrade.com.br', '$2a$10$K71vv.8zijoMc3VF6iu13u.aSAZsT4ZETXtKoQ2P7fgvu8gGN6c9C', 21, 21, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (13, '2026-05-16', 'nilton.brito@outlook.cghom', '$2a$10$BpCZFk0U8rchDPwQKlMjkOfSlQyrgT/esGpi..zhjfbDfd84UYCb2', 22, 22, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (14, '2026-05-16', 'nilton.brito@ggoutlook.com', '$2a$10$oxyxwNCYd8hsR3Erexc.oO./FjjnnN/kDURpeHENoE.p6XIDYGnLK', 24, 24, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (15, '2026-05-16', 'nilton.brito@ou44tlook.com', '$2a$10$0GJqI44fbH07Tl6oKeDoY.O7oXyXr1ppiO4MxdQCdIS7QA/a2SM8m', 25, 25, '2026-05-14');
INSERT INTO public.usuario (id, create_at, login, senha, empresa_id, pessoa_id, update_at) VALUES (17, '2026-05-18', 'nilton.brito@rbdimacvxcxdsfgooem.com.br', '$2a$10$VMn2D2KxHxk2pvdI2Q4Bj.rWwY56aLo0ofjDfWiMN1lIZK3NiE2Ii', 29, 29, '2026-05-18');


--
-- TOC entry 2379 (class 0 OID 16977)
-- Dependencies: 216
-- Data for Name: usuario_acesso; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (6, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (7, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (8, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (9, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (10, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (11, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (12, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (16, 2);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (17, 2);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (18, 2);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (21, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (22, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (23, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (24, 2);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (25, 2);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (26, 2);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (27, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (28, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (29, 1);
INSERT INTO public.usuario_acesso (usuario_id, acesso_id) VALUES (30, 1);


--
-- TOC entry 2380 (class 0 OID 16980)
-- Dependencies: 217
-- Data for Name: venda_compra_loja_virtual; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2118 (class 2606 OID 16735)
-- Name: acesso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.acesso
    ADD CONSTRAINT acesso_pkey PRIMARY KEY (id);


--
-- TOC entry 2164 (class 2606 OID 17120)
-- Name: avaliacao_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT avaliacao_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 2120 (class 2606 OID 16745)
-- Name: categoria_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria_produto
    ADD CONSTRAINT categoria_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 2122 (class 2606 OID 16753)
-- Name: conta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta_pagar
    ADD CONSTRAINT conta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 2124 (class 2606 OID 16761)
-- Name: conta_receber_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta_receber
    ADD CONSTRAINT conta_receber_pkey PRIMARY KEY (id);


--
-- TOC entry 2126 (class 2606 OID 16766)
-- Name: cupom_desconto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupom_desconto
    ADD CONSTRAINT cupom_desconto_pkey PRIMARY KEY (id);


--
-- TOC entry 2128 (class 2606 OID 16774)
-- Name: endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 2172 (class 2606 OID 50286)
-- Name: flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 2130 (class 2606 OID 16782)
-- Name: forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);


--
-- TOC entry 2132 (class 2606 OID 16826)
-- Name: imagem_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT imagem_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 2134 (class 2606 OID 16831)
-- Name: item_venda_loja_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT item_venda_loja_pkey PRIMARY KEY (id);


--
-- TOC entry 2166 (class 2606 OID 49310)
-- Name: login_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT login_unique UNIQUE (login);


--
-- TOC entry 2136 (class 2606 OID 16836)
-- Name: marca_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marca_produto
    ADD CONSTRAINT marca_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 2138 (class 2606 OID 16844)
-- Name: nota_fiscal_compra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT nota_fiscal_compra_pkey PRIMARY KEY (id);


--
-- TOC entry 2140 (class 2606 OID 16852)
-- Name: nota_fiscal_venda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT nota_fiscal_venda_pkey PRIMARY KEY (id);


--
-- TOC entry 2142 (class 2606 OID 16857)
-- Name: nota_item_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_item_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 2144 (class 2606 OID 16911)
-- Name: pessoa_fisica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT pessoa_fisica_pkey PRIMARY KEY (id);


--
-- TOC entry 2150 (class 2606 OID 16919)
-- Name: pessoa_juridica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT pessoa_juridica_pkey PRIMARY KEY (id);


--
-- TOC entry 2162 (class 2606 OID 17090)
-- Name: produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);


--
-- TOC entry 2156 (class 2606 OID 16968)
-- Name: status_rastreio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT status_rastreio_pkey PRIMARY KEY (id);


--
-- TOC entry 2152 (class 2606 OID 16935)
-- Name: uk_3h78rtw3ei11cb43k77af5nhl; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT uk_3h78rtw3ei11cb43k77af5nhl UNIQUE (cnpj);


--
-- TOC entry 2154 (class 2606 OID 16933)
-- Name: uk_6f8dnvy9aakthuofgyrj66lyf; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT uk_6f8dnvy9aakthuofgyrj66lyf UNIQUE (email);


--
-- TOC entry 2146 (class 2606 OID 16929)
-- Name: uk_d70aayxv20yf3y8kofcx7fhbg; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT uk_d70aayxv20yf3y8kofcx7fhbg UNIQUE (email);


--
-- TOC entry 2148 (class 2606 OID 16931)
-- Name: uk_p3d8co8s4y5h7y18fpqco1wv6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT uk_p3d8co8s4y5h7y18fpqco1wv6 UNIQUE (cpf);


--
-- TOC entry 2168 (class 2606 OID 41121)
-- Name: uk_pm3f4m4fqv89oeeeac4tbe2f4; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk_pm3f4m4fqv89oeeeac4tbe2f4 UNIQUE (login);


--
-- TOC entry 2158 (class 2606 OID 16990)
-- Name: unique_acesso_user; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT unique_acesso_user UNIQUE (usuario_id, acesso_id);


--
-- TOC entry 2170 (class 2606 OID 41119)
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2160 (class 2606 OID 16984)
-- Name: venda_compra_loja_virtual_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT venda_compra_loja_virtual_pkey PRIMARY KEY (id);


--
-- TOC entry 2173 (class 1259 OID 50287)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 2229 (class 2620 OID 17128)
-- Name: validachavepessoadelete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.avaliacao_produto FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2211 (class 2620 OID 17062)
-- Name: validachavepessoadelete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2217 (class 2620 OID 17070)
-- Name: validachavepessoadelete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.conta_receber FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2220 (class 2620 OID 17073)
-- Name: validachavepessoadelete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.endereco FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2223 (class 2620 OID 17076)
-- Name: validachavepessoadelete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.nota_fiscal_compra FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2226 (class 2620 OID 17082)
-- Name: validachavepessoadelete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoadelete BEFORE DELETE ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2214 (class 2620 OID 17067)
-- Name: validachavepessoafornecedordelete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoafornecedordelete BEFORE DELETE ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoafornecedor();


--
-- TOC entry 2212 (class 2620 OID 17065)
-- Name: validachavepessoafornecedorinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoafornecedorinsert BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoafornecedor();


--
-- TOC entry 2213 (class 2620 OID 17066)
-- Name: validachavepessoafornecedorupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoafornecedorupdate BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoafornecedor();


--
-- TOC entry 2227 (class 2620 OID 17126)
-- Name: validachavepessoainsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.avaliacao_produto FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2209 (class 2620 OID 17060)
-- Name: validachavepessoainsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2215 (class 2620 OID 17068)
-- Name: validachavepessoainsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.conta_receber FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2218 (class 2620 OID 17071)
-- Name: validachavepessoainsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.endereco FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2221 (class 2620 OID 17074)
-- Name: validachavepessoainsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.nota_fiscal_compra FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2224 (class 2620 OID 17080)
-- Name: validachavepessoainsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2228 (class 2620 OID 17127)
-- Name: validachavepessoaupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.avaliacao_produto FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2210 (class 2620 OID 17061)
-- Name: validachavepessoaupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2216 (class 2620 OID 17069)
-- Name: validachavepessoaupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.conta_receber FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2219 (class 2620 OID 17072)
-- Name: validachavepessoaupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.endereco FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2222 (class 2620 OID 17075)
-- Name: validachavepessoaupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.nota_fiscal_compra FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2225 (class 2620 OID 17081)
-- Name: validachavepessoaupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE PROCEDURE public.validachavepessoa();


--
-- TOC entry 2196 (class 2606 OID 17006)
-- Name: acesso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT acesso_fk FOREIGN KEY (acesso_id) REFERENCES public.acesso(id);


--
-- TOC entry 2187 (class 2606 OID 16873)
-- Name: conta_pagar_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT conta_pagar_fk FOREIGN KEY (conta_pagar_id) REFERENCES public.conta_pagar(id);


--
-- TOC entry 2198 (class 2606 OID 17016)
-- Name: cupom_desconto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT cupom_desconto_fk FOREIGN KEY (cupom_desconto_id) REFERENCES public.cupom_desconto(id);


--
-- TOC entry 2207 (class 2606 OID 41127)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2176 (class 2606 OID 41132)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta_receber
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2175 (class 2606 OID 41137)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.conta_pagar
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2174 (class 2606 OID 41142)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2177 (class 2606 OID 41147)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupom_desconto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2178 (class 2606 OID 41152)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2179 (class 2606 OID 41157)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2181 (class 2606 OID 41162)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2184 (class 2606 OID 41167)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2185 (class 2606 OID 41172)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marca_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2186 (class 2606 OID 41177)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2189 (class 2606 OID 41182)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2191 (class 2606 OID 41187)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2193 (class 2606 OID 41192)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2205 (class 2606 OID 41202)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2195 (class 2606 OID 41207)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2203 (class 2606 OID 41217)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2208 (class 2606 OID 49313)
-- Name: empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES public.pessoa_juridica(id);


--
-- TOC entry 2199 (class 2606 OID 17021)
-- Name: endereco_cobranca_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT endereco_cobranca_fk FOREIGN KEY (endereco_cobranca_id) REFERENCES public.endereco(id);


--
-- TOC entry 2200 (class 2606 OID 17026)
-- Name: endereco_entrega_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT endereco_entrega_fk FOREIGN KEY (endereco_entrega_id) REFERENCES public.endereco(id);


--
-- TOC entry 2201 (class 2606 OID 17031)
-- Name: forma_pagamento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT forma_pagamento_fk FOREIGN KEY (forma_pagamento_id) REFERENCES public.forma_pagamento(id);


--
-- TOC entry 2192 (class 2606 OID 16883)
-- Name: nota_fiscal_compra_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_fiscal_compra_fk FOREIGN KEY (nota_fiscal_compra_id) REFERENCES public.nota_fiscal_compra(id);


--
-- TOC entry 2202 (class 2606 OID 17036)
-- Name: nota_fiscal_venda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT nota_fiscal_venda_fk FOREIGN KEY (nota_fiscal_venda_id) REFERENCES public.nota_fiscal_venda(id);


--
-- TOC entry 2204 (class 2606 OID 17111)
-- Name: notaitemproduto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT notaitemproduto_fk FOREIGN KEY (nota_item_produto_id) REFERENCES public.nota_item_produto(id);


--
-- TOC entry 2180 (class 2606 OID 17096)
-- Name: produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 2183 (class 2606 OID 17101)
-- Name: produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 2190 (class 2606 OID 17106)
-- Name: produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 2206 (class 2606 OID 17121)
-- Name: produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 2197 (class 2606 OID 41122)
-- Name: usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 2182 (class 2606 OID 16991)
-- Name: venda_compra_loja_virtual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);


--
-- TOC entry 2188 (class 2606 OID 16996)
-- Name: venda_compra_loja_virtual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);


--
-- TOC entry 2194 (class 2606 OID 17001)
-- Name: venda_compra_loja_virtual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);


--
-- TOC entry 2392 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2026-05-18 21:03:39

--
-- PostgreSQL database dump complete
--

