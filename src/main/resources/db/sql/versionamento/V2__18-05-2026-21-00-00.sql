-- Popula a tabela acesso com os valores do Enum RoleUser
-- Insere apenas se ainda não existir

INSERT INTO acesso (id, role_user) VALUES (1, 'ROLE_USER')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO acesso (id, role_user) VALUES (2,'ROLE_FINANCEIRO')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO acesso (id, role_user) VALUES (3, 'ROLE_GERENTE')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO acesso (id, role_user) VALUES (4, 'ROLE_ESTOQUE')
    ON CONFLICT (id) DO NOTHING;
    
INSERT INTO acesso (id, role_user) VALUES (5, 'ROLE_ADMIN')
    ON CONFLICT (id) DO NOTHING;
    
INSERT INTO acesso (id, role_user) VALUES (6, 'ROLE_SUPER_ADMIN')
    ON CONFLICT (id) DO NOTHING;

