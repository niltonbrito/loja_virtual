-- ============================================================================
-- V3__06-08-2026-14-30-00.sql
-- Funções auxiliares para validar referências polimórficas de pessoa
--
-- Observação: estas funções somente serão executadas quando associadas a
-- triggers em tabelas que possuam pessoa_id ou pessoa_fornecedor_id.
-- ============================================================================

SET search_path TO public;

DROP FUNCTION IF EXISTS public.validachavepessoa();

CREATE FUNCTION public.validachavepessoa()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM public.pessoa_fisica
        WHERE id = NEW.pessoa_id
    )
    AND NOT EXISTS (
        SELECT 1
        FROM public.pessoa_juridica
        WHERE id = NEW.pessoa_id
    ) THEN
        RAISE EXCEPTION
            'Não foi encontrado o ID e PK da pessoa para realizar a associação do cadastro';
    END IF;

    RETURN NEW;
END;
$$;

DROP FUNCTION IF EXISTS public.validachavepessoafornecedor();

CREATE FUNCTION public.validachavepessoafornecedor()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM public.pessoa_fisica
        WHERE id = NEW.pessoa_fornecedor_id
    )
    AND NOT EXISTS (
        SELECT 1
        FROM public.pessoa_juridica
        WHERE id = NEW.pessoa_fornecedor_id
    ) THEN
        RAISE EXCEPTION
            'Não foi encontrado o ID e PK da pessoa para realizar a associação do cadastro';
    END IF;

    RETURN NEW;
END;
$$;