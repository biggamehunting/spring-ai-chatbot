# Spring AI Chatbot

A small runnable starter project built with Java 17, Spring Boot 3.4, Spring AI 1.0, a REST endpoint, and a browser UI.

## Features

- Chat UI served at `/`
- `POST /api/chat` REST endpoint
- Spring AI `ChatClient`
- OpenAI chat model configured by environment variables
- Basic request validation and an 8,000-character message limit
- Dockerfile and Docker Compose
- API key is read from the environment, not committed to source control

## Prerequisites

- JDK 17+
- Maven 3.9+ (or use the Maven wrapper after generating one)
- An API key for the configured model provider

## Run locally (Windows PowerShell)

```powershell
$env:OPENAI_API_KEY = "your-real-api-key"
$env:OPENAI_MODEL = "gpt-4o-mini"
mvn spring-boot:run
```

Open http://localhost:8080.

Do not put your real key in Git or share screenshots/logs that expose it. API usage may incur provider charges.

## Test the API

```powershell
$body = @{ message = "Explain dependency injection in Spring in two sentences." } | ConvertTo-Json
Invoke-RestMethod -Uri http://localhost:8080/api/chat -Method Post -ContentType "application/json" -Body $body
```

Example response:

```json
{"answer":"..."}
```

## Run with Docker

PowerShell:

```powershell
$env:OPENAI_API_KEY = "your-real-api-key"
$env:OPENAI_MODEL = "gpt-4o-mini"
docker compose up --build
```

Open http://localhost:8080. The key is passed into the container as an environment variable.

## Configuration

| Variable | Purpose | Default |
|---|---|---|
| `OPENAI_API_KEY` | Provider API key (required) | none |
| `OPENAI_MODEL` | Model name supported by your account/provider | `gpt-4o-mini` |

If you use a different provider, confirm that it supports the OpenAI-compatible API and adjust the Spring AI provider dependency and configuration accordingly. A Grok subscription does not automatically mean API access is included; API billing/access can be separate from a consumer subscription.

## Endpoint

`POST /api/chat`

Request:

```json
{"message":"What is Spring AI?"}
```

Response:

```json
{"answer":"Spring AI is ..."}
```

## Architecture

1. Browser sends a JSON request to `/api/chat`.
2. `ChatController` validates the message.
3. Spring AI `ChatClient` sends the prompt to the configured model provider.
4. The model response is returned as JSON and shown in the browser.

## Limitations / next improvements

This is a starter, not a production-ready RAG chatbot. It has no conversation persistence, authentication, rate limiting, streaming, retrieval/vector database, or observability integration. Add those deliberately before exposing it publicly. The dependencies have not been downloaded or compiled in this generated environment, so run the commands above to verify the build in your setup.
