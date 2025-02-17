# 📌 Gerenciador de Tarefas - API REST com Spring Boot  

Este é um backend para um **Gerenciador de Tarefas**, desenvolvido com **Spring Boot**, **Spring Security (JWT)** e **PostgreSQL**. Ele permite que os usuários criem contas, façam login e gerenciem suas tarefas de forma segura.  

---

## 🚀 Tecnologias Utilizadas  
- **Java 17**  
- **Spring Boot 3**  
- **Spring Security & JWT**  
- **Spring Data JPA & Hibernate**  
- **PostgreSQL**  
- **Lombok**  
- **Swagger (OpenAPI)**  

---

## 📌 Funcionalidades  
✅ Cadastro e autenticação de usuários (JWT)  
✅ CRUD de tarefas (Criar, Listar, Atualizar, Deletar)  
✅ Proteção de endpoints com autenticação  
✅ Filtragem de tarefas por status (pendente/concluída)  
✅ Documentação da API com Swagger  

---

## ⚙️ Como Rodar o Projeto Localmente  

### 1️⃣ Pré-requisitos  
- Ter **Java 17** instalado  
- Ter **PostgreSQL** instalado e rodando  

### 2️⃣ Configurar o Banco de Dados  
Crie um banco de dados PostgreSQL chamado `gerenciador_tarefas` e atualize o `application.properties`:  

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_tarefas
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Rodar o Projeto  
Clone o repositório e execute os seguintes comandos:  

```bash
git clone https://github.com/pmiguell/gerenciador-tarefas.git
cd gerenciador-tarefas
mvn spring-boot:run
```

A API estará disponível em http://localhost:8080.

## 🛠 Endpoints da API  

### 📌 Autenticação  

| Método | Endpoint        | Descrição              | Autenticação |
|--------|----------------|------------------------|--------------|
| POST   | `/auth/register` | Cadastrar um novo usuário | ❌ |
| POST   | `/auth/login`    | Login (gera JWT)        | ❌ |

### 📌 Tarefas  

| Método | Endpoint                 | Descrição                                    | Autenticação |
|--------|---------------------------|----------------------------------------------|--------------|
| GET    | `/tasks`                  | Obtém todas as tarefas do usuário autenticado | ✅ |
| POST   | `/tasks`                  | Cria uma nova tarefa                         | ✅ |
| GET    | `/tasks/{id}`             | Obtém uma tarefa específica por ID           | ✅ |
| PUT    | `/tasks/{id}`             | Atualiza uma tarefa existente                | ✅ |
| DELETE | `/tasks/{id}`             | Deleta uma tarefa por ID                     | ✅ |
| DELETE | `/tasks`                  | Deleta todas as tarefas do usuário autenticado | ✅ |
| PATCH  | `/tasks/conclude/{id}`    | Marca uma tarefa como **CONCLUÍDA**          | ✅ |
| PATCH  | `/tasks/undo-conclude/{id}` | Desfaz a conclusão de uma tarefa             | ✅ |
| GET    | `/tasks/filter`           | Filtra tarefas com base em critérios         | ✅ |
| GET    | `/tasks/categories`       | Obtém todas as categorias de tarefas do usuário | ✅ |

✅ **Autenticação:** Todos os endpoints de **tarefas** exigem um token JWT no cabeçalho:  
```http
Authorization: Bearer <seu_token>
```

## 📖 Documentação da API  

Após iniciar a aplicação localmente, a documentação completa da API pode ser acessada pelo **Swagger UI** no seguinte endereço:  
🔗 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)  
