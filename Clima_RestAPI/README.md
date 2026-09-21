# Clima RestAPI

API REST desenvolvida com **Java** e **Spring Boot** para consultar informações meteorológicas de **Belo Horizonte - MG**, consumindo a API externa da [OpenWeather](https://openweathermap.org/).

Projeto desenvolvido para a Atividade 01 da disciplina Laboratório de Desenvolvimento de Software (PUC Minas — Prof. João Paulo Aramuni).

## 🛠️ Tecnologias e dependências

- Java 25
- Spring Boot 4.1.1
- Spring Web (`RestTemplate`)
- Maven

## 📦 Estrutura do projeto

```
src/main/java/com/example/Clima_RestAPI/
│   
├── controller/
│   └── Controller.java                # endpoints REST
└── service/
|   └── ClimaService.java              # lógica de consumo da API OpenWeather
└── ClimaRestApiApplication.java   # classe principal (@SpringBootApplication)
```

## 🔑 Configuração da API Key

Este projeto usa uma chave de API da OpenWeather, que **não deve ser publicada no repositório**.

1. Crie uma conta gratuita em [openweathermap.org](https://openweathermap.org/) e gere sua API Key (menu "API keys" no seu perfil).
2. No arquivo `src/main/resources/application.properties`, adicione:

   ```properties
   openweather.api.key=SUA_CHAVE_AQUI
   ```

3. **Importante:** não faça commit da sua chave real. Se for subir o projeto para um repositório público, substitua o valor por um placeholder antes do commit (ou remova o arquivo do controle de versão com `git rm --cached` e adicione `application.properties` ao `.gitignore`).

## ▶️ Como executar localmente

Pré-requisitos: Java 25 e Maven instalados (ou use o `mvnw` incluso no projeto).

```bash
# clone o repositório
git clone <URL_DO_SEU_REPOSITORIO>
cd <pasta_do_projeto>

# configure sua API Key (veja seção acima) e então rode:
./mvnw spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

## 🌐 Endpoints disponíveis

### `GET /climaBH`

Retorna o clima atual de Belo Horizonte - MG, sem necessidade de parâmetros (coordenadas fixas no `Service`).

**Exemplo:**
```
GET http://localhost:8080/climaBH
```

### `GET /weather?lat={latitude}&lon={longitude}`

Retorna o clima atual para qualquer coordenada informada.

**Parâmetros:**

| Parâmetro | Tipo   | Obrigatório | Descrição              |
|-----------|--------|-------------|-------------------------|
| `lat`     | double | Sim         | Latitude do local       |
| `lon`     | double | Sim         | Longitude do local      |

**Exemplo:**
```
GET http://localhost:8080/weather?lat=-19.9245&lon=-43.9352
```

### Resposta (ambos os endpoints)

Retorna o corpo de resposta original da API OpenWeather, em JSON, contendo temperatura, umidade, vento, descrição das condições do tempo, localização e horário da consulta, entre outros dados.

