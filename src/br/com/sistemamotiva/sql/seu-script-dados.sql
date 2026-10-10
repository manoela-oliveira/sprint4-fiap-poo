-- Inserções de teste
INSERT INTO equipes (identificador, quantidade_membros) VALUES ('Equipe Alfa', 4);
INSERT INTO equipes (identificador, quantidade_membros) VALUES ('Equipe Beta', 2);
INSERT INTO equipes (identificador, quantidade_membros) VALUES ('Equipe Gama', 3);

INSERT INTO trechos (codigo, km_inicial, km_final, nivel_vegetacao, tipo, regiao_umida, quantidade_faixas, is_pavimentada)
VALUES ('BR-101-LITORAL', 0.0, 15.5, 32.0, 'AUTOESTRADA', 1, 4, 1);

INSERT INTO trechos (codigo, km_inicial, km_final, nivel_vegetacao, tipo, regiao_umida, quantidade_faixas, is_pavimentada)
VALUES ('BR-116-SERRA', 120.0, 135.0, 24.5, 'AUTOESTRADA', 0, 2, 1);

INSERT INTO trechos (codigo, km_inicial, km_final, nivel_vegetacao, tipo, regiao_umida, quantidade_faixas, is_pavimentada)
VALUES ('VIC-404-INTERIOR', 0.0, 8.0, 18.0, 'ESTRADA_VICINAL', 0, 1, 0);

INSERT INTO intervencoes (tipo, descricao, custo_estimado)
VALUES ('ROCADA', 'Roçada mecânica pesada com trator', 1500.00);

INSERT INTO intervencoes (tipo, descricao, custo_estimado)
VALUES ('PULVERIZACAO', 'Aplicação química controlada de defensivo inibidor', 850.00);

COMMIT;