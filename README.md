# Sistema de Gestão de Projetos e Pessoas

## 1. Visão Geral

Este projeto é uma aplicação de desktop Java Swing para gerenciamento de equipes e projetos, seguindo a metodologia Scrum. Ele permite o cadastro de diferentes tipos de usuários (Product Owners, Tech Leads, Developers), a criação de produtos, a decomposição de produtos em demandas e a quebra de demandas em tarefas.

O sistema possui um fluxo de autenticação e uma arquitetura de back-end robusta, com separação clara de responsabilidades, pronta para futuras expansões.

## 2. Funcionalidades Implementadas

*   **Autenticação de Usuário:** Tela de login com validação de credenciais (usuário e senha).
*   **Registro de Usuário:** Tela de registro para novos usuários com validação de regras de negócio (tamanho de senha, formato de CPF, CPF único).
*   **Segurança:** Senhas e CPFs são armazenados de forma segura no banco de dados usando hashing (bcrypt).
*   **Navegação por Telas:** Uso de `CardLayout` para um fluxo de navegação entre telas de Login, Registro e a Home Page principal.
*   **Home Page Centralizada:** Após o login, o usuário é direcionado para uma página principal com acesso às áreas de "Board de Projetos" e "Gerenciamento de Usuários".
*   **Gerenciamento de Entidades (CRUD):**
    *   **Produtos:** Criação e listagem.
    *   **Demandas:** Criação e listagem.
    *   **Tarefas:** Criação e listagem.
*   **Visualização em Abas:** A área do "Board" é organizada em abas (`JTabbedPane`) para separar a visualização de Produtos, Demandas e Tarefas.
*   **Associação de Entidades:**
    *   Associação de um `ProductOwner` a um `Product` no momento da criação.
    *   Associação de uma `Demand` a um `Product`.
    *   Associação de uma `Task` a uma `Demand`.
*   **Atualização de Dados Dinâmica:** As listas de dados nas telas são atualizadas automaticamente quando o usuário navega entre as abas.

## 3. Arquitetura

O projeto foi construído seguindo uma arquitetura de N-Camadas (N-Tier), que promove a separação de responsabilidades, alta coesão e baixo acoplamento.

**Fluxo da Requisição:** `View -> Controller -> Service -> DAO -> Database`

*   **`View` (Pacote `br.com.anhembi.view`):** A camada de apresentação, construída com Java Swing. É responsável por exibir os dados e capturar as interações do usuário. Ela não contém nenhuma lógica de negócio.
*   **`Controller` (Pacote `br.com.anhembi.controller`):** A ponte entre a `View` e o `Service`. Ele recebe as solicitações da `View` (ex: um clique de botão) e as delega para o método apropriado na camada de Serviço.
*   **`Service` (Pacote `br.com.anhembi.service`):** O "cérebro" da aplicação. Esta camada contém toda a lógica de negócio, validações (ex: senha forte, CPF único), orquestração de operações e a hidratação de objetos. É a única camada que pode coordenar múltiplos DAOs para completar uma tarefa complexa.
*   **`DAO` (Data Access Object) (Pacote `br.com.anhembi.dao`):** A camada de persistência. Sua única responsabilidade é se comunicar com o banco de dados. Cada DAO é "enxuto" e focado em sua própria tabela, executando operações CRUD (Create, Read, Update, Delete) através de JDBC.
*   **`Model` (Pacote `br.com.anhembi.model`):** Representa as entidades de dados do sistema (ex: `Users`, `Product`, `Demand`). São objetos puros que contêm os dados e, opcionalmente, métodos que manipulam seu próprio estado em memória.
*   **`Database` (Pacote `br.com.anhembi.db`):** Contém a classe `DBConnection` responsável por fornecer a conexão com o banco de dados PostgreSQL.

## 4. Tecnologias Utilizadas

*   **Linguagem:** Java 17+
*   **Interface Gráfica:** Java Swing
*   **Banco de Dados:** PostgreSQL
*   **Conectividade DB:** JDBC (PostgreSQL Driver)
*   **Gerenciador de Dependências:** Apache Maven
*   **Segurança:** Spring Security Crypto (para hashing de senhas e CPFs)
*   **Logging:** `java.util.logging`

## 5. Como Executar o Projeto

#### Pré-requisitos
1.  **JDK 21** ou superior instalado.
2.  **Apache Maven** instalado e configurado.
3.  **PostgreSQL** instalado e rodando.

#### 1. Configuração do Banco de Dados
1.  Crie um novo banco de dados no PostgreSQL com o nome `team_management_db`.
2.  Execute o script SQL abaixo para criar todas as tabelas e relações necessárias:
