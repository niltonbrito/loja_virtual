-- ============================================
-- 1. Cria EMPRESA padrão (PessoaJuridica)
-- ============================================
INSERT INTO pessoa_juridica (
       id, nome, email, telefone, tipo_pessoa,
       cnpj, inscricao_estadual, inscricao_municipal,
       nome_fantasia, razao_social, categoria, empresa_id
)
SELECT nextval('seq_pessoa'),
       'Empresa Administrativa',
       'empresa@sistema.com',
       '71999990000',
       'JURIDICA',
       '00000000000000',
       'ISENTO',
       'ISENTO',
       'Empresa Administrativa',
       'Empresa Administrativa LTDA',
       'ADMIN',
       NULL
WHERE NOT EXISTS (
    SELECT 1 FROM pessoa_juridica WHERE email = 'empresa@sistema.com'
);

-- ============================================
-- 2. Cria PESSOA FÍSICA padrão para o usuário admin
-- ============================================
INSERT INTO pessoa_fisica (
       id, nome, email, telefone, tipo_pessoa,
       cpf, data_nascimento, empresa_id
)
SELECT nextval('seq_pessoa'),
       'Administrador do Sistema',
       'admin@sistema.com',
       '71999990001',
       'FISICA',
       '00000000000',
       '1990-01-01',
       (SELECT id FROM pessoa_juridica WHERE email = 'empresa@sistema.com')
WHERE NOT EXISTS (
    SELECT 1 FROM pessoa_fisica WHERE email = 'admin@sistema.com'
);

-- ============================================
-- 3. Cria o usuário ADMIN
-- ============================================
INSERT INTO usuario (
       id, login, senha, pessoa_id, empresa_id, create_at, update_at
)
SELECT nextval('seq_usuario'),
       'admin',
       '$2a$10$g1pAwZ4omnpfWKzr8bsmKOspS76zYX5QnX4mKAally2V2Tb.gktWW',
       (SELECT id FROM pessoa_fisica WHERE email = 'admin@sistema.com'),
       (SELECT id FROM pessoa_juridica WHERE email = 'empresa@sistema.com'),
       now(),
       now()
WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE login = 'admin'
);

-- ============================================
-- 4. Associa o usuário ADMIN ao ROLE_SUPER_ADMIN
-- ============================================
INSERT INTO usuario_acesso (usuario_id, acesso_id)
SELECT u.id, a.id
FROM usuario u
JOIN acesso a ON a.role_user = 'ROLE_SUPER_ADMIN'
WHERE u.login = 'admin'
  AND NOT EXISTS (
        SELECT 1 FROM usuario_acesso 
        WHERE usuario_id = u.id 
          AND acesso_id = a.id
  );
