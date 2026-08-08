-- ============================================================================
-- V1__init.sql
-- Esquema inicial da Loja Virtual - PostgreSQL
--
-- Esta migration foi consolidada a partir do dump legado e deve ser executada
-- somente em banco vazio. Alterações posteriores devem ser feitas em V2, V3...
--
-- Removidos do dump original:
--   * comandos OWNER, SET, setval e metadados do pg_dump;
--   * funções sem triggers associados;
--   * constraints duplicadas e nomes gerados pelo Hibernate;
--   * carga extensa de palavra_proibida (mover para migration separada);
--   * credenciais e usuário SUPER_ADMIN (criados pelo bootstrap da aplicação).
-- ============================================================================

SET search_path TO public;

-- --------------------------------------------------------------------------
-- 1. SEQUENCES
-- --------------------------------------------------------------------------
CREATE SEQUENCE seq_acesso
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_avaliacao_produto
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_categoria_produto
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_compra_loja_virtual
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_conta_pagar
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_conta_receber
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_convite_colaborador
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_cupom_desconto
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_endereco
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_forma_pagamento
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_imagem_produto
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_item_venda_loja
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_marca_produto
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_nota_fiscal_compra
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_nota_fiscal_venda
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_nota_item_produto
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_palavra_proibida
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_pedido_compra
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_pedido_compra_item
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_pessoa
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_pre_cadastro_cliente
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_pre_cadastro_empresa
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_produto
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_produto_fornecedor
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_setor
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_solicitacao_compra
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_solicitacao_compra_item
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_status_rastreio
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_tabela_acesso_end_point
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_usuario
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE seq_venda_loja_virtual
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;

-- --------------------------------------------------------------------------
-- 2. TABLES
-- --------------------------------------------------------------------------
CREATE TABLE acesso (
    id bigint DEFAULT nextval('seq_acesso') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao varchar(255) NOT NULL,
    role_user varchar(255) NOT NULL
);

CREATE TABLE palavra_proibida (
    id bigint DEFAULT nextval('seq_palavra_proibida') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao varchar(255) NOT NULL
);

CREATE TABLE tabela_acesso_end_point (
    id bigint DEFAULT nextval('seq_tabela_acesso_end_point') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    nome_end_point varchar(255),
    qtd_acesso bigint NOT NULL
);

CREATE TABLE pessoa_juridica (
    id bigint DEFAULT nextval('seq_pessoa') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    email varchar(255) NOT NULL,
    nome varchar(255) NOT NULL,
    telefone varchar(255) NOT NULL,
    tipo_cadastro varchar(255),
    tipo_pessoa varchar(255),
    setor_id bigint,
    categoria varchar(255),
    cnpj varchar(255) NOT NULL,
    inscricao_estadual varchar(255),
    inscricao_municipal varchar(255),
    nome_fantasia varchar(255) NOT NULL,
    razao_social varchar(255) NOT NULL,
    empresa_id bigint,
    matriz_id bigint
);

CREATE TABLE setor (
    id bigint DEFAULT nextval('seq_setor') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    nome varchar(255) NOT NULL,
    empresa_id bigint NOT NULL
);

CREATE TABLE pessoa_fisica (
    id bigint DEFAULT nextval('seq_pessoa') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    email varchar(255) NOT NULL,
    nome varchar(255) NOT NULL,
    telefone varchar(255) NOT NULL,
    tipo_cadastro varchar(255),
    tipo_pessoa varchar(255),
    setor_id bigint,
    cpf varchar(255) NOT NULL,
    data_nascimento date,
    empresa_id bigint
);

CREATE TABLE usuario (
    id bigint DEFAULT nextval('seq_usuario') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    login varchar(180) NOT NULL,
    senha varchar(100) NOT NULL,
    status varchar(20) NOT NULL DEFAULT 'ATIVO',
    troca_senha_obrigatoria boolean NOT NULL DEFAULT FALSE,
    empresa_id bigint,
    pessoa_id bigint NOT NULL

);

CREATE TABLE usuario_acesso (
    usuario_id bigint NOT NULL,
    acesso_id bigint NOT NULL
);

CREATE TABLE pre_cadastro_empresa (
    id bigint DEFAULT nextval('seq_pre_cadastro_empresa') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cnpj varchar(14),
    concluido_em timestamp,
    cpf_responsavel varchar(11),
    email varchar(180) NOT NULL,
    email_confirmado_em timestamp,
    inscricao_estadual varchar(30),
    nome_fantasia varchar(200),
    nome_responsavel varchar(150) NOT NULL,
    razao_social varchar(200),
    senha_hash varchar(100),
    status varchar(40) NOT NULL,
    telefone_empresa varchar(20),
    telefone_responsavel varchar(20),
    token_confirmacao_hash varchar(128),
    token_expira_em timestamp
);

CREATE TABLE pre_cadastro_cliente (
    id bigint DEFAULT nextval('seq_pre_cadastro_cliente') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    confirmado_em timestamp,
    cpf varchar(255),
    email varchar(255) NOT NULL,
    nome varchar(255) NOT NULL,
    senha_hash varchar(255) NOT NULL,
    status varchar(255),
    telefone varchar(255),
    token_confirmacao_hash varchar(255) NOT NULL,
    token_expira_em timestamp,
    empresa_id bigint NOT NULL
);

CREATE TABLE convite_colaborador (
    id bigint DEFAULT nextval('seq_convite_colaborador') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    aceito_em timestamp,
    cancelado_em timestamp,
    email varchar(180) NOT NULL,
    expira_em timestamp NOT NULL,
    nome varchar(150) NOT NULL,
    perfil varchar(30) NOT NULL,
    status varchar(20) NOT NULL,
    token_hash varchar(64) NOT NULL,
    criado_por_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);

CREATE TABLE categoria_produto (
    id bigint DEFAULT nextval('seq_categoria_produto') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao varchar(255) NOT NULL,
    empresa_id bigint NOT NULL
);

CREATE TABLE marca_produto (
    id bigint DEFAULT nextval('seq_marca_produto') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao varchar(255) NOT NULL,
    empresa_id bigint NOT NULL
);

CREATE TABLE produto (
    id bigint DEFAULT nextval('seq_produto') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    alerta_estoque boolean NOT NULL,
    altura numeric(19,2) NOT NULL,
    ativo boolean NOT NULL,
    descricao text NOT NULL,
    largura numeric(19,2) NOT NULL,
    link_youtube varchar(255),
    nome varchar(255) NOT NULL,
    peso numeric(19,2) NOT NULL,
    profundidade numeric(19,2) NOT NULL,
    qtd_click_produto integer,
    qtd_estoque numeric(19,2) NOT NULL,
    qtd_estoque_minimo numeric(19,2) NOT NULL,
    tipo_unidade_medida varchar(255) NOT NULL,
    valor_venda numeric(19,2) NOT NULL,
    categoria_produto_id bigint NOT NULL,
    empresa_id bigint NOT NULL,
    marca_produto_id bigint NOT NULL
);

CREATE TABLE imagem_produto (
    id bigint DEFAULT nextval('seq_imagem_produto') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    imagem_miniatura bytea NOT NULL,
    imagem_original bytea NOT NULL,
    empresa_id bigint NOT NULL,
    produto_id bigint NOT NULL
);

CREATE TABLE produto_fornecedor (
    id bigint DEFAULT nextval('seq_produto_fornecedor') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    codigo_produto_fornecedor varchar(255) NOT NULL,
    empresa_id bigint NOT NULL,
    fornecedor_id bigint NOT NULL,
    produto_id bigint NOT NULL
);

CREATE TABLE cupom_desconto (
    id bigint DEFAULT nextval('seq_cupom_desconto') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    codigo varchar(255) NOT NULL,
    data_validade date NOT NULL,
    limite_uso_total integer,
    quantidade_usado integer,
    valor_porcentagem_desconto numeric(19,2),
    valor_real_desconto numeric(19,2),
    empresa_id bigint NOT NULL
);

CREATE TABLE cupom_categoria (
    cupom_id bigint NOT NULL,
    categoria_id bigint NOT NULL
);

CREATE TABLE cupom_marca (
    cupom_id bigint NOT NULL,
    marca_id bigint NOT NULL
);

CREATE TABLE cupom_produto (
    cupom_id bigint NOT NULL,
    produto_id bigint NOT NULL
);

CREATE TABLE forma_pagamento (
    id bigint DEFAULT nextval('seq_forma_pagamento') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao varchar(255) NOT NULL,
    quantidade_maxima_parcelas integer,
    tipo_pagamento varchar(255) NOT NULL,
    valor_minimo_parcela integer,
    empresa_id bigint NOT NULL
);

CREATE TABLE endereco (
    id bigint DEFAULT nextval('seq_endereco') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    bairro varchar(255) NOT NULL,
    cep varchar(255) NOT NULL,
    cidade varchar(255) NOT NULL,
    complemento varchar(255),
    numero varchar(255) NOT NULL,
    rua varchar(255) NOT NULL,
    tipo_endereco varchar(255) NOT NULL,
    uf varchar(255) NOT NULL,
    pessoa_id bigint NOT NULL
);

CREATE TABLE conta_pagar (
    id bigint DEFAULT nextval('seq_conta_pagar') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_pagamento  date NOT NULL,
    data_vencimento  date NOT NULL,
    descricao varchar(255) NOT NULL,
    status varchar(255) NOT NULL,
    valor_desconto numeric(19,2),
    valor_total numeric(19,2) NOT NULL,
    empresa_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    pessoa_fornecedor_id bigint NOT NULL
);

CREATE TABLE conta_receber (
    id bigint DEFAULT nextval('seq_conta_receber') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_pagamento  date NOT NULL,
    data_vencimento date NOT NULL,
    descricao varchar(255) NOT NULL,
    status varchar(255) NOT NULL,
    valor_desconto numeric(19,2),
    valor_total numeric(19,2) NOT NULL,
    empresa_id bigint NOT NULL,
    pessoa_id bigint NOT NULL
);

CREATE TABLE solicitacao_compra (
    id bigint DEFAULT nextval('seq_solicitacao_compra') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_solicitacao timestamp without time zone NOT NULL,
    descricao varchar(255) NOT NULL,
    status varchar(255) NOT NULL,
    empresa_id bigint NOT NULL,
    setor_solicitante_id bigint,
    solicitante_id bigint NOT NULL
);

CREATE TABLE solicitacao_compra_item (
    id bigint DEFAULT nextval('seq_solicitacao_compra_item') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quantidade double precision NOT NULL,
    empresa_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    solicitacao_compra_id bigint NOT NULL
);

CREATE TABLE pedido_compra (
    id bigint DEFAULT nextval('seq_pedido_compra') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_pedido timestamp without time zone NOT NULL,
    numero_pedido varchar(255) NOT NULL,
    status varchar(255) NOT NULL,
    empresa_id bigint NOT NULL,
    fornecedor_id bigint NOT NULL,
    solicitacao_compra_id bigint
);

CREATE TABLE pedido_compra_item (
    id bigint DEFAULT nextval('seq_pedido_compra_item') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quantidade numeric(19,2) NOT NULL,
    valor_unitario_negociado numeric(19,2) NOT NULL,
    empresa_id bigint NOT NULL,
    pedido_compra_id bigint NOT NULL,
    produto_id bigint NOT NULL
);

CREATE TABLE nota_fiscal_compra (
    id bigint DEFAULT nextval('seq_nota_fiscal_compra') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_compra timestamp without time zone NOT NULL,
    descricao_observacao varchar(255),
    numero_nota varchar(255) NOT NULL,
    serie_nota varchar(255) NOT NULL,
    valor_desconto numeric(19,2),
    valor_icms numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    conta_pagar_id bigint,
    empresa_id bigint NOT NULL,
    pedido_compra_id bigint,
    pessoa_id bigint NOT NULL
);

CREATE TABLE nota_item_produto (
    id bigint DEFAULT nextval('seq_nota_item_produto') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quantidade numeric(19,2) NOT NULL,
    valor_unitario_custo numeric(19,2) NOT NULL,
    empresa_id bigint NOT NULL,
    nota_fiscal_compra_id bigint NOT NULL,
    produto_id bigint NOT NULL
);

CREATE TABLE compra_loja_virtual (
    id bigint DEFAULT nextval('seq_compra_loja_virtual') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_compra timestamp without time zone NOT NULL,
    data_entrega date NOT NULL,
    dias_entrega integer NOT NULL,
    valor_desconto numeric(19,2),
    valor_frete numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    cupom_desconto_id bigint,
    empresa_id bigint NOT NULL,
    endereco_cobranca_id bigint NOT NULL,
    endereco_entrega_id bigint NOT NULL,
    forma_pagamento_id bigint NOT NULL,
    nota_fiscal_compra_id bigint NOT NULL,
    pessoa_id bigint NOT NULL
);

CREATE TABLE venda_loja_virtual (
    id bigint DEFAULT nextval('seq_venda_loja_virtual') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_entrega date NOT NULL,
    data_venda timestamp without time zone NOT NULL,
    dias_entrega integer NOT NULL,
    numero_pedido varchar(255) NOT NULL,
    valor_desconto numeric(19,2),
    valor_frete numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    cupom_desconto_id bigint,
    empresa_id bigint NOT NULL,
    endereco_cobranca_id bigint NOT NULL,
    endereco_entrega_id bigint NOT NULL,
    forma_pagamento_id bigint NOT NULL,
    nota_fiscal_venda_id bigint,
    pessoa_id bigint NOT NULL
);

CREATE TABLE item_venda_loja (
    id bigint DEFAULT nextval('seq_item_venda_loja') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quantidade double precision NOT NULL,
    empresa_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    venda_loja_virtual_id bigint NOT NULL
);

CREATE TABLE nota_fiscal_venda (
    id bigint DEFAULT nextval('seq_nota_fiscal_venda') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao varchar(255) NOT NULL,
    numero_nota varchar(255) NOT NULL,
    pdf text NOT NULL,
    serie_nota varchar(255) NOT NULL,
    tipo varchar(255) NOT NULL,
    valor_desconto numeric(19,2),
    valor_icms numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    xml text NOT NULL,
    empresa_id bigint NOT NULL,
    venda_loja_virtual_id bigint NOT NULL
);

CREATE TABLE status_rastreio (
    id bigint DEFAULT nextval('seq_status_rastreio') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    centro_distribuicao varchar(255),
    cidade varchar(255),
    codigo varchar(255),
    estado varchar(255),
    status varchar(255),
    compra_loja_virtual_id bigint,
    empresa_id bigint NOT NULL,
    venda_loja_virtual_id bigint
);

CREATE TABLE avaliacao_produto (
    id bigint DEFAULT nextval('seq_avaliacao_produto') NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao varchar(255) NOT NULL,
    nota integer NOT NULL,
    empresa_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    produto_id bigint NOT NULL
);

-- --------------------------------------------------------------------------
-- 3. VÍNCULO DAS SEQUENCES ÀS COLUNAS
-- --------------------------------------------------------------------------
-- seq_pessoa é propositalmente compartilhada por pessoa_fisica e pessoa_juridica
-- e, por isso, não recebe OWNED BY.
ALTER SEQUENCE seq_acesso OWNED BY acesso.id;
ALTER SEQUENCE seq_avaliacao_produto OWNED BY avaliacao_produto.id;
ALTER SEQUENCE seq_categoria_produto OWNED BY categoria_produto.id;
ALTER SEQUENCE seq_compra_loja_virtual OWNED BY compra_loja_virtual.id;
ALTER SEQUENCE seq_conta_pagar OWNED BY conta_pagar.id;
ALTER SEQUENCE seq_conta_receber OWNED BY conta_receber.id;
ALTER SEQUENCE seq_convite_colaborador OWNED BY convite_colaborador.id;
ALTER SEQUENCE seq_cupom_desconto OWNED BY cupom_desconto.id;
ALTER SEQUENCE seq_endereco OWNED BY endereco.id;
ALTER SEQUENCE seq_forma_pagamento OWNED BY forma_pagamento.id;
ALTER SEQUENCE seq_imagem_produto OWNED BY imagem_produto.id;
ALTER SEQUENCE seq_item_venda_loja OWNED BY item_venda_loja.id;
ALTER SEQUENCE seq_marca_produto OWNED BY marca_produto.id;
ALTER SEQUENCE seq_nota_fiscal_compra OWNED BY nota_fiscal_compra.id;
ALTER SEQUENCE seq_nota_fiscal_venda OWNED BY nota_fiscal_venda.id;
ALTER SEQUENCE seq_nota_item_produto OWNED BY nota_item_produto.id;
ALTER SEQUENCE seq_palavra_proibida OWNED BY palavra_proibida.id;
ALTER SEQUENCE seq_pedido_compra OWNED BY pedido_compra.id;
ALTER SEQUENCE seq_pedido_compra_item OWNED BY pedido_compra_item.id;
ALTER SEQUENCE seq_pre_cadastro_cliente OWNED BY pre_cadastro_cliente.id;
ALTER SEQUENCE seq_pre_cadastro_empresa OWNED BY pre_cadastro_empresa.id;
ALTER SEQUENCE seq_produto OWNED BY produto.id;
ALTER SEQUENCE seq_produto_fornecedor OWNED BY produto_fornecedor.id;
ALTER SEQUENCE seq_setor OWNED BY setor.id;
ALTER SEQUENCE seq_solicitacao_compra OWNED BY solicitacao_compra.id;
ALTER SEQUENCE seq_solicitacao_compra_item OWNED BY solicitacao_compra_item.id;
ALTER SEQUENCE seq_status_rastreio OWNED BY status_rastreio.id;
ALTER SEQUENCE seq_tabela_acesso_end_point OWNED BY tabela_acesso_end_point.id;
ALTER SEQUENCE seq_usuario OWNED BY usuario.id;
ALTER SEQUENCE seq_venda_loja_virtual OWNED BY venda_loja_virtual.id;

-- --------------------------------------------------------------------------
-- 4. PRIMARY KEYS E UNIQUE CONSTRAINTS
-- --------------------------------------------------------------------------
ALTER TABLE acesso
    ADD CONSTRAINT pk_acesso PRIMARY KEY (id);
ALTER TABLE palavra_proibida
    ADD CONSTRAINT pk_palavra_proibida PRIMARY KEY (id);
ALTER TABLE tabela_acesso_end_point
    ADD CONSTRAINT pk_tabela_acesso_end_point PRIMARY KEY (id);
ALTER TABLE pessoa_juridica
    ADD CONSTRAINT pk_pessoa_juridica PRIMARY KEY (id);
ALTER TABLE setor
    ADD CONSTRAINT pk_setor PRIMARY KEY (id);
ALTER TABLE pessoa_fisica
    ADD CONSTRAINT pk_pessoa_fisica PRIMARY KEY (id);
ALTER TABLE usuario
    ADD CONSTRAINT pk_usuario PRIMARY KEY (id);
ALTER TABLE pre_cadastro_empresa
    ADD CONSTRAINT pk_pre_cadastro_empresa PRIMARY KEY (id);
ALTER TABLE pre_cadastro_cliente
    ADD CONSTRAINT pk_pre_cadastro_cliente PRIMARY KEY (id);
ALTER TABLE convite_colaborador
    ADD CONSTRAINT pk_convite_colaborador PRIMARY KEY (id);
ALTER TABLE categoria_produto
    ADD CONSTRAINT pk_categoria_produto PRIMARY KEY (id);
ALTER TABLE marca_produto
    ADD CONSTRAINT pk_marca_produto PRIMARY KEY (id);
ALTER TABLE produto
    ADD CONSTRAINT pk_produto PRIMARY KEY (id);
ALTER TABLE imagem_produto
    ADD CONSTRAINT pk_imagem_produto PRIMARY KEY (id);
ALTER TABLE produto_fornecedor
    ADD CONSTRAINT pk_produto_fornecedor PRIMARY KEY (id);
ALTER TABLE cupom_desconto
    ADD CONSTRAINT pk_cupom_desconto PRIMARY KEY (id);
ALTER TABLE forma_pagamento
    ADD CONSTRAINT pk_forma_pagamento PRIMARY KEY (id);
ALTER TABLE endereco
    ADD CONSTRAINT pk_endereco PRIMARY KEY (id);
ALTER TABLE conta_pagar
    ADD CONSTRAINT pk_conta_pagar PRIMARY KEY (id);
ALTER TABLE conta_receber
    ADD CONSTRAINT pk_conta_receber PRIMARY KEY (id);
ALTER TABLE solicitacao_compra
    ADD CONSTRAINT pk_solicitacao_compra PRIMARY KEY (id);
ALTER TABLE solicitacao_compra_item
    ADD CONSTRAINT pk_solicitacao_compra_item PRIMARY KEY (id);
ALTER TABLE pedido_compra
    ADD CONSTRAINT pk_pedido_compra PRIMARY KEY (id);
ALTER TABLE pedido_compra_item
    ADD CONSTRAINT pk_pedido_compra_item PRIMARY KEY (id);
ALTER TABLE nota_fiscal_compra
    ADD CONSTRAINT pk_nota_fiscal_compra PRIMARY KEY (id);
ALTER TABLE nota_item_produto
    ADD CONSTRAINT pk_nota_item_produto PRIMARY KEY (id);
ALTER TABLE compra_loja_virtual
    ADD CONSTRAINT pk_compra_loja_virtual PRIMARY KEY (id);
ALTER TABLE venda_loja_virtual
    ADD CONSTRAINT pk_venda_loja_virtual PRIMARY KEY (id);
ALTER TABLE item_venda_loja
    ADD CONSTRAINT pk_item_venda_loja PRIMARY KEY (id);
ALTER TABLE nota_fiscal_venda
    ADD CONSTRAINT pk_nota_fiscal_venda PRIMARY KEY (id);
ALTER TABLE status_rastreio
    ADD CONSTRAINT pk_status_rastreio PRIMARY KEY (id);
ALTER TABLE avaliacao_produto
    ADD CONSTRAINT pk_avaliacao_produto PRIMARY KEY (id);
ALTER TABLE cupom_categoria
    ADD CONSTRAINT pk_cupom_categoria PRIMARY KEY (cupom_id, categoria_id);
ALTER TABLE cupom_marca
    ADD CONSTRAINT pk_cupom_marca PRIMARY KEY (cupom_id, marca_id);
ALTER TABLE cupom_produto
    ADD CONSTRAINT pk_cupom_produto PRIMARY KEY (cupom_id, produto_id);
ALTER TABLE usuario_acesso
    ADD CONSTRAINT pk_usuario_acesso PRIMARY KEY (usuario_id, acesso_id);

ALTER TABLE acesso
    ADD CONSTRAINT uk_acesso_role_user UNIQUE (role_user);
ALTER TABLE palavra_proibida
    ADD CONSTRAINT uk_palavra_proibida_descricao UNIQUE (descricao);
ALTER TABLE tabela_acesso_end_point
    ADD CONSTRAINT uk_tabela_acesso_end_point_nome UNIQUE (nome_end_point);
ALTER TABLE pessoa_juridica
    ADD CONSTRAINT uk_pessoa_juridica_cnpj UNIQUE (cnpj);
ALTER TABLE pessoa_juridica
    ADD CONSTRAINT uk_pessoa_juridica_email UNIQUE (email);
ALTER TABLE pessoa_juridica
    ADD CONSTRAINT uk_pessoa_juridica_inscricao_estadual UNIQUE (inscricao_estadual);
ALTER TABLE pessoa_juridica
    ADD CONSTRAINT uk_pessoa_juridica_inscricao_municipal UNIQUE (inscricao_municipal);
ALTER TABLE pessoa_fisica
    ADD CONSTRAINT uk_pessoa_fisica_cpf UNIQUE (cpf);
ALTER TABLE pessoa_fisica
    ADD CONSTRAINT uk_pessoa_fisica_email UNIQUE (email);
ALTER TABLE usuario
    ADD CONSTRAINT uk_usuario_login UNIQUE (login);
ALTER TABLE usuario
    ADD CONSTRAINT uk_usuario_pessoa UNIQUE (pessoa_id);
ALTER TABLE pre_cadastro_empresa
    ADD CONSTRAINT uk_pre_cadastro_empresa_email UNIQUE (email);
ALTER TABLE pre_cadastro_empresa
    ADD CONSTRAINT uk_pre_cadastro_empresa_cnpj UNIQUE (cnpj);
ALTER TABLE pre_cadastro_empresa
    ADD CONSTRAINT uk_pre_cadastro_empresa_cpf UNIQUE (cpf_responsavel);
ALTER TABLE pre_cadastro_empresa
    ADD CONSTRAINT uk_pre_cadastro_empresa_token UNIQUE (token_confirmacao_hash);
ALTER TABLE pre_cadastro_cliente
    ADD CONSTRAINT uk_pre_cadastro_cliente_empresa_email UNIQUE (empresa_id, email);
ALTER TABLE pre_cadastro_cliente
    ADD CONSTRAINT uk_pre_cadastro_cliente_empresa_cpf UNIQUE (empresa_id, cpf);
ALTER TABLE pre_cadastro_cliente
    ADD CONSTRAINT uk_pre_cadastro_cliente_token UNIQUE (token_confirmacao_hash);
ALTER TABLE convite_colaborador
    ADD CONSTRAINT uk_convite_colaborador_token UNIQUE (token_hash);
ALTER TABLE categoria_produto
    ADD CONSTRAINT uk_categoria_produto_empresa_descricao UNIQUE (empresa_id, descricao);
ALTER TABLE marca_produto
    ADD CONSTRAINT uk_marca_produto_empresa_descricao UNIQUE (empresa_id, descricao);
ALTER TABLE setor
    ADD CONSTRAINT uk_setor_empresa_nome UNIQUE (empresa_id, nome);
ALTER TABLE produto
    ADD CONSTRAINT uk_produto_empresa_nome UNIQUE (empresa_id, nome);
ALTER TABLE produto_fornecedor
    ADD CONSTRAINT uk_produto_fornecedor_produto_fornecedor UNIQUE (produto_id, fornecedor_id);
ALTER TABLE produto_fornecedor
    ADD CONSTRAINT uk_produto_fornecedor_codigo UNIQUE (fornecedor_id, codigo_produto_fornecedor);
ALTER TABLE cupom_desconto
    ADD CONSTRAINT uk_cupom_desconto_empresa_codigo UNIQUE (empresa_id, codigo);
ALTER TABLE venda_loja_virtual
    ADD CONSTRAINT uk_venda_loja_virtual_numero_pedido UNIQUE (numero_pedido);

-- --------------------------------------------------------------------------
-- 5. CHECK CONSTRAINTS
-- --------------------------------------------------------------------------
ALTER TABLE acesso
    ADD CONSTRAINT ck_acesso_role_user
    CHECK (role_user IN (
        'ROLE_USER',
        'ROLE_CLIENTE',
        'ROLE_FINANCEIRO',
        'ROLE_ESTOQUE',
        'ROLE_GERENTE',
        'ROLE_ADMIN',
        'ROLE_SUPER_ADMIN'
    ));
ALTER TABLE usuario
    ADD CONSTRAINT ck_usuario_status
    CHECK (status IN ('ATIVO', 'BLOQUEADO'));

ALTER TABLE pre_cadastro_empresa
    ADD CONSTRAINT ck_pre_cadastro_empresa_status
    CHECK (status IN (
        'EMAIL_PENDENTE',
        'DADOS_EMPRESA_PENDENTES',
        'PRONTO_PARA_FINALIZAR',
        'CONCLUIDO',
        'EXPIRADO',
        'CANCELADO'
    ));
ALTER TABLE convite_colaborador
    ADD CONSTRAINT ck_convite_colaborador_status
    CHECK (status IN ('PENDENTE', 'ACEITO', 'EXPIRADO', 'CANCELADO'));

-- Referências polimórficas de pessoa não recebem FK convencional porque
-- pessoa_fisica e pessoa_juridica compartilham a sequence seq_pessoa.

-- --------------------------------------------------------------------------
-- 6. FOREIGN KEYS
-- --------------------------------------------------------------------------
ALTER TABLE produto
    ADD CONSTRAINT fk_produto_categoria_produto_id
    FOREIGN KEY (categoria_produto_id) REFERENCES categoria_produto (id);
ALTER TABLE nota_fiscal_compra
    ADD CONSTRAINT fk_nota_fiscal_compra_conta_pagar_id
    FOREIGN KEY (conta_pagar_id) REFERENCES conta_pagar (id);
ALTER TABLE compra_loja_virtual
    ADD CONSTRAINT fk_compra_loja_virtual_cupom_desconto_id
    FOREIGN KEY (cupom_desconto_id) REFERENCES cupom_desconto (id);
ALTER TABLE venda_loja_virtual
    ADD CONSTRAINT fk_venda_loja_virtual_cupom_desconto_id
    FOREIGN KEY (cupom_desconto_id) REFERENCES cupom_desconto (id);
ALTER TABLE avaliacao_produto
    ADD CONSTRAINT fk_avaliacao_produto_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE categoria_produto
    ADD CONSTRAINT fk_categoria_produto_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE compra_loja_virtual
    ADD CONSTRAINT fk_compra_loja_virtual_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE conta_pagar
    ADD CONSTRAINT fk_conta_pagar_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE conta_receber
    ADD CONSTRAINT fk_conta_receber_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE cupom_desconto
    ADD CONSTRAINT fk_cupom_desconto_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE forma_pagamento
    ADD CONSTRAINT fk_forma_pagamento_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE imagem_produto
    ADD CONSTRAINT fk_imagem_produto_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE item_venda_loja
    ADD CONSTRAINT fk_item_venda_loja_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE marca_produto
    ADD CONSTRAINT fk_marca_produto_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE nota_fiscal_compra
    ADD CONSTRAINT fk_nota_fiscal_compra_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE nota_fiscal_venda
    ADD CONSTRAINT fk_nota_fiscal_venda_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE nota_item_produto
    ADD CONSTRAINT fk_nota_item_produto_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE pedido_compra
    ADD CONSTRAINT fk_pedido_compra_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE pedido_compra_item
    ADD CONSTRAINT fk_pedido_compra_item_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE pessoa_juridica
    ADD CONSTRAINT fk_pessoa_juridica_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE produto
    ADD CONSTRAINT fk_produto_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE solicitacao_compra
    ADD CONSTRAINT fk_solicitacao_compra_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE solicitacao_compra_item
    ADD CONSTRAINT fk_solicitacao_compra_item_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE status_rastreio
    ADD CONSTRAINT fk_status_rastreio_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE venda_loja_virtual
    ADD CONSTRAINT fk_venda_loja_virtual_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE compra_loja_virtual
    ADD CONSTRAINT fk_compra_loja_virtual_endereco_cobranca_id
    FOREIGN KEY (endereco_cobranca_id) REFERENCES endereco (id);
ALTER TABLE venda_loja_virtual
    ADD CONSTRAINT fk_venda_loja_virtual_endereco_cobranca_id
    FOREIGN KEY (endereco_cobranca_id) REFERENCES endereco (id);
ALTER TABLE compra_loja_virtual
    ADD CONSTRAINT fk_compra_loja_virtual_endereco_entrega_id
    FOREIGN KEY (endereco_entrega_id) REFERENCES endereco (id);
ALTER TABLE venda_loja_virtual
    ADD CONSTRAINT fk_venda_loja_virtual_endereco_entrega_id
    FOREIGN KEY (endereco_entrega_id) REFERENCES endereco (id);
ALTER TABLE cupom_categoria
    ADD CONSTRAINT fk_cupom_categoria_cupom_id
    FOREIGN KEY (cupom_id) REFERENCES cupom_desconto (id) ON DELETE CASCADE;
ALTER TABLE cupom_categoria
    ADD CONSTRAINT fk_cupom_categoria_categoria_id
    FOREIGN KEY (categoria_id) REFERENCES categoria_produto (id) ON DELETE CASCADE;
ALTER TABLE cupom_marca
    ADD CONSTRAINT fk_cupom_marca_marca_id
    FOREIGN KEY (marca_id) REFERENCES marca_produto (id) ON DELETE CASCADE;
ALTER TABLE setor
    ADD CONSTRAINT fk_setor_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE cupom_marca
    ADD CONSTRAINT fk_cupom_marca_cupom_id
    FOREIGN KEY (cupom_id) REFERENCES cupom_desconto (id) ON DELETE CASCADE;
ALTER TABLE produto_fornecedor
    ADD CONSTRAINT fk_produto_fornecedor_fornecedor_id
    FOREIGN KEY (fornecedor_id) REFERENCES pessoa_juridica (id);
ALTER TABLE convite_colaborador
    ADD CONSTRAINT fk_convite_colaborador_criado_por_id
    FOREIGN KEY (criado_por_id) REFERENCES usuario (id);
ALTER TABLE convite_colaborador
    ADD CONSTRAINT fk_convite_colaborador_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE pessoa_fisica
    ADD CONSTRAINT fk_pessoa_fisica_setor_id
    FOREIGN KEY (setor_id) REFERENCES setor (id);
ALTER TABLE pessoa_juridica
    ADD CONSTRAINT fk_pessoa_juridica_setor_id
    FOREIGN KEY (setor_id) REFERENCES setor (id);
ALTER TABLE usuario_acesso
    ADD CONSTRAINT fk_usuario_acesso_acesso_id
    FOREIGN KEY (acesso_id) REFERENCES acesso (id) ON DELETE CASCADE;
ALTER TABLE usuario_acesso
    ADD CONSTRAINT fk_usuario_acesso_usuario_id
    FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE;
ALTER TABLE usuario
    ADD CONSTRAINT fk_usuario_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE cupom_produto
    ADD CONSTRAINT fk_cupom_produto_cupom_id
    FOREIGN KEY (cupom_id) REFERENCES cupom_desconto (id) ON DELETE CASCADE;
ALTER TABLE cupom_produto
    ADD CONSTRAINT fk_cupom_produto_produto_id
    FOREIGN KEY (produto_id) REFERENCES produto (id) ON DELETE CASCADE;
ALTER TABLE solicitacao_compra
    ADD CONSTRAINT fk_solicitacao_compra_setor_solicitante_id
    FOREIGN KEY (setor_solicitante_id) REFERENCES setor (id);
ALTER TABLE produto_fornecedor
    ADD CONSTRAINT fk_produto_fornecedor_produto_id
    FOREIGN KEY (produto_id) REFERENCES produto (id);
ALTER TABLE produto_fornecedor
    ADD CONSTRAINT fk_produto_fornecedor_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE compra_loja_virtual
    ADD CONSTRAINT fk_compra_loja_virtual_forma_pagamento_id
    FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id);
ALTER TABLE venda_loja_virtual
    ADD CONSTRAINT fk_venda_loja_virtual_forma_pagamento_id
    FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id);
ALTER TABLE pedido_compra
    ADD CONSTRAINT fk_pedido_compra_fornecedor_id
    FOREIGN KEY (fornecedor_id) REFERENCES pessoa_juridica (id);
ALTER TABLE produto
    ADD CONSTRAINT fk_produto_marca_produto_id
    FOREIGN KEY (marca_produto_id) REFERENCES marca_produto (id);
ALTER TABLE pessoa_juridica
    ADD CONSTRAINT fk_pessoa_juridica_matriz_id
    FOREIGN KEY (matriz_id) REFERENCES pessoa_juridica (id);
ALTER TABLE compra_loja_virtual
    ADD CONSTRAINT fk_compra_loja_virtual_nota_fiscal_compra_id
    FOREIGN KEY (nota_fiscal_compra_id) REFERENCES nota_fiscal_compra (id);
ALTER TABLE nota_item_produto
    ADD CONSTRAINT fk_nota_item_produto_nota_fiscal_compra_id
    FOREIGN KEY (nota_fiscal_compra_id) REFERENCES nota_fiscal_compra (id);
ALTER TABLE venda_loja_virtual
    ADD CONSTRAINT fk_venda_loja_virtual_nota_fiscal_venda_id
    FOREIGN KEY (nota_fiscal_venda_id) REFERENCES nota_fiscal_venda (id);
ALTER TABLE nota_fiscal_compra
    ADD CONSTRAINT fk_nota_fiscal_compra_pedido_compra_id
    FOREIGN KEY (pedido_compra_id) REFERENCES pedido_compra (id);
ALTER TABLE pedido_compra_item
    ADD CONSTRAINT fk_pedido_compra_item_pedido_compra_id
    FOREIGN KEY (pedido_compra_id) REFERENCES pedido_compra (id);
ALTER TABLE pessoa_fisica
    ADD CONSTRAINT fk_pessoa_fisica_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE avaliacao_produto
    ADD CONSTRAINT fk_avaliacao_produto_pessoa_id
    FOREIGN KEY (pessoa_id) REFERENCES pessoa_fisica (id);
ALTER TABLE compra_loja_virtual
    ADD CONSTRAINT fk_compra_loja_virtual_pessoa_id
    FOREIGN KEY (pessoa_id) REFERENCES pessoa_fisica (id);
ALTER TABLE conta_pagar
    ADD CONSTRAINT fk_conta_pagar_pessoa_id
    FOREIGN KEY (pessoa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE venda_loja_virtual
    ADD CONSTRAINT fk_venda_loja_virtual_pessoa_id
    FOREIGN KEY (pessoa_id) REFERENCES pessoa_fisica (id);
ALTER TABLE pre_cadastro_cliente
    ADD CONSTRAINT fk_pre_cadastro_cliente_empresa_id
    FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (id);
ALTER TABLE avaliacao_produto
    ADD CONSTRAINT fk_avaliacao_produto_produto_id
    FOREIGN KEY (produto_id) REFERENCES produto (id);
ALTER TABLE imagem_produto
    ADD CONSTRAINT fk_imagem_produto_produto_id
    FOREIGN KEY (produto_id) REFERENCES produto (id);
ALTER TABLE item_venda_loja
    ADD CONSTRAINT fk_item_venda_loja_produto_id
    FOREIGN KEY (produto_id) REFERENCES produto (id);
ALTER TABLE nota_item_produto
    ADD CONSTRAINT fk_nota_item_produto_produto_id
    FOREIGN KEY (produto_id) REFERENCES produto (id);
ALTER TABLE pedido_compra_item
    ADD CONSTRAINT fk_pedido_compra_item_produto_id
    FOREIGN KEY (produto_id) REFERENCES produto (id);
ALTER TABLE solicitacao_compra_item
    ADD CONSTRAINT fk_solicitacao_compra_item_produto_id
    FOREIGN KEY (produto_id) REFERENCES produto (id);
ALTER TABLE pedido_compra
    ADD CONSTRAINT fk_pedido_compra_solicitacao_compra_id
    FOREIGN KEY (solicitacao_compra_id) REFERENCES solicitacao_compra (id);
ALTER TABLE solicitacao_compra_item
    ADD CONSTRAINT fk_solicitacao_compra_item_solicitacao_compra_id
    FOREIGN KEY (solicitacao_compra_id) REFERENCES solicitacao_compra (id);
ALTER TABLE status_rastreio
    ADD CONSTRAINT fk_status_rastreio_compra_loja_virtual_id
    FOREIGN KEY (compra_loja_virtual_id) REFERENCES compra_loja_virtual (id);
ALTER TABLE status_rastreio
    ADD CONSTRAINT fk_status_rastreio_venda_loja_virtual_id
    FOREIGN KEY (venda_loja_virtual_id) REFERENCES venda_loja_virtual (id);
ALTER TABLE item_venda_loja
    ADD CONSTRAINT fk_item_venda_loja_venda_loja_virtual_id
    FOREIGN KEY (venda_loja_virtual_id) REFERENCES venda_loja_virtual (id);
ALTER TABLE nota_fiscal_venda
    ADD CONSTRAINT fk_nota_fiscal_venda_venda_loja_virtual_id
    FOREIGN KEY (venda_loja_virtual_id) REFERENCES venda_loja_virtual (id);

-- --------------------------------------------------------------------------
-- 7. INDEXES
-- --------------------------------------------------------------------------
CREATE INDEX idx_pessoa_juridica_empresa ON pessoa_juridica (empresa_id);
CREATE INDEX idx_pessoa_juridica_matriz ON pessoa_juridica (matriz_id);
CREATE INDEX idx_pessoa_juridica_setor ON pessoa_juridica (setor_id);
CREATE INDEX idx_pessoa_fisica_empresa ON pessoa_fisica (empresa_id);
CREATE INDEX idx_pessoa_fisica_setor ON pessoa_fisica (setor_id);
CREATE INDEX idx_usuario_empresa ON usuario (empresa_id);
CREATE INDEX idx_usuario_acesso_acesso ON usuario_acesso (acesso_id);
CREATE INDEX idx_pre_cadastro_empresa_status ON pre_cadastro_empresa (status);
CREATE INDEX idx_pre_cadastro_empresa_token_expira ON pre_cadastro_empresa (token_expira_em);
CREATE INDEX idx_pre_cadastro_empresa_created_at ON pre_cadastro_empresa (created_at);
CREATE INDEX idx_pre_cadastro_cliente_empresa ON pre_cadastro_cliente (empresa_id);
CREATE INDEX idx_pre_cadastro_cliente_status ON pre_cadastro_cliente (status);
CREATE INDEX idx_pre_cadastro_cliente_token_expira ON pre_cadastro_cliente (token_expira_em);
CREATE INDEX idx_convite_colaborador_empresa_email ON convite_colaborador (empresa_id, email);
CREATE INDEX idx_convite_colaborador_status ON convite_colaborador (status);
CREATE INDEX idx_convite_colaborador_expira_em ON convite_colaborador (expira_em);
CREATE INDEX idx_endereco_pessoa ON endereco (pessoa_id);
CREATE INDEX idx_conta_receber_pessoa ON conta_receber (pessoa_id);
CREATE INDEX idx_conta_pagar_fornecedor ON conta_pagar (pessoa_fornecedor_id);
CREATE INDEX idx_nota_fiscal_compra_pessoa ON nota_fiscal_compra (pessoa_id);
CREATE INDEX idx_solicitacao_compra_solicitante ON solicitacao_compra (solicitante_id);
CREATE INDEX idx_produto_categoria_produto_id ON produto (categoria_produto_id);
CREATE INDEX idx_nota_fiscal_compra_conta_pagar_id ON nota_fiscal_compra (conta_pagar_id);
CREATE INDEX idx_compra_loja_virtual_cupom_desconto_id ON compra_loja_virtual (cupom_desconto_id);
CREATE INDEX idx_venda_loja_virtual_cupom_desconto_id ON venda_loja_virtual (cupom_desconto_id);
CREATE INDEX idx_avaliacao_produto_empresa_id ON avaliacao_produto (empresa_id);
CREATE INDEX idx_compra_loja_virtual_empresa_id ON compra_loja_virtual (empresa_id);
CREATE INDEX idx_conta_pagar_empresa_id ON conta_pagar (empresa_id);
CREATE INDEX idx_conta_receber_empresa_id ON conta_receber (empresa_id);
CREATE INDEX idx_forma_pagamento_empresa_id ON forma_pagamento (empresa_id);
CREATE INDEX idx_imagem_produto_empresa_id ON imagem_produto (empresa_id);
CREATE INDEX idx_item_venda_loja_empresa_id ON item_venda_loja (empresa_id);
CREATE INDEX idx_nota_fiscal_compra_empresa_id ON nota_fiscal_compra (empresa_id);
CREATE INDEX idx_nota_fiscal_venda_empresa_id ON nota_fiscal_venda (empresa_id);
CREATE INDEX idx_nota_item_produto_empresa_id ON nota_item_produto (empresa_id);
CREATE INDEX idx_pedido_compra_empresa_id ON pedido_compra (empresa_id);
CREATE INDEX idx_pedido_compra_item_empresa_id ON pedido_compra_item (empresa_id);
CREATE INDEX idx_solicitacao_compra_empresa_id ON solicitacao_compra (empresa_id);
CREATE INDEX idx_solicitacao_compra_item_empresa_id ON solicitacao_compra_item (empresa_id);
CREATE INDEX idx_status_rastreio_empresa_id ON status_rastreio (empresa_id);
CREATE INDEX idx_venda_loja_virtual_empresa_id ON venda_loja_virtual (empresa_id);
CREATE INDEX idx_compra_loja_virtual_endereco_cobranca_id ON compra_loja_virtual (endereco_cobranca_id);
CREATE INDEX idx_venda_loja_virtual_endereco_cobranca_id ON venda_loja_virtual (endereco_cobranca_id);
CREATE INDEX idx_compra_loja_virtual_endereco_entrega_id ON compra_loja_virtual (endereco_entrega_id);
CREATE INDEX idx_venda_loja_virtual_endereco_entrega_id ON venda_loja_virtual (endereco_entrega_id);
CREATE INDEX idx_cupom_categoria_categoria_id ON cupom_categoria (categoria_id);
CREATE INDEX idx_cupom_marca_marca_id ON cupom_marca (marca_id);
CREATE INDEX idx_convite_colaborador_criado_por_id ON convite_colaborador (criado_por_id);
CREATE INDEX idx_convite_colaborador_empresa_id ON convite_colaborador (empresa_id);
CREATE INDEX idx_cupom_produto_produto_id ON cupom_produto (produto_id);
CREATE INDEX idx_solicitacao_compra_setor_solicitante_id ON solicitacao_compra (setor_solicitante_id);
CREATE INDEX idx_produto_fornecedor_empresa_id ON produto_fornecedor (empresa_id);
CREATE INDEX idx_compra_loja_virtual_forma_pagamento_id ON compra_loja_virtual (forma_pagamento_id);
CREATE INDEX idx_venda_loja_virtual_forma_pagamento_id ON venda_loja_virtual (forma_pagamento_id);
CREATE INDEX idx_pedido_compra_fornecedor_id ON pedido_compra (fornecedor_id);
CREATE INDEX idx_produto_marca_produto_id ON produto (marca_produto_id);
CREATE INDEX idx_compra_loja_virtual_nota_fiscal_compra_id ON compra_loja_virtual (nota_fiscal_compra_id);
CREATE INDEX idx_nota_item_produto_nota_fiscal_compra_id ON nota_item_produto (nota_fiscal_compra_id);
CREATE INDEX idx_venda_loja_virtual_nota_fiscal_venda_id ON venda_loja_virtual (nota_fiscal_venda_id);
CREATE INDEX idx_nota_fiscal_compra_pedido_compra_id ON nota_fiscal_compra (pedido_compra_id);
CREATE INDEX idx_pedido_compra_item_pedido_compra_id ON pedido_compra_item (pedido_compra_id);
CREATE INDEX idx_avaliacao_produto_pessoa_id ON avaliacao_produto (pessoa_id);
CREATE INDEX idx_compra_loja_virtual_pessoa_id ON compra_loja_virtual (pessoa_id);
CREATE INDEX idx_conta_pagar_pessoa_id ON conta_pagar (pessoa_id);
CREATE INDEX idx_venda_loja_virtual_pessoa_id ON venda_loja_virtual (pessoa_id);
CREATE INDEX idx_avaliacao_produto_produto_id ON avaliacao_produto (produto_id);
CREATE INDEX idx_imagem_produto_produto_id ON imagem_produto (produto_id);
CREATE INDEX idx_item_venda_loja_produto_id ON item_venda_loja (produto_id);
CREATE INDEX idx_nota_item_produto_produto_id ON nota_item_produto (produto_id);
CREATE INDEX idx_pedido_compra_item_produto_id ON pedido_compra_item (produto_id);
CREATE INDEX idx_solicitacao_compra_item_produto_id ON solicitacao_compra_item (produto_id);
CREATE INDEX idx_pedido_compra_solicitacao_compra_id ON pedido_compra (solicitacao_compra_id);
CREATE INDEX idx_solicitacao_compra_item_solicitacao_compra_id ON solicitacao_compra_item (solicitacao_compra_id);
CREATE INDEX idx_status_rastreio_compra_loja_virtual_id ON status_rastreio (compra_loja_virtual_id);
CREATE INDEX idx_status_rastreio_venda_loja_virtual_id ON status_rastreio (venda_loja_virtual_id);
CREATE INDEX idx_item_venda_loja_venda_loja_virtual_id ON item_venda_loja (venda_loja_virtual_id);
CREATE INDEX idx_nota_fiscal_venda_venda_loja_virtual_id ON nota_fiscal_venda (venda_loja_virtual_id);

-- --------------------------------------------------------------------------
-- 8. DADOS ESTRUTURAIS INICIAIS
-- --------------------------------------------------------------------------
INSERT INTO acesso (role_user, descricao)
VALUES
    ('ROLE_USER', 'Usuário padrão da empresa'),
    ('ROLE_CLIENTE', 'Cliente da loja virtual'),
    ('ROLE_FINANCEIRO', 'Usuário do setor financeiro'),
    ('ROLE_ESTOQUE', 'Usuário responsável pelo estoque'),
    ('ROLE_GERENTE', 'Gerente da empresa'),
    ('ROLE_ADMIN', 'Administrador da empresa'),
    ('ROLE_SUPER_ADMIN', 'Administrador geral da plataforma')
ON CONFLICT (role_user) DO UPDATE
SET descricao = EXCLUDED.descricao;

-- O SUPER_ADMIN não é inserido por SQL. Ele é criado pelo bootstrap idempotente
-- usando SUPER_ADMIN_EMAIL, SUPER_ADMIN_PASSWORD e demais variáveis externas.
