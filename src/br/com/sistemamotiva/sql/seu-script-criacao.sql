-- Limpeza prévia caso já existam (Ordem invertida por causa das Foreign Keys)
BEGIN EXECUTE IMMEDIATE 'DROP TABLE relatorios CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE intervencoes CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE trechos CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE equipes CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

-- Tabela de Equipes de Manutenção
CREATE TABLE equipes (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    identificador VARCHAR2(50) NOT NULL,
    quantidade_membros NUMBER NOT NULL
);

-- Tabela de Trechos de Rodovia
CREATE TABLE trechos (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR2(50) NOT NULL,
    km_inicial NUMBER(8, 2) NOT NULL,
    km_final NUMBER(8, 2) NOT NULL,
    nivel_vegetacao NUMBER(6, 2) NOT NULL,
    tipo VARCHAR2(30) NOT NULL,  -- 'AUTOESTRADA' ou 'ESTRADA_VICINAL'
    regiao_umida NUMBER(1) DEFAULT 0 NOT NULL,
    quantidade_faixas NUMBER DEFAULT 1,
    is_pavimentada NUMBER(1) DEFAULT 1
);

-- Tabela de Intervenções Operacionais (AGORA COM FOREIGN KEYS)
CREATE TABLE intervencoes (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo VARCHAR2(30) NOT NULL,  -- 'ROCADA' ou 'PULVERIZACAO'
    descricao VARCHAR2(255) NOT NULL,
    custo_estimado NUMBER(10, 2) NOT NULL,
    
    -- Colunas de Relacionamento
    id_trecho NUMBER,
    id_equipe NUMBER,
    
    -- Restrições (Integridade Referencial e regras de DELETE)
    CONSTRAINT fk_interv_trecho FOREIGN KEY (id_trecho) REFERENCES trechos(id) ON DELETE CASCADE,
    CONSTRAINT fk_interv_equipe FOREIGN KEY (id_equipe) REFERENCES equipes(id) ON DELETE SET NULL
);

-- Tabela de Histórico de Relatórios de Prioridade
CREATE TABLE relatorios (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    data_geracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    qt_critico NUMBER NOT NULL,
    qt_alta NUMBER NOT NULL,
    qt_baixa NUMBER NOT NULL,
    resumo VARCHAR2(1000) NOT NULL
);

COMMIT;