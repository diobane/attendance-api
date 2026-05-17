-- =============================================================================
-- 1. EQUIPES E ENDEREÇOS
-- =============================================================================
INSERT INTO "TB_TEAM" (NAME, COLOR) VALUES
('Exploradores', '#FF5733'),
('Aventureiros', '#33FF57'),
('Guardiões', '#2E4053'),
('Mensageiros', '#F1C40F'),
('Pequenos Gigantes', '#8E44AD');

INSERT INTO "TB_ADDRESS" (CHURCH_NAME, COUNTRY, CITY, STREET, NUMBER, COMPLEMENT) VALUES
('Igreja Central', 'Brasil', 'São Paulo', 'Rua das Flores', '100', 'Próximo ao metrô'),
('Igreja do Bairro', 'Brasil', 'São Paulo', 'Av. Principal', '500', 'Salão de festas'),
('Igreja Esperança', 'Brasil', 'Curitiba', 'Rua XV', '10', 'Anexo B'),
('Sede Regional', 'Brasil', 'São Paulo', 'Rua das Palmeiras', '250', 'Prédio Administrativo');

-- =============================================================================
-- 2. CONTATOS
-- =============================================================================
INSERT INTO "TB_CONTACT" (PHONE_1, PHONE_2, EMAIL) VALUES
('(11) 99999-8888', '(11) 3333-2222', 'joao.silva@email.com'),     -- ID 1
('(11) 97777-6666', NULL, 'maria.oliveira@email.com'),            -- ID 2
('(41) 98888-0001', NULL, 'pai_teste1@email.com'),                -- ID 3
('(41) 98888-0002', '(41) 3030-4040', 'mae_teste1@email.com'),    -- ID 4
('(11) 97777-1111', NULL, 'tio_teste@email.com'),                 -- ID 5
('(11) 91111-2222', NULL, 'avofamilia@email.com'),                -- ID 6
('(11) 92222-3333', '(11) 3333-4444', 'responsavel_legal@email.com'); -- ID 7

-- =============================================================================
-- 3. FAMÍLIAS (ÂNCORAS) - Criando 7 núcleos familiares
-- =============================================================================
INSERT INTO "TB_FAMILY" (CREATED_AT) VALUES
(NOW()), (NOW()), (NOW()), (NOW()), (NOW()), (NOW()), (NOW());

-- =============================================================================
-- 4. EVENTOS
-- =============================================================================
INSERT INTO "TB_EVENT" (NAME, ADDRESS_ID, START_DATE, END_DATE) VALUES
('Acampamento de Verão', 1, '2026-01-10 08:00:00', '2026-01-15 18:00:00'),
('Escola Cristã de Férias', 3, '2026-07-05 13:00:00', '2026-07-10 17:00:00'),
('Congresso Infantil', 4, '2026-10-12 09:00:00', '2026-10-12 21:00:00');

-- =============================================================================
-- 5. RESPONSÁVEIS
-- =============================================================================
INSERT INTO "TB_RESPONSIBLE" (NAME, FAMILY_ID, CONTACT_ID) VALUES
('João Silva', 1, 1),           -- Família 1
('Maria Oliveira', 2, 2),       -- Família 2
('Roberto Almeida', 3, 3),      -- Família 3 (Pai)
('Luciana Almeida', 3, 4),      -- Família 3 (Mãe)
('Carlos Souza', 4, 5),         -- Família 4 (Tio)
('Severino Silva', 5, 6),       -- Família 5 (Avô)
('Patrícia Lima', 6, 7);        -- Família 6

-- =============================================================================
-- 6. CRIANÇAS
-- =============================================================================
INSERT INTO "TB_CHILD" (NAME, TEAM_ID, FAMILY_ID, RELIGION, FROM_EVENT_CHURCH, OBSERVATION) VALUES
-- Filhos do João (Família 1)
('Pedro Silva', 1, 1, 'Adventista', TRUE, 'Alérgico a amendoim'),
('Ana Silva', 2, 1, 'Adventista', TRUE, NULL),

-- Filho da Maria (Família 2)
('Lucas Oliveira', 1, 2, 'Católico', FALSE, 'Usa óculos'),

-- Filhos dos Almeida (Família 3) - 3 crianças em times diferentes
('Enzo Almeida', 3, 3, 'Adventista', TRUE, 'Liderança'),
('Valentina Almeida', 4, 3, 'Adventista', TRUE, NULL),
('Tiago Almeida', 5, 3, 'Adventista', TRUE, NULL),

-- Criança Visitante (Família 4)
('Bruno Souza', 3, 4, 'Católico', FALSE, 'Primeira vez'),

-- Neto do Severino (Família 5)
('Samuel Silva', 3, 5, 'Adventista', TRUE, 'Mora com o avô'),

-- Criança com restrição (Família 6)
('Beatriz Lima', 4, 6, 'Evangélica', FALSE, 'Intolerante à lactose');

-- =============================================================================
-- 7. REGISTROS DE PRESENÇA (ATTENDANCE)
-- =============================================================================
INSERT INTO "TB_ATTENDANCE" (CHILD_ID, TYPE) VALUES
(1, '1'), (2, '1'), (3, '1'), -- Check-ins iniciais
(4, '1'), (4, '2'),           -- Bruno Souza entrou e saiu
(5, '1'), (6, '1');           -- Samuel e Beatriz entraram