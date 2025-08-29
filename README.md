# Transfer Scheduler (Java 11 + Spring Boot 2.7 + H2 + Vue 3/Vite)

Implementação de um sistema de **agendamento de transferências financeiras** usando **Java 11**, **Spring Boot**, **JPA/H2** e **Vue 3/Vite**.  
O sistema calcula taxas de acordo com regras de negócio, impede transferências inválidas e exibe um extrato em tempo real.

---

## Versões Utilizadas
- Java 11
- Spring Boot 2.7.x
- Gradle 7.x
- Node.js 18+
- Vue.js 3 + Vite
- H2 Database (dev)

## Visão Geral

- **Backend (API REST)**  
  Implementado em Java 11 com Spring Boot 2.7.  
  Regras de negócio, cálculos de taxas, persistência em banco (H2 em memória) e validações.  

- **Frontend (SPA)**  
  Implementado em Vue 3 (Composition API) com Vite.  
  Permite cadastrar transferências, simular taxas e visualizar um extrato atualizado.  

- **Banco de Dados**  
  Ambiente de desenvolvimento usa H2 (memória).  

---

### Docker Compose

**Requisitos:** Docker + Docker Compose.

```bash
cd transfer-scheduler
docker compose up --build
```

- Backend: http://localhost:8080  
- H2 Console: http://localhost:8080/h2  
  - JDBC URL: jdbc:h2:mem:transfersdb  
  - Usuário: sa  
  - Senha: (vazio)  
- Frontend (Vite): http://localhost:5173  

O frontend usa proxy de desenvolvimento para http://backend:8080 dentro do Compose.

---

## Endpoints da API

### POST /api/transfers
Agenda uma transferência.  
Exemplo body:
```json
{
  "fullName": "Misrael Wilk",
  "cpf": "12345678901",
  "sourceAccount": "1111111111",
  "targetAccount": "2222222222",
  "amount": 100.00,
  "transferDate": "2025-09-01"
}
```

### GET /api/transfers
Lista todos os agendamentos.

### GET /api/transfers/quote?amount=100&transferDate=2025-09-01
Simula taxa e valor líquido para uma transferência.

---

## Validações

- Contas: exatamente 10 dígitos (XXXXXXXXXX).  
- Valor: deve ser positivo.  
- Data de transferência: não pode ser anterior a hoje (UTC-3/SP).  
- Taxa: se o valor for menor ou igual à taxa → operação bloqueada.  

---

## Decisões Arquiteturais

- **Backend em camadas**  
  - Controller → expõe endpoints.  
  - Service → centraliza regras de negócio e cálculos.  
  - Repository → abstrai persistência.  
  - Entity/DTO → isolamento entre modelo de banco e contrato da API.  

- **Tratamento de erros**  
  - Uso de ResponseStatusException para mensagens claras.  
  - Respostas padronizadas em JSON.  

- **Frontend**  
  - Vue 3 com Composition API.  
  - Componentes isolados: ScheduleForm.vue e TransferList.vue.  
  - Formatação de CPF, moeda e datas no lado do cliente.  

- **Banco de dados**  
  - H2 em memória no ambiente dev.  

- **Infraestrutura**  
  - Gradle para build.  
  - Docker Compose para subir frontend, backend e banco juntos.  
