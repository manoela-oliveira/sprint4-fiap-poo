# Sprint 4 POO - Programação Orientada a Objeto

> **Professor:** Ygor Moraes Martins dos Anjos
---

## Instruções para entrega

O objetivo macro desta Sprint é transformar o sistema MOTIVA — hoje um protótipo de console com persistência via JDBC puro (Sprint 3) — em uma API REST profissional usando Spring Boot e Spring Data JPA, reaproveitando o banco Oracle e as tabelas modeladas na Sprint 3.
<ul>
    <li><strong>Migração de Persistência (JPA):</strong> Substituir 100% dos DAOs manuais e conexões nativas JDBC por entidades mapeadas com anotações JPA (<code>@Entity</code>, <code>@Table</code>, <code>@Id</code>, <code>@GeneratedValue</code>) e interfaces que herdam de <code>JpaRepository</code>.</li>
    <li><strong>Mapeamento de Herança e Polimorfismo:</strong> Implementar a estratégia de herança relacional de tabela única (<code>@Inheritance(strategy = InheritanceType.SINGLE_TABLE)</code>) para as hierarquias de <code>TrechoRodovia</code> e <code>IntervencaoOperacional</code> com suas respectivas colunas discriminadoras.</li>
    <li><strong>Arquitetura em Camadas Estrita:</strong> Respeitar a separação de responsabilidades (SRP) estruturando o projeto nas camadas <code>model</code>, <code>repository</code>, <code>service</code> e <code>controller</code>, onde cada camada conversa apenas com sua vizinha direta.</li>
    <li><strong>CRUD REST Completo:</strong> Expor endpoints HTTP (GET, POST, PUT, DELETE) com tratamento correto de códigos de status de resposta (200 OK, 201 Created, 204 No Content e 404 Not Found) para as entidades principais.</li>
    <li><strong>Motor de Relatórios REST:</strong> Migrar a lógica de cálculo de prioridades de manutenção para o <code>RelatorioPrioridadeService</code>, expondo endpoints para geração e persistência no Oracle, além de consulta histórica.</li>
    <li><strong>Spring Data Derived Queries:</strong> Demonstrar o uso de consultas derivadas nos Repositories (ex: busca por nível mínimo de vegetação e filtragem de relatórios por intervalo de datas).</li>
    <li><strong>Tratamento Global de Erros:</strong> Capturar exceções de domínio de forma elegante via <code>@ControllerAdvice</code>, devolvendo JSONs amigáveis com status HTTP 400 em vez de falhas internas não tratadas.</li>
    <li><strong>Segurança e Boas Práticas:</strong> Não versionar credenciais sensíveis de banco no código fonte, utilizando placeholders e variáveis de ambiente gerenciadas no <code>application.properties</code>.</li>
</ul>


## Tecnologias Utilizadas
- **Java 17** (LTS)
- **Spring Boot 3.x**
  - Spring Web (construção de APIs REST)
  - Spring Data JPA (persistência e mapeamento ORM)
  - Hibernate (provedor JPA)
- **Oracle JDBC Driver (ojdbc17)** gerenciado via Maven
- **Maven** (gerenciador de dependências e build)

---

### Lógica para desenvolvimento do sistema
Com o objetivo de implementar melhorias no projeto entregue durante o processo avaliativo da Sprint 3, algumas alterações foram feitas na estrutura do código, contemplando as solicitação da nova entrega.
  ### Organização de arquivos da Sprint 3:

```text
sprint3-fiap-poo/
├── lib/
│   └── ojdbc17.jar                      # Driver JDBC oficial do Oracle (Java 17+)
├── sql/
│   ├── seu-script-criacao.sql           # DDL: Criação das tabelas no Oracle (IDENTITY, PKs)
│   └── seu-script-dados.sql             # DML: Carga inicial de dados de teste
├── src/
│   └── br/
│       └── com/
│           └── sistemamotiva/
│               ├── db/
│               │   └── ConexaoBanco.java         # Gerenciamento de conexões
│               │
│               │
│               ├── exception/
│               │   └── CredenciaisInvalidasException.java # Exceção customizada (ORA-01017)
│               │
│               ├── model/                        # Camada de Domínio / Regras de Negócio
│               │   ├── IdentificacaoTrecho.java 
│               │   ├── TrechoRodovia.java
│               │   ├── Autoestrada.java
│               │   ├── EstradaVicinal.java
│               │   ├── MonitoravelViaIoT.java
│               │   ├── AutoestradaSensorizada.java
│               │   ├── MockTrechoSensorizado.java
│               │   ├── EquipeManutencao.java
│               │   ├── IntervencaoOperacional.java
│               │   ├── RocadaMecanizada.java
│               │   ├── Pulverizacao.java
│               │   └── Manutencao.java
│               │
│               ├── dao/                          # Camada de Persistência (Data Access Object)
│               │   ├── EquipeManutencaoDAO.java   # CRUD completo para a tabela EQUIPES
│               │   ├── TrechoRodoviaDAO.java      # CRUD com extração polimórfica (TRECHOS)
│               │   ├── IntervencaoOperacionalDAO.java # CRUD para catálogo de INTERVENCOES
│               │   └── RelatorioPrioridadeDAO.java# Persistência de histórico via Java Record
│               │
│               ├── service/                      # Camada de Serviços da Aplicação
│               │   └── GeradorRelatorio.java      # Orquestrador de varredura e persistência
│               │
│               └── main/
│                   └── SistemaMonitoramento.java  # Pista de testes, auditoria e execução CRUD
├── .gitignore
└── README.md
```

### Organização de arquivos da Sprint 4:

```text
sprint4-fiap-poo/
├── pom.xml                                          # Gerenciador de dependências Maven (Spring Web, JPA, Oracle)
├── .gitignore                                      
├── README.md                                        # Documentação da API, cURL e perguntas conceituais
├── sql/
│   ├── seu-script-criacao.sql                       # DDL das tabelas Oracle reaproveitadas
│   └── seu-script-dados.sql                         
└── sistemamotiva/
    └── src/
        └── main/
            ├── java/
            │   └── br/
            │       └── com/
            │           └── motiva/
            │               └── sistemamotiva/
            │                   ├── MotivaApplication.java                  # Ponto de entrada (@SpringBootApplication)
            │                   │
            │                   ├── model/                                  # Entidades JPA (@Entity)
            │                   │   ├── EquipeManutencao.java
            │                   │   ├── IdentificacaoTrecho.java            # Value Object (@Embeddable)
            │                   │   ├── TrechoRodovia.java                  # Classe Mãe Abstrata (@Inheritance)
            │                   │   ├── Autoestrada.java                    # Subclasse (@DiscriminatorValue)
            │                   │   ├── EstradaVicinal.java                 # Subclasse (@DiscriminatorValue)
            │                   │   ├── IntervencaoOperacional.java         # Classe Mãe Abstrata (@Inheritance)
            │                   │   ├── RocadaMecanizada.java               # Subclasse (@DiscriminatorValue)
            │                   │   ├── Pulverizacao.java                   # Subclasse (@DiscriminatorValue)
            │                   │   └── RelatorioPrioridade.java           
            │                   │
            │                   ├── repository/                             # Camada de Acesso a Dados (Spring Data)
            │                   │   ├── EquipeManutencaoRepository.java     # JpaRepository + Derived Query
            │                   │   ├── TrechoRodoviaRepository.java        # JpaRepository + Derived Queries
            │                   │   ├── IntervencaoOperacionalRepository.java
            │                   │   └── RelatorioPrioridadeRepository.java  # JpaRepository + Filtro por período
            │                   │
            │                   ├── service/                                # Camada de Negócio e Orquestração (@Service)
            │                   │   ├── EquipeManutencaoService.java        # Validações fail-fast e regras de equipe
            │                   │   ├── TrechoRodoviaService.java           # Validações e atualizações de trechos
            │                   │   ├── IntervencaoOperacionalService.java  # Gestão de custos e serviços
            │                   │   └── RelatorioPrioridadeService.java     # Motor de priorização de roçada
            │                   │
            │                   ├── controller/                             # Camada REST / Endpoints HTTP (@RestController)
            │                   │   ├── EquipeManutencaoController.java     # CRUD completo /api/equipes
            │                   │   ├── TrechoRodoviaController.java        # CRUD completo /api/trechos
            │                   │   ├── IntervencaoOperacionalController.java # CRUD completo /api/intervencoes
            │                   │   └── RelatorioPrioridadeController.java  # Endpoints /api/relatorios
            │                   │
            │                   └── exception/                              # Tratamento de Erros e Exceções de Domínio
            │                       ├── EquipeInvalidaException.java        # Exceção de regra de negócio
            │                       ├── TrechoInvalidoException.java        # Exceção de regra de negócio
            │                       └── GlobalExceptionHandler.java         # Tratamento global REST (@ControllerAdvice)
            │
            └── resources/
                └── application.properties                   # Configurações do Oracle, JPA e variáveis de ambiente
```


## Como executar o projeto no Visual Studio Code? Faça como eu fiz!

### Pré-requisitos
1. Ter o **Java Development Kit (JDK 17+)** instalado na máquina.
2. Ter o **VS Code** instalado com as seguintes extensões (disponíveis no Marketplace):
   - **Extension Pack for Java** (Microsoft)
   - **Spring Boot Tools** (VMware)
   - **Maven for Java** (Microsoft)
3. Conexão ativa com o banco de dados **Oracle da FIAP** (via rede do laboratório ou VPN).

### Passo 1: Preparar o Banco de Dados
As tabelas e dados de teste utilizados são os mesmos da entrega anterior:
1. Abra sua ferramenta de banco de dados (ex: extensão *Oracle SQL Developer for VSCode*, *DBeaver* ou *SQL Developer*).
2. Conecte-se com seu usuário e senha no Oracle da FIAP (`oracle.fiap.com.br:1521:ORCL`).
3. Execute o script DDL: `sql/seu-script-criacao.sql` para garantir que as tabelas `equipes`, `trechos`, `intervencoes` e `relatorios` estejam criadas.
4. *(Opcional)* Execute o script DML: `sql/seu-script-dados.sql` para realizar uma carga inicial de teste.

### Passo 2: Configurar as credenciais no application.properties
Diferente da Sprint 3 (onde usávamos código Java manual), o Spring Boot gerencia o pool de conexões automaticamente:
1. Navegue até o arquivo `sistemamotiva/src/main/resources/application.properties`.
2. Certifique-se de que a URL do Oracle está apontando para o servidor da FIAP:
   ```properties
   spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
   spring.datasource.driverClassName=oracle.jdbc.OracleDriver
   spring.datasource.username=${DB_USER:seu_RM_fiap}
   spring.datasource.password=${DB_PASSWORD:sua_senha_fiap}
## Tabela de Endpoints da API REST

| Entidade | Método | Rota | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- | :--- |
| **Equipes** | \`GET\` | \`/api/equipes\` | Lista todas as equipes | 200 OK |
| **Equipes** | \`GET\` | \`/api/equipes/{id}\` | Busca equipe por ID | 200 OK / 404 |
| **Equipes** | \`POST\` | \`/api/equipes\` | Cadastra nova equipe | 201 Created |
| **Equipes** | \`PUT\` | \`/api/equipes/{id}\` | Atualiza equipe existente | 200 OK / 404 |
| **Equipes** | \`DELETE\` | \`/api/equipes/{id}\` | Remove equipe | 204 No Content |
| **Trechos** | \`GET\` | \`/api/trechos\` | Lista todos os trechos cadastrados | 200 OK |
| **Trechos** | \`GET\` | \`/api/trechos/{id}\` | Busca trecho por ID | 200 OK / 404 |
| **Trechos** | \`GET\` | \`/api/trechos/filtro/vegetacao?minimo=30.0\` | Filtra por vegetação mínima (Derived Query) | 200 OK |
| **Trechos** | \`POST\` | \`/api/trechos\` | Cadastra novo trecho (Autoestrada/Vicinal) | 201 Created |
| **Trechos** | \`PUT\` | \`/api/trechos/{id}\` | Atualiza nível/atributos do trecho | 200 OK / 404 |
| **Trechos** | \`DELETE\` | \`/api/trechos/{id}\` | Remove trecho | 204 No Content |
| **Intervenções** | \`GET\` | \`/api/intervencoes\` | Lista catálogo de intervenções | 200 OK |
| **Intervenções** | \`POST\` | \`/api/intervencoes\` | Cria intervenção operacional | 201 Created |
| **Relatórios** | \`POST\` | \`/api/relatorios\` | Gera análise dos trechos e persiste histórico | 201 Created |
| **Relatórios** | \`GET\` | \`/api/relatorios\` | Lista o histórico de auditoria | 200 OK |
| **Relatórios** | \`GET\` | \`/api/relatorios/periodo?inicio=...&fim=...\` | Filtra relatórios por intervalo de datas | 200 OK |


## Exemplos de Chamadas cURL

### 1. Criar Equipe de Manutenção
\`\`\`bash
curl -X POST http://localhost:8080/api/equipes \
  -H "Content-Type: application/json" \
  -d '{"identificadorEquipe":"Equipe Alfa","quantidadeMembros":4}'
\`\`\`

### 2. Cadastrar Autoestrada (Polimorfismo JPA)
\`\`\`bash
curl -X POST http://localhost:8080/api/trechos \
  -H "Content-Type: application/json" \
  -d '{"tipo":"AUTOESTRADA","identificador":{"codigoIdentificacao":"BR-101-LITORAL","quilometroInicial":0.0,"quilometroFinal":15.5},"nivelVegetacaoCm":35.0,"regiaoUmida":true,"quantidadeFaixas":4}'
\`\`\`

### 3. Cadastrar Estrada Vicinal
\`\`\`bash
curl -X POST http://localhost:8080/api/trechos \
  -H "Content-Type: application/json" \
  -d '{"tipo":"ESTRADA_VICINAL","identificador":{"codigoIdentificacao":"VIC-404-INTERIOR","quilometroInicial":0.0,"quilometroFinal":8.0},"nivelVegetacaoCm":18.0,"regiaoUmida":false,"pavimentada":false}'
\`\`\`

### 4. Cadastrar Intervenção Operacional
\`\`\`bash
curl -X POST http://localhost:8080/api/intervencoes \
  -H "Content-Type: application/json" \
  -d '{"tipo":"ROCADA","descricao":"Rocada Mecanica Pesada","custoEstimado":1500.0}'
\`\`\`

### 5. Disparar Varredura e Gravação de Relatório
\`\`\`bash
curl -X POST http://localhost:8080/api/relatorios
\`\`\`

### 6. Consultar Histórico por Período
\`\`\`bash
curl -X GET "http://localhost:8080/api/relatorios/periodo?inicio=2026-01-01T00:00:00&fim=2026-12-31T23:59:59"
\`\`\`



## Conceitos em Foco (Perguntas de Reflexão)

### 1. Por que o Repository é uma interface e não uma classe? Quem escreve a implementação e quando?
**Resposta:** O Repository é definido como uma interface porque ele atua como um contrato puro de persistência seguindo o Princípio da Inversão de Dependência (DIP). O desenvolvedor não precisa escrever código repetitivo de banco (boilerplate). O próprio **Spring Data JPA**, em tempo de inicialização da aplicação (runtime), utiliza *Dynamic Proxies* (procuradores dinâmicos) do Java para gerar automaticamente a classe concreta que implementa todos os comandos SQL no banco de dados.

### 2. O pattern DAO da Sprint 3 "morreu" na migração ou só mudou de forma?
**Resposta:** O pattern DAO **não morreu; ele foi absorvido e automatizado**. O papel do DAO sempre foi isolar as regras de acesso a dados da lógica de negócios. Na Sprint 4, a interface `JpaRepository` desempenha exatamente o papel de um DAO corporativo, com a vantagem de ser padronizada, testada e gerada pelo framework sem os riscos de vazamento de conexões ou SQL injection manual.

### 3. Por que a validação de `nivelVegetacao >= 0` deve ficar no Service e não no Controller?
**Resposta:** Porque a validação de valores de domínio é uma **Regra de Negócio**, e não uma regra de protocolo web. O `@RestController` possui uma única responsabilidade (SRP): receber requisições HTTP, delegar para a aplicação e responder JSON. Se colocarmos validações no Controller, perderemos a capacidade de reutilizar essa mesma regra caso a aplicação receba dados por outras fontes (como mensagens IoT em mensageria, tarefas agendadas ou testes unitários).

### 4. No JDBC puro vocês escreviam SQL. Onde está o SQL do `findByTipo()`? Quem o gerou?
**Resposta:** O SQL não precisa ser escrito manualmente porque o Spring Data JPA utiliza o recurso de **Derived Queries**. O framework possui um analisador sintático que interpreta o nome do método (`findBy...`), mapeia as palavras-chave para a entidade correspondente e converte automaticamente a assinatura em uma instrução SQL (`SELECT * FROM trechos WHERE tipo = ?`) adaptada para o dialeto do Oracle.