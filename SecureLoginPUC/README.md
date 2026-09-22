# SecureLoginPUC

Aplicação web de **login e cadastro de usuários**, desenvolvida com **Java, Spring Boot, Spring Security e Thymeleaf**.

Projeto desenvolvido para a Atividade 02 da disciplina Laboratório de Desenvolvimento de Software (PUC Minas — Prof. João Paulo Aramuni).

## 🛠️ Tecnologias e dependências

- Java 25
- Spring Boot
- Spring Security (autenticação e proteção de rotas)
- Spring Web
- Thymeleaf (páginas HTML)

## 📦 Estrutura do projeto

```
src/main/java/com/example/
├── SecureLoginPucApplication.java   # classe principal (@SpringBootApplication)
├── config/
│   └── SecurityConfig.java          # configuração do Spring Security
├── controller/
│   └── SecureLoginController.java   # endpoints (login, cadastro, etc.)
└── service/
    └── UserService.java             # lógica de cadastro/autenticação

src/main/resources/
├── templates/                       # páginas Thymeleaf (.html)
└── static/
    ├── css/
    └── images/
```

## 🔒 Como funciona a autenticação

Este projeto **não usa banco de dados**. Os usuários cadastrados ficam guardados em **memória**, através do `InMemoryUserDetailsManager` do Spring Security — ou seja, os cadastros são perdidos toda vez que a aplicação é reiniciada.

- As senhas são armazenadas de forma segura, criptografadas com **BCrypt** (nunca em texto puro).
- O login (`POST /login`) é processado automaticamente pelo Spring Security, com base nos usuários cadastrados via `/register`.
- Não há usuários pré-cadastrados: é necessário se cadastrar antes de conseguir logar.

## ▶️ Como executar localmente

Pré-requisitos: Java 25 e Maven (ou use o `mvnw` incluso no projeto).

```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd <pasta_do_projeto>
./mvnw spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

Não é necessário configurar nenhuma credencial no `application.properties` para rodar a aplicação — o único ajuste presente é:

```properties
spring.thymeleaf.cache=false
```

Isso desativa o cache do Thymeleaf durante o desenvolvimento, para que alterações nos arquivos `.html` apareçam sem precisar reiniciar a aplicação.

## 🌐 Endpoints disponíveis

| Método | Endpoint            | Descrição                                              | Requer login |
|--------|----------------------|---------------------------------------------------------|:---:|
| `GET`  | `/login`             | Exibe a tela de login                                    | Não |
| `GET`  | `/register`          | Exibe a tela de cadastro                                  | Não |
| `POST` | `/register`          | Processa o cadastro de um novo usuário                    | Não |
| `GET`  | `/recoverpassword`   | Exibe a tela de recuperação de senha                       | Não |
| `POST` | `/recoverpassword`   | Processa a solicitação de recuperação (registra no console) | Não |
| `GET`  | `/home`              | Página inicial após login, exibe o usuário autenticado     | Sim |
| `POST` | `/logout`            | Encerra a sessão do usuário                                | Sim |
| `GET`  | `/error`             | Exibe uma página de erro genérica                          | Não |

> `POST /login` não possui um Controller próprio — é processado automaticamente pelo Spring Security, conforme configurado em `SecurityConfig`.

### Cadastro (`POST /register`)

Campos esperados: `nome`, `email`, `cpf`, `rg`, `campus`, `instituicao`, `senha`, `confirmarSenha`.

Validações implementadas:
- Bloqueia cadastro com senhas diferentes (`senha` ≠ `confirmarSenha`).
- Bloqueia cadastro com e-mail já existente.
