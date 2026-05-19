-- Popula a tabela acesso com os valores do Enum RoleUser
-- Insere apenas se ainda não existir

INSERT INTO acesso (role_user)
SELECT 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM acesso WHERE role_user = 'ROLE_USER');

INSERT INTO acesso (role_user)
SELECT 'ROLE_FINANCEIRO'
WHERE NOT EXISTS (SELECT 1 FROM acesso WHERE role_user = 'ROLE_FINANCEIRO');

INSERT INTO acesso (role_user)
SELECT 'ROLE_ESTOQUE'
WHERE NOT EXISTS (SELECT 1 FROM acesso WHERE role_user = 'ROLE_ESTOQUE');

INSERT INTO acesso (role_user)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM acesso WHERE role_user = 'ROLE_ADMIN');

INSERT INTO acesso (role_user)
SELECT 'ROLE_SUPER_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM acesso WHERE role_user = 'ROLE_SUPER_ADMIN');
