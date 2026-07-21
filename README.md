# 🛒 Basket Service

Microsserviço de carrinho de compras desenvolvido com **Spring Boot**, responsável por gerenciar o ciclo de vida dos carrinhos de compras em uma arquitetura de e-commerce. Integra-se com a [Platzi Store API](https://api.escuelajs.co/api/v1) para validação e enriquecimento de dados de produtos.

---

## 🧰 Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 4.1.0 |
| Spring Cloud (OpenFeign) | 2025.1.2 |
| MongoDB | 4 |
| Redis | Latest |
| Lombok | Latest |
| Maven | Wrapper incluso |

---

## 🏗️ Arquitetura

```
basketservice/
├── client/             # Integração via Feign com a Platzi Store API
│   └── response/       # DTOs de resposta do cliente externo
├── controller/         # Endpoints REST
│   ├── request/        # DTOs de entrada
│   └── ...
├── entity/             # Documentos MongoDB (Basket, Product, Status, PaymentMethod)
├── exceptions/         # Exceções personalizadas (BusinessException, DataNotFoundException)
├── repository/         # Interface do repositório MongoDB
└── service/            # Regras de negócio (BasketService, ProductService)
```

---

## 📡 Endpoints

### Carrinho (`/basket`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/basket/{id}` | Busca um carrinho pelo ID |
| `POST` | `/basket` | Cria um novo carrinho |
| `PUT` | `/basket/{id}` | Atualiza os produtos de um carrinho |
| `PUT` | `/basket/{id}/payment` | Realiza o pagamento de um carrinho |
| `DELETE` | `/basket/{id}` | Remove um carrinho |

### Produtos (`/products`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/products` | Lista todos os produtos da Platzi Store |
| `GET` | `/products/{id}` | Busca um produto pelo ID |

---

## 🗃️ Modelo de Dados

### Basket (MongoDB)

```json
{
  "id": "string",
  "client": 1,
  "totalPrice": 99.99,
  "paymentMethod": "CREDIT_CARD",
  "producs": [
    {
      "id": 1,
      "title": "Product Name",
      "price": 49.99,
      "quantity": 2
    }
  ],
  "status": "OPEN"
}
```

**Status possíveis:** `OPEN`, `SOLD`

---

## ⚙️ Configuração

### `application.yaml`

```yaml
spring:
  application:
    name: basket-service

  data:
    mongodb:
      host: localhost
      port: 27017
      database: basket-service
    redis:
      host: localhost
      port: 6379
      password: sa

  cache:
    redis:
      time-to-live: 60000   # TTL em milissegundos (1 minuto)

basket:
  client:
    platzi: https://api.escuelajs.co/api/v1
```

---

## 🐳 Executando com Docker

O projeto inclui um `docker-compose.yml` para subir as dependências (MongoDB e Redis) localmente.

```bash
# Subir os containers
docker-compose up -d

# Verificar se os containers estão rodando
docker ps
```

Serviços disponibilizados:

| Serviço | Porta |
|---|---|
| MongoDB | `27017` |
| Redis | `6379` |

---

## 🚀 Executando a Aplicação

### Pré-requisitos

- Java 17+
- Docker e Docker Compose

### Passos

```bash
# 1. Clone o repositório
git clone <url-do-repositorio>
cd basketservice

# 2. Suba as dependências com Docker
docker-compose up -d

# 3. Execute a aplicação
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 🧪 Testes

```bash
# Executar todos os testes
./mvnw test

# Executar testes com relatório
./mvnw verify
```

---

## 📦 Build

```bash
# Gerar o JAR
./mvnw clean package

# Executar o JAR gerado
java -jar target/basketservice-0.0.1-SNAPSHOT.jar
```

---

## 🔁 Regras de Negócio

- Um cliente só pode ter **um carrinho com status `OPEN`** por vez. Tentar criar um segundo carrinho retorna erro de negócio.
- O **preço total** do carrinho é calculado automaticamente com base em `preço × quantidade` de cada produto.
- Os dados dos produtos são buscados em tempo real na **Platzi Store API** via OpenFeign.
- Produtos são cacheados no **Redis** com TTL de **1 minuto** para reduzir chamadas externas.
- Ao efetuar o pagamento, o status do carrinho é alterado para `SOLD` e o método de pagamento é registrado.
