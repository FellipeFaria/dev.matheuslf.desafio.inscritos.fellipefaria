# 🚀 Gerenciador de Projetos (Clean Architecture)

API RESTful para gestão de projetos e tarefas (demandas), desenvolvida com foco em boas práticas de engenharia de software, utilizando **Clean Architecture** e **Domain-Driven Design (DDD)**.

Este projeto permite criar projetos, gerenciar suas tarefas, controlar prazos e atualizar status seguindo regras de negócio específicas.

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3.5.8**
* **Spring Data JPA** (Persistência)
* **PostgreSQL 15** (Banco de Dados)
* **Flyway** (Migração e Versionamento de Banco de Dados)
* **Docker & Docker Compose** (Containerização)
* **Bean Validation** (Validação de DTOs)
* **Lombok** (Redução de código repetitivo na Infraestrutura)

---

## 🏗️ Arquitetura do Projeto

O projeto segue estritamente a **Clean Architecture**, dividindo as responsabilidades em camadas para desacoplar as regras de negócio de frameworks e bibliotecas externas.

### Estrutura de Pastas
```text
src/main/java/dev/matheuslf/desafio/inscritos
├── application      # Casos de uso (Regras da Aplicação) - Agnóstico ao framework
│   ├── usecases     # Implementações e Interfaces (ex: CreateProjectUseCase)
│   └── exceptions   # Exceções de negócio
├── domain           # Núcleo (Entidades, Value Objects, Gateways)
│   ├── model        # Entidades ricas (Project, Task)
│   ├── gateway      # Interfaces de Repositório (Inversão de Dependência)
│   ├── valueobjects # Objetos de Valor (Name, Title, Date)
│   └── enums        # Constantes de Domínio (TaskStatus, TaskPriority)
└── infra            # Camada Externa (Frameworks, Banco de Dados, Web)
    ├── api          # Controllers e DTOs
    ├── config       # Configuração de Beans do Spring
    ├── mappers      # Conversores (Domain <-> Infra)
    ├── persistence  # Entidades JPA e Repositórios Spring Data
    ├── exception    # Tratamento global de erros (GlobalExceptionHandler)
    └── service      # Implementação dos Gateways
