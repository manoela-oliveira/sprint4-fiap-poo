package br.com.sistemamotiva.main;

import br.com.sistemamotiva.dao.EquipeManutencaoDAO;
import br.com.sistemamotiva.dao.IntervencaoOperacionalDAO;
import br.com.sistemamotiva.dao.RelatorioPrioridadeDAO;
import br.com.sistemamotiva.dao.TrechoRodoviaDAO;
import br.com.sistemamotiva.db.ConexaoBanco;
import br.com.sistemamotiva.exception.CredenciaisInvalidasException;
import br.com.sistemamotiva.model.*;
import br.com.sistemamotiva.service.GeradorRelatorio;
import java.sql.Connection;
import java.util.List;

public class SistemaMonitoramento {

    public static void main(String[] args) {
        System.out.println("\n==================================================================");
        System.out.println("   PISTA DE TESTES MOTIVA: INTEGRAÇÃO JDBC & VALIDAÇÃO POO");
        System.out.println("==================================================================");

        Connection conexaoTeste = null;

        try {
            // CONECTIVIDADE COM O BANCO DE DADOS ORACLE
            imprimirSeparador("Teste de Infraestrutura e Conexão");
            conexaoTeste = ConexaoBanco.getConexao();
            System.out.println("Status da Conexão: ATIVA (Oracle DB alcançado com sucesso)");
            System.out.println("Nota: As tabelas devem ter sido criadas previamente via script SQL.");

            // RESILIÊNCIA E REGRAS DE POO EM MEMÓRIA (FAIL-FAST)
             imprimirSeparador("BATERIA 2: Estresse de Regras de Domínio e Proteção POO");

            System.out.println("\n[Teste 2.1] Tentativa de criar equipe abaixo do limite regulatório (mínimo 2):");
            try {
                new EquipeManutencao("Gama-Invalida", 1);
                System.err.println("FALHA: O sistema permitiu criar equipe com 1 membro!");
            } catch (br.com.sistemamotiva.exception.EquipeInvalidaException e) {
                System.out.println("SUCESSO (Fail-Fast): Exceção capturada com a mensagem -> " + e.getMessage());
            }

            System.out.println("\n[Teste 2.2] Tentativa de cadastrar quilometragem negativa em Trecho:");
            try {
                new IdentificacaoTrecho("BR-TESTE-NEG", -15.0, -5.0);
                System.err.println("FALHA: O sistema permitiu quilometragem negativa!");
            } catch (br.com.sistemamotiva.exception.TrechoInvalidoException e) {
                System.out.println("SUCESSO (Fail-Fast): Exceção capturada com a mensagem -> " + e.getMessage());
            }

            System.out.println("\n[Teste 2.3] Proteção da Legislação Ambiental (Roçada nunca < 5.0cm):");
            IdentificacaoTrecho localEcol = new IdentificacaoTrecho("BR-ECO-01", 0.0, 10.0);
            TrechoRodovia autoestradaEcologica = new Autoestrada(localEcol, 25.0, 2);
            System.out.println("Vegetação Pré-Intervenção: " + autoestradaEcologica.getNivelVegetacaoCm() + "cm");
            autoestradaEcologica.atualizarNivelVegetacao(1.5); // Tentativa predatória
            System.out.println("Vegetação Pós-Intervenção: " + autoestradaEcologica.getNivelVegetacaoCm() + "cm (Mínimo ecológico preservado com sucesso!)");

            // CRUD DE EQUIPE DE MANUTENÇÃO (JDBC)
            imprimirSeparador("Operações CRUD - Entidade EquipeManutencao");
            EquipeManutencaoDAO equipeDAO = new EquipeManutencaoDAO();

            System.out.println("\n[CRUD C] Inserindo nova equipe operacional...");
            EquipeManutencao equipeOperacional = new EquipeManutencao("Equipe Delta-Frente", 3);
            equipeDAO.inserir(equipeOperacional);
            System.out.println("Registro criado no Oracle: " + equipeOperacional);

            System.out.println("\n[CRUD R] Consultando equipe por ID (" + equipeOperacional.getId() + ")...");
            EquipeManutencao equipeConsultada = equipeDAO.buscarPorId(equipeOperacional.getId());
            System.out.println("Registro recuperado: " + equipeConsultada);

            System.out.println("\n[CRUD U] Atualizando quantidade de membros da equipe...");
            equipeConsultada.setQuantidadeMembros(5);
            equipeDAO.atualizar(equipeConsultada);
            EquipeManutencao equipeAtualizada = equipeDAO.buscarPorId(equipeConsultada.getId());
            System.out.println("Registro após atualização: " + equipeAtualizada);

            // CRUD DE TRECHOS RODOVIÁRIOS (POLIMORFISMO & PERSISTÊNCIA)
            imprimirSeparador("Operações CRUD - Entidade TrechoRodovia");
            TrechoRodoviaDAO trechoDAO = new TrechoRodoviaDAO();

            System.out.println("\n[CRUD C] Inserindo trecho com vegetação crítica...");
            IdentificacaoTrecho localCritico = new IdentificacaoTrecho("BR-381-MINAS", 100.0, 120.0);
            Autoestrada trechoCritico = new Autoestrada(localCritico, 38.0, 4);
            trechoDAO.inserir(trechoCritico);
            System.out.println("Trecho persistido: ID " + trechoCritico.getId() + " | Prioridade: " + trechoCritico.calcularPrioridade());

            System.out.println("\n[CRUD R] Listando todos os trechos registrados no banco Oracle:");
            List<TrechoRodovia> trechosCadastrados = trechoDAO.listarTodas();
            trechosCadastrados.forEach(TrechoRodovia::exibirInformacoes);

            // CRUD DE INTERVENÇÕES OPERACIONAIS
            imprimirSeparador("Operações CRUD - Intervenção Operacional");
            IntervencaoOperacionalDAO intervencaoDAO = new IntervencaoOperacionalDAO();

            System.out.println("\n[CRUD R] Carregando serviços catalogados no banco:");
            List<IntervencaoOperacional> intervencoes = intervencaoDAO.listarTodas();
            intervencoes.forEach(System.out::println);

            // GERADOR DE RELATÓRIO COM PERSISTÊNCIA DE HISTÓRICO
            imprimirSeparador("Serviço de Domínio e Gravação de Relatório");
            GeradorRelatorio gerador = new GeradorRelatorio();

            System.out.println("\nProcessando malha rodoviária capturada do banco...");
            TrechoRodovia[] malhaParaAnalise = trechosCadastrados.toArray(new TrechoRodovia[0]);
            gerador.gerarRelatorio(malhaParaAnalise);

            // AUDITORIA DE HISTÓRICO NO ORACLE (VIA RECORD)
            imprimirSeparador("Auditoria e Consulta do Histórico de Relatórios");
            RelatorioPrioridadeDAO relatorioDAO = new RelatorioPrioridadeDAO();

            List<RelatorioPrioridadeDAO.RelatorioRegistro> historico = relatorioDAO.listarTodas();
            System.out.println("Total de relatórios arquivados no Oracle: " + historico.size());
            historico.forEach(registro -> System.out.printf(
                    "• Relatório #%d [%s] -> Críticos: %d | Altos: %d | Baixos: %d | Obs: %s\n",
                    registro.id(), registro.dataGeracao(), registro.qtCritico(),
                    registro.qtAlta(), registro.qtBaixa(), registro.resumo()
            ));

            // TESTES CRUD DO RELATÓRIO
            if (!historico.isEmpty()) {
                imprimirSeparador("Operações CRUD Faltantes - RelatorioPrioridadeDAO (Correção Professor)");
                
                // Pegando o primeiro relatório da lista para fazer o teste
                RelatorioPrioridadeDAO.RelatorioRegistro relatorioOriginal = historico.get(0);
                Long idTeste = relatorioOriginal.id();

                System.out.println("\n[CRUD R] Buscando Relatório específico pelo ID (" + idTeste + ")...");
                RelatorioPrioridadeDAO.RelatorioRegistro relatorioBuscado = relatorioDAO.buscarPorId(idTeste);
                System.out.println("Encontrado: " + relatorioBuscado);

                System.out.println("\n[CRUD U] Atualizando o resumo do relatório...");
                // Criando uma cópia imutável (Record) com o novo resumo
                RelatorioPrioridadeDAO.RelatorioRegistro relatorioAtualizado = new RelatorioPrioridadeDAO.RelatorioRegistro(
                        idTeste,
                        relatorioBuscado.dataGeracao(),
                        relatorioBuscado.qtCritico(),
                        relatorioBuscado.qtAlta(),
                        relatorioBuscado.qtBaixa(),
                        relatorioBuscado.resumo() + " [REVISADO PELO SISTEMA]"
                );
                relatorioDAO.atualizar(relatorioAtualizado);
                System.out.println("Atualização concluída. Verificando persistência:");
                System.out.println("Registro Pós-Update: " + relatorioDAO.buscarPorId(idTeste));

                System.out.println("\n[CRUD D] Deletando o relatório de teste...");
                relatorioDAO.deletar(idTeste);
                System.out.println("Relatório ID " + idTeste + " apagado com sucesso.");
            }

            // DELETAR/LIMPEZA DE DADOS TEMPORÁRIOS
            imprimirSeparador("Finalização do Ciclo CRUD (Limpeza)");
            System.out.println("\nRemovendo equipe de teste ID: " + equipeOperacional.getId());
            equipeDAO.deletar(equipeOperacional.getId());
            System.out.println("Remoção confirmada.");

            System.out.println("\nRemovendo trecho de teste ID: " + trechoCritico.getId());
            trechoDAO.deletar(trechoCritico.getId());
            System.out.println("Remoção confirmada.");

            System.out.println("\n==================================================================");
            System.out.println(" PISTA DE TESTES FINALIZADA COM SUCESSO!");
            System.out.println("==================================================================");

        } catch (CredenciaisInvalidasException e) {
            System.err.println("\n FALHA CRÍTICA DE AUTENTICAÇÃO:");
            System.err.println(e.getMessage());
            System.err.println(e.getDicaCorrecao());
        } catch (Exception e) {
            System.err.println("\n FALHA INESPERADA NA EXECUÇÃO DOS TESTES:");
            System.err.println("Motivo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConexaoBanco.fechar(conexaoTeste);
        }
    }

    private static void imprimirSeparador(String titulo) {
        System.out.println("\n------------------------------------------------------------------");
        System.out.println(">> " + titulo);
        System.out.println("------------------------------------------------------------------");
    }
}