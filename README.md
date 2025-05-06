# API Fluxo

## Descrição

O Fluxo é um sistema completo para gestão inteligente de estoques, desenvolvido como solução para o controle de operações logísticas. Esta API constitui o núcleo backend do sistema, responsável por:

- Integrar o frontend com o banco de dados PostgreSQL

- Fornecer endpoints RESTful para operações críticas

- Garantir a segurança das transações através de JWT

## Funcionalidades

- **Gerenciamento de Produtos**
  - Cadastrar novos produtos
  - Atualizar informações de produtos existentes
  - Listar produtos com suporte a pesquisa
  - Excluir produtos

- **Gerenciamento de Lotes**
  - Registrar entradas e saídas de lotes
  - Listar movimentações de estoque
  - Excluir movimentações

- **Gerenciamento de Usuários**
  - Cadastrar novos usuários
  - Atualizar informações de usuários
  - Listar usuários
  - Excluir usuários
  - Autenticação de usuários com JWT

- **Enumerações**
  - Listar papéis de usuário
  - Listar tipos de operações

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL
- Swagger para documentação da API
- Flyway para gerenciamento de migrações de banco de dados
- Lombok para simplificação do código

## Estrutura do Projeto

```
api_fluxo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── fluxo/
│   │   │           └── api_fluxo/
│   │   │               ├── controller/
│   │   │               ├── dto/
│   │   │               ├── domain/
│   │   │               ├── repositories/
│   │   │               ├── service/
│   │   │               └── configs/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── migrations/
│   └── test/
│       └── java/
│           └── com/
│               └── fluxo/
│                   └── api_fluxo/
│ 
├── .gitignore
├── Dockerfile
├── README.md
└── pom.xml
```

## Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/IDeath-Z/Fluxo.git
   cd Fluxo
   ```

2. **Configure o banco de dados:**
   - Crie um banco de dados PostgreSQL e configure as credenciais no arquivo `application.properties ou através de um arquivo .env`.

3. **Execute as migrações:**
   - As migrações do Flyway serão executadas automaticamente ao iniciar a aplicação.

4. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Acesse a API:**
   - A API estará disponível em `http://localhost:8080`.

## Documentação da API

A documentação da API pode ser acessada através do Swagger em `http://localhost:8080/swagger-ui.html`.
