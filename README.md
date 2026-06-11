
---

# 📝 **Loja Virtual (API REST)**

```markdown
# 🛒 Loja Virtual – API REST (Spring Boot)

██████╗  ██╗      ██████╗      ██╗   ██╗██╗████████╗██╗   ██╗██████╗ 
██╔══██╗ ██║     ██╔═══██╗     ██║   ██║██║╚══██╔══╝██║   ██║██╔══██╗
██████╔╝ ██║     ██║   ██║     ██║   ██║██║   ██║   ██║   ██║██████╔╝
██╔══██╗ ██║     ██║   ██║     ╚██╗ ██╔╝██║   ██║   ██║   ██║██╔══██╗
██████╔╝ ███████╗╚██████╔╝      ╚████╔╝ ██║   ██║   ╚██████╔╝██║  ██║
╚═════╝  ╚══════╝ ╚═════╝        ╚═══╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝

> API REST completa para gestão de uma Loja Virtual, com autenticação JWT, Specification, CRUDs completos, validações e arquitetura profissional.

---

## 🏷 Badges

![Java](https://img.shields.io/badge/Java-17+-red?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Build-blue?style=for-the-badge&logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-316192?style=for-the-badge&logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-Security-purple?style=for-the-badge&logo=jsonwebtokens)

---

## 🚀 Sobre o Projeto

A **Loja Virtual API** é uma aplicação backend completa construída com **Spring Boot**, oferecendo:

- Autenticação JWT  
- Multi-tenant por empresa  
- CRUDs completos  
- Paginação  
- Busca avançada com Specification  
- Delete em massa  
- Arquitetura limpa e escalável  
- Tratamento global de exceções  
- Respostas padronizadas  

---

## 🏗 Arquitetura do Projeto

```
src/main/java/com/bandampla/lojavirtual
│
├── controller/           → Endpoints REST
├── service/              → Regras de negócio
├── repository/           → Acesso ao banco (JPA)
│   └── specification/    → Filtros avançados (Specification)
│
├── dto/                  → Objetos de transferência de dados
├── model/                → Entidades JPA
├── security/             → JWT, filtros e autenticação
├── exception/            → Tratamento global de erros
└── util/                 → Classes auxiliares
```

---

## 🔐 Segurança – JWT

A API utiliza autenticação baseada em **JWT**, garantindo:

- Login seguro via `/auth/login`
- Token contendo:
  - `sub` (usuário)
  - `empresaId`
  - `roles`
- Filtro automático que injeta o usuário autenticado no contexto
- Controle de acesso por empresa (multi-tenant)

---

## 🔄 Fluxograma da API

```
                          ┌──────────────────────┐
                          │      Cliente         │
                          └──────────┬───────────┘
                                     │
                                     ▼
                         ┌────────────────────────┐
                         │   /auth/login (JWT)    │
                         └──────────┬─────────────┘
                                     │ Token JWT
                                     ▼
                    ┌──────────────────────────────────┐
                    │   Filtro JWT (Autenticação)      │
                    └──────────┬───────────────────────┘
                               │ Usuário + EmpresaId
                               ▼
                  ┌──────────────────────────────────────┐
                  │        Controller REST                │
                  └──────────┬───────────────────────────┘
                               │ DTOs
                               ▼
                  ┌──────────────────────────────────────┐
                  │             Service                   │
                  └──────────┬───────────────────────────┘
                               │ Regras de Negócio
                               ▼
                  ┌──────────────────────────────────────┐
                  │ Repository (JPA + Specification)      │
                  └──────────┬───────────────────────────┘
                               │ SQL / ORM
                               ▼
                  ┌──────────────────────────────────────┐
                  │        Banco de Dados (PostgreSQL)    │
                  └───────────────────────────────────────┘
```

---

## 🧩 Diagrama UML (Arquitetura Simplificada)

```
Controller
 ├── CategoriaProdutoController
 ├── ProdutoController
 ├── UsuarioController
 └── AuthController

Service
 ├── CategoriaProdutoService
 ├── ProdutoService
 ├── UsuarioService
 └── AuthService

Repository
 ├── CategoriaProdutoRepository
 ├── ProdutoRepository
 ├── UsuarioRepository
 └── PessoaJuridicaRepository

Specification
 ├── CategoriaProdutoSpec
 └── ProdutoSpec

Security
 ├── JwtTokenProvider
 ├── JwtAuthenticationFilter
 └── SecurityConfig
```

---

## 📚 Endpoints Principais

### 🔹 CategoriaProduto
- POST `/categoria/salvar`
- PUT `/categoria/{id}`
- DELETE `/categoria/{id}`
- DELETE `/categoria/deletar-em-massa`
- GET `/categoria/consulta`
- GET `/categoria/listar`
- GET `/categoria/paginado`
- GET `/categoria/busca-avancada`

### 🔹 Produto
- POST `/produto/salvar`
- PUT `/produto/{id}`
- DELETE `/produto/{id}`
- DELETE `/produto/deletar-em-massa`
- GET `/produto/consulta`
- GET `/produto/listar`
- GET `/produto/paginado`
- GET `/produto/busca-avancada`

### 🔹 Autenticação
- POST `/auth/login`

---

## 🔍 JPA Specification – Buscas Avançadas

Exemplo:

```java
Specification.where(CategoriaProdutoSpec.descricaoContem(descricao))
             .and(CategoriaProdutoSpec.empresaIgual(empresaId));
```

Benefícios:

- Filtros opcionais  
- Combinação dinâmica  
- Queries limpas  
- Paginação integrada  

---

## 🗄 Banco de Dados

### Tabelas principais:

- pessoa_fisica  
- pessoa_juridica  
- usuario  
- categoria_produto  
- produto  
- nota_item_produto  

### Relacionamentos:

- Produto → Categoria (ManyToOne)  
- Produto → Empresa (ManyToOne)  
- Categoria → Empresa (ManyToOne)  
- Usuário → Empresa (ManyToOne)  

---

## ▶ Como Rodar o Projeto

### 1. Clonar o repositório
```bash
git clone https://github.com/niltonbrito/loja_virtual.git
```

### 2. Configurar o banco no `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/loja_virtual
spring.datasource.username=postgres
spring.datasource.password=123456
```

### 3. Rodar o projeto
```bash
mvn spring-boot:run
```

---

## 🧩 Diferenciais Técnicos

- Arquitetura limpa (Controller → Service → Repository)  
- DTOs para entrada e saída  
- Validações com `@Valid`  
- Tratamento global de exceções  
- Respostas padronizadas com `ResponseDefaultDTO`  
- Paginação e ordenação nativas  
- Busca avançada com Specification  
- Multi-tenant por empresa via JWT  
- Delete em massa  
- Conversão DTO ↔ Entidade isolada  

---

## 🗺 Roadmap

### ✔ Concluído
- CRUD CategoriaProduto  
- CRUD Produto  
- CRUD Pessoa Física  
- CRUD Pessoa Jurídica  
- CRUD Usuário  
- Login JWT  
- Filtro automático por empresa  
- Paginação  
- Specification  
- Delete em massa  
- Respostas padronizadas  

### 🚧 Em desenvolvimento
- Upload de imagens  
- Sistema de pedidos  
- Carrinho de compras  
- Auditoria  
- Dashboard  

### 🛠 Futuro
- Microsserviços  
- Kafka / RabbitMQ  
- Redis Cache  
- Docker / Kubernetes  

---

## 👨‍💻 Autor

**Nilton Brito**  
Desenvolvedor Backend Java  
📧 <nilton.brito@outlook.com>  
🔗 GitHub: [https://github.com/niltonbrito](https://github.com/niltonbrito)

---

## ⭐ Contribuições

Pull requests são bem-vindos!  
Sinta-se à vontade para abrir issues com sugestões ou melhorias.
