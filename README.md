# 💰 Sistema de Controle Financeiro Pessoal

Sistema fullstack para gerenciamento de finanças pessoais, desenvolvido com Java/Spring Boot no backend e interface web moderna no frontend.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📸 Screenshots

![Dashboard](https://via.placeholder.com/800x400?text=Dashboard+Screenshot)
*Dashboard com visão geral das finanças*

![Transações](https://via.placeholder.com/800x400?text=Transações+Screenshot)
*Lista de transações com filtros*

## 🎯 Funcionalidades

### Backend (API REST)
- ✅ CRUD completo de transações financeiras
- ✅ Cálculo automático de saldo (receitas - despesas)
- ✅ Filtros por tipo (receita/despesa) e período
- ✅ Relatório por categoria com totais e quantidade
- ✅ Validações de dados
- ✅ Persistência com PostgreSQL

### Frontend
- ✅ Dashboard interativo em tempo real
- ✅ Formulário de cadastro de transações
- ✅ Lista de transações com badges visuais
- ✅ Filtros rápidos (todas, receitas, despesas)
- ✅ Relatório visual por categoria
- ✅ Design responsivo e moderno

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.x**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **PostgreSQL** - Banco de dados relacional
- **Hibernate/JPA** - ORM
- **Maven** - Gerenciamento de dependências

### Frontend
- **HTML5**
- **CSS3** (Design moderno com gradientes)
- **JavaScript (ES6+)**
- **Fetch API** - Comunicação com backend

## 🏗️ Arquitetura

O projeto segue o padrão de arquitetura em camadas:

```
┌─────────────────────────────────────────┐
│            Frontend (HTML/CSS/JS)        │
└─────────────────┬───────────────────────┘
                  │ HTTP/REST
┌─────────────────▼───────────────────────┐
│         Controller (API REST)            │
├──────────────────────────────────────────┤
│         Service (Regras de Negócio)      │
├──────────────────────────────────────────┤
│         Repository (Acesso a Dados)      │
├──────────────────────────────────────────┤
│         Model (Entidades JPA)            │
└─────────────────┬───────────────────────┘
                  │ JDBC
┌─────────────────▼───────────────────────┐
│            PostgreSQL Database           │
└──────────────────────────────────────────┘
```

### Estrutura de Pacotes

```
com.allyson.controlegastos/
├── config/
│   └── CorsConfig.java
├── controller/
│   └── TransacaoController.java
├── dto/
│   └── RelatorioCategoriaDTO.java
├── model/
│   ├── Transacao.java
│   ├── Categoria.java
│   └── TipoTransacao.java
├── repository/
│   └── TransacaoRepository.java
├── service/
│   └── TransacaoService.java
└── ControleGastosApplication.java
```

## 📡 Endpoints da API

### Transações

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/transacoes` | Criar nova transação |
| `GET` | `/api/transacoes` | Listar todas as transações |
| `GET` | `/api/transacoes/id/{id}` | Buscar transação por ID |
| `PUT` | `/api/transacoes/{id}` | Atualizar transação |
| `DELETE` | `/api/transacoes/{id}` | Deletar transação |
| `GET` | `/api/transacoes/tipo/{tipo}` | Filtrar por tipo (RECEITA/DESPESA) |
| `GET` | `/api/transacoes/periodo` | Filtrar por período (query params: inicio, fim) |
| `GET` | `/api/transacoes/saldo` | Calcular saldo total |
| `GET` | `/api/transacoes/relatorio/categoria` | Relatório agrupado por categoria |

### Exemplos de Requisições

**Criar Transação:**
```json
POST /api/transacoes
Content-Type: application/json

{
  "descricao": "Supermercado",
  "valor": 250.00,
  "data": "2026-01-07",
  "categoria": "ALIMENTACAO",
  "tipo": "DESPESA"
}
```

**Resposta:**
```json
{
  "id": 1,
  "descricao": "Supermercado",
  "valor": 250.00,
  "data": "2026-01-07",
  "categoria": "ALIMENTACAO",
  "tipo": "DESPESA"
}
```

**Relatório por Categoria:**
```json
GET /api/transacoes/relatorio/categoria

[
  {
    "categoria": "ALIMENTACAO",
    "total": 500.00,
    "quantidade": 3
  },
  {
    "categoria": "TRANSPORTE",
    "total": 150.00,
    "quantidade": 2
  }
]
```

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- PostgreSQL 12 ou superior
- Maven 3.6 ou superior
- Navegador web moderno

### Configuração do Banco de Dados

1. Instale o PostgreSQL
2. Crie o banco de dados:
```sql
CREATE DATABASE controlegastos;
```

3. Configure as credenciais em `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/controlegastos
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Executando o Backend

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/controle-gastos.git
cd controle-gastos
```

2. Compile e execute:
```bash
mvn clean install
mvn spring-boot:run
```

Ou use sua IDE (IntelliJ IDEA, Eclipse, VS Code)

A API estará disponível em: `http://localhost:8080`

### Executando o Frontend

1. Navegue até a pasta do frontend:
```bash
cd frontend
```

2. Abra o arquivo `index.html` diretamente no navegador

Ou use um servidor local:
```bash
# Com Python 3
python -m http.server 8000

# Com Node.js (http-server)
npx http-server
```

Acesse: `http://localhost:8000`

## 📝 Variáveis de Ambiente

Crie um arquivo `.env` ou configure as seguintes variáveis:

```properties
# Database
DB_URL=jdbc:postgresql://localhost:5432/controlegastos
DB_USERNAME=postgres
DB_PASSWORD=sua_senha

# Server
SERVER_PORT=8080
```

## 🧪 Testando a API

### Com cURL

```bash
# Criar transação
curl -X POST http://localhost:8080/api/transacoes \
  -H "Content-Type: application/json" \
  -d '{
    "descricao": "Salário",
    "valor": 3000.00,
    "data": "2026-01-05",
    "categoria": "OUTROS",
    "tipo": "RECEITA"
  }'

# Listar todas
curl http://localhost:8080/api/transacoes

# Ver saldo
curl http://localhost:8080/api/transacoes/saldo
```

### Com Insomnia/Postman

Importe a collection disponível em `/docs/api-collection.json`

## 📊 Modelo de Dados

### Entidade Transacao

```java
@Entity
@Table(name = "transacoes")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
}
```

### Enums

**Categoria:**
- ALIMENTACAO
- TRANSPORTE
- MORADIA
- LAZER
- SAUDE
- EDUCACAO
- OUTROS

**TipoTransacao:**
- RECEITA
- DESPESA

## 🎨 Customização

### Alterando as Cores

Edite o arquivo `index.html`, seção `<style>`:

```css
/* Gradiente principal */
background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

/* Cores dos cards */
.receitas .value { color: #10b981; }  /* Verde */
.despesas .value { color: #ef4444; }  /* Vermelho */
.saldo .value { color: #3b82f6; }     /* Azul */
```

### Adicionando Novas Categorias

1. Adicione no enum `Categoria.java`:
```java
PETS("Pets"),
INVESTIMENTOS("Investimentos")
```

2. Adicione no select do `index.html`:
```html
<option value="PETS">Pets</option>
<option value="INVESTIMENTOS">Investimentos</option>
```

## 🔒 Segurança

### Próximas Implementações

- [ ] Autenticação JWT
- [ ] Autorização por roles
- [ ] Criptografia de dados sensíveis
- [ ] Rate limiting
- [ ] HTTPS

## 🚧 Roadmap

### Versão 2.0
- [ ] Autenticação de usuários (JWT)
- [ ] Multi-usuário
- [ ] Despesas recorrentes
- [ ] Metas de gastos por categoria
- [ ] Orçamento mensal
- [ ] Exportar relatórios (PDF/CSV)
- [ ] Gráficos interativos (Chart.js)
- [ ] Notificações por email
- [ ] App mobile (React Native)

### Versão 1.1
- [x] Migração H2 → PostgreSQL
- [x] Interface web
- [x] Relatório por categoria
- [ ] Busca por descrição
- [ ] Editar/deletar via interface
- [ ] Filtro por data no frontend
- [ ] Modo escuro

## 🤝 Contribuindo

Contribuições são bem-vindas! Siga os passos:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

### Padrões de Commit

- `feat:` Nova funcionalidade
- `fix:` Correção de bug
- `docs:` Documentação
- `style:` Formatação
- `refactor:` Refatoração
- `test:` Testes
- `chore:` Tarefas gerais


## 👤 Autor

**Allyson**

- LinkedIn:(https://www.linkedin.com/in/allyson-ramos-322b1a233/)
- Email: allysonramos350@gmail.com

## 🙏 Agradecimentos

- Spring Boot pela excelente framework
- PostgreSQL pela robustez
- Comunidade open source

---

⭐ Se este projeto foi útil para você, considere dar uma estrela!

**Desenvolvido com ☕ e muito aprendizado**
```




