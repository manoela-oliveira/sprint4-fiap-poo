# Sprint 4 POO - Programação Orientada a Objeto

> **Professor:** Ygor Moraes Martins dos Anjos
---

## Instruções para entrega

O objetivo macro desta Sprint é transformar o sistema MOTIVA — hoje um protótipo de console com persistência via JDBC puro (Sprint 3) — em uma API REST profissional usando Spring Boot e Spring Data JPA, reaproveitando o banco Oracle e as tabelas modeladas na Sprint 3.
<ul>
    <li>
    </li>
    <li>
    </li>
</ul>

---
### Tecnologias utilizadas
<p>
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=git,java" />
  </a>
</p>

### Lógica para desenvolvimento do sistema
Com o objetivo de implementar melhorias no projeto entregue durante o processo avaliativo da Sprint 3, algumas alterações foram feitas na estrutura do código a fim de contemplar as solicitação da nova entrega.
  ### Organização de arquivos da Sprint 3:

```text
sistema-motiva/
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




## Como executar o projeto no Visual Studio Code? Faça como eu fiz!

### Pré-requisitos
1. Ter o **Java Development Kit (JDK 17+)** instalado.
2. Ter o **VS Code** instalado com a extensão **Extension Pack for Java** (fornecida pela Microsoft).
3. Acesso a um banco de dados **Oracle** (laboratório da faculdade ou versão XE/Docker local).

### Passo 1: Preparar o Banco de Dados
Antes de rodar o código Java, é necessário preparar as tabelas:
1. Abra sua ferramenta de banco de dados (ex: *Oracle SQL Developer for VSCode*, *DBeaver* ou *SQL Developer*).
2. Conecte-se ao seu banco de dados Oracle.
3. Execute o script DDL: 'sql/seu-script-criacao.sql' (Ele apagará versões antigas e recriará as tabelas limpas).
4. Execute o script DML: 'sql/seu-script-dados.sql' (Ele populará o banco com informações de teste).

### Passo 2: Configurar as credenciais no Java
Para que o sistema consiga acessar o banco de dados:
1. Navegue até o arquivo 'src/br/com/sistemamotiva/db/ConexaoBanco.java'.
2. Altere as variáveis contendo a **URL**, **Usuário** e **Senha** para refletir as credenciais do seu banco de dados Oracle.

### Passo 3: Configurar o driver JDBC no VS Code
O projeto necessita do 'ojdbc17.jar' para se comunicar com o banco:
1. No menu lateral esquerdo do VS Code, abra a aba **Java Projects** (fica geralmente na parte inferior do painel Explorer).
2. Expanda o projeto 'sistema-motiva'.
3. Procure por **Referenced Libraries** (Bibliotecas Referenciadas).
4. Clique no ícone de **+** (Add Jar/Folder) que aparece ao passar o mouse por cima.
5. Selecione o arquivo 'ojdbc17.jar' localizado na pasta 'lib/' do projeto.

### Passo 4: Executar a aplicação
1. No explorador de arquivos, abra a classe principal: 'src/br/com/sistemamotiva/main/SistemaMonitoramento.java'.
2. Acima do método 'public static void main(String[] args)', o VS Code exibirá um botão escrito **Run** (Executar).
3. Clique em **Run**.
4. Acompanhe no terminal integrado do VS Code a demonstração completa dos testes de infraestrutura, proteções POO, operações CRUD e geração do relatório em banco!