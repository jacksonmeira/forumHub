# FórumHub

**Projeto de Estudo - Desafio Back End da Alura**

Este repositório contém a API REST do **FórumHub**, desenvolvida como parte do challenge de Back End oferecido pela [Alura](https://www.alura.com.br).

Aqui você encontra um serviço simples para criar, listar, detalhar, atualizar e apagar tópicos de um fórum, com autenticação via JWT.

---

## Sobre o Projeto

* **Objetivo:** Aprender na prática como montar uma API REST completa usando Spring Boot.
* **Principais tecnologias:** Java 17, Spring Boot 3, Spring Data JPA, Spring Security, JWT, Flyway (migrations), MySQL.
* **Funcionalidades principais:**

  * CRUD de tópicos (`/topicos`)
  * Registro e login de usuários (`/auth/registrar`, `/auth/login`)
  * Validação de dados e prevenção de tópicos duplicados
  * Segurança com tokens JWT

---

## Como Começar

1. **Clone este repositório:**

   ```bash
   git clone https://github.com/seu-usuario/forumhub.git
   cd forumhub
   ```
2. **Configure o banco de dados:**

   * Crie um schema `forumhub` no MySQL.
   * No arquivo `src/main/resources/application.properties`, atualize:

     ```properties
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha
     ```
3. **Execute a aplicação:**

   ```bash
   mvn spring-boot:run
   ```

   As migrations do Flyway vão criar as tabelas automaticamente.

---

## Uso da API

### Autenticação

* **POST** `/auth/registrar`
  Registra novo usuário. Exemplo:

  ```json
  {
    "nome": "Maria",
    "email": "maria@exemplo.com",
    "senha": "123456"
  }
  ```
* **POST** `/auth/login`
  Gera JWT. Exemplo:

  ```json
  {
    "email": "maria@exemplo.com",
    "senha": "123456"
  }
  ```

  Retorna: `{ "token": "...", "tipo": "Bearer" }`

> Obs.: copie o token para usar nos endpoints protegidos.

### Endpoints de Tópicos

* **GET** `/topicos`
  Lista todos os tópicos. Não requer token.

* **GET** `/topicos/{id}`
  Mostra detalhes de um tópico. Não requer token.

* **POST** `/topicos`
  Cria um novo tópico. **Requer** header `Authorization: Bearer <token>`. Exemplo:

  ```json
  {
    "titulo": "Nova dúvida",
    "mensagem": "Como usar JWT no Spring?",
    "status": "NAO_RESPONDIDO",
    "autor": "Maria",
    "curso": "Spring Boot"
  }
  ```

* **PUT** `/topicos/{id}`
  Atualiza um tópico (requer token). Body igual ao POST.

* **DELETE** `/topicos/{id}`
  Exclui um tópico (requer token).

---

