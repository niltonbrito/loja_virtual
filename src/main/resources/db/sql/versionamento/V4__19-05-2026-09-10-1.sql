DROP FUNCTION IF EXISTS public.validachavepessoa();

CREATE FUNCTION public.validachavepessoa() RETURNS trigger
    LANGUAGE plpgsql
AS $$
DECLARE
    existe INTEGER;
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


DROP FUNCTION IF EXISTS public.validachavepessoafornecedor();

CREATE FUNCTION public.validachavepessoafornecedor() RETURNS trigger
    LANGUAGE plpgsql
AS $$
DECLARE
    existe INTEGER;
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
