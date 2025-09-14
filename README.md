# QA Demo REST API (Spring Boot)

Pequena API REST para treinar chamadas **GET/POST/PUT/DELETE** com **Spring Boot 3** em **Java 17**.
Inclui testes de API com **Rest Assured**.

## Rodar no IntelliJ IDEA

1. Abra o IntelliJ → **File > Open** → selecione a pasta `springboot-qa-demo`.
2. Aguarde o download das dependências do Maven.
3. Abra `QaDemoApplication` e clique em **Run** (setinha verde).
4. A aplicação sobe em `http://localhost:8080`.

## Endpoints

- `GET /api/todos` — lista tarefas.
- `GET /api/todos/{id}` — obtém uma tarefa.
- `POST /api/todos` — cria tarefa. **Body:** `{ "title": "..." }`
- `PUT /api/todos/{id}` — atualiza tarefa. **Body:** `{ "title": "...", "done": true }`
- `DELETE /api/todos/{id}` — remove tarefa.

### Exemplos `curl`

```bash
curl http://localhost:8080/api/todos

curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{ "title": "Estudar RestAssured" }'

curl -X PUT http://localhost:8080/api/todos/1 \
  -H "Content-Type: application/json" \
  -d '{ "title": "Novo título", "done": true }'

curl -X DELETE http://localhost:8080/api/todos/1
```

## Testes automatizados

Rode: `./mvnw test` (ou pelo IntelliJ). Os testes usam **Rest Assured** em porta aleatória.

## Observações
- Armazenamento **em memória** (sem banco).
- Validação de entrada com `spring-boot-starter-validation`.
- Tratamento de erros com `@ControllerAdvice`.
