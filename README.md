# 📚 Listas de Atividades - Desenvolvimento Web 3

## 📖 Sobre o Repositório

Este repositório contém as listas de atividades práticas desenvolvidas para a disciplina de **Desenvolvimento Web 3** na **FATEC Prof. Jessen Vidal**.

As atividades foram desenvolvidas com o objetivo de praticar e consolidar os conhecimentos obtidos durante o curso, abordando diferentes tecnologias e conceitos fundamentais do desenvolvimento web moderno.

## 🎯 Estrutura do Repositório

O repositório está organizado em **4 listas de atividades (ATVs)**, cada uma focando em aspectos específicos do desenvolvimento web com Spring Framework:

### 📁 **ATV1** - Spring Boot Básico
- **Tecnologias**: Spring Boot 2.6.3, JPA, H2 Database
- **Objetivo**: Introdução ao Spring Boot e conceitos básicos de API REST
- **Conteúdo**: CRUD básico, configuração de banco de dados, endpoints fundamentais

### 📁 **ATV2** - HATEOAS Implementation
- **Tecnologias**: Spring Boot 2.6.4, Spring HATEOAS, JPA
- **Objetivo**: Implementação de REST APIs com HATEOAS (Hypermedia as the Engine of Application State)
- **Conteúdo**: Links hipermídia, navegação entre recursos, maturidade Richardson nível 3

### 📁 **ATV3** - Sistema CRUD Completo
- **Tecnologias**: Spring Boot 2.6.7, Spring HATEOAS, JPA, H2
- **Objetivo**: Desenvolvimento de sistema CRUD completo com HATEOAS
- **Conteúdo**: Operações CRUD avançadas, relacionamentos entre entidades, documentação completa

### 📁 **ATV4** - Autenticação JWT
- **Tecnologias**: Spring Boot 2.7.0, Spring Security 6.x, JWT, JPA
- **Objetivo**: Sistema de autenticação e autorização com JWT
- **Conteúdo**: Login/logout, controle de acesso por perfis, filtros de segurança, tokens JWT

## 🚀 Como Executar os Projetos

Cada projeto possui seu próprio `README.md` com instruções específicas de execução. De forma geral:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/IvanSuiyama/ATV_AUTOBOTS_novo.git
   cd ATV_AUTOBOTS_novo
   ```

2. **Navegue para o projeto desejado**:
   ```bash
   cd atv1  # ou atv2, atv3, atv4
   ```

3. **Execute o projeto**:
   ```bash
   ./mvnw spring-boot:run
   # ou
   mvn spring-boot:run
   ```

## 🌿 Organização das Branches

O repositório utiliza branches separadas para organizar cada atividade:

- **`master`**: Branch principal contendo todos os projetos
- **`Lista01`**: ATV1 - Spring Boot básico
- **`Lista02`**: ATV2 - HATEOAS
- **`Lista03`**: ATV3 - Sistema CRUD completo
- **`Lista04`**: ATV4 - Autenticação JWT

## 🛠️ Pré-requisitos

Para executar os projetos, certifique-se de ter instalado:

- **Java 11** ou superior
- **Maven 3.6+** (ou use o wrapper incluído)
- **IDE de sua preferência** (IntelliJ IDEA, Eclipse, VS Code)

## 📋 Funcionalidades Implementadas

### Recursos Comuns
- ✅ API REST completa
- ✅ Banco de dados H2 (desenvolvimento)
- ✅ Documentação de endpoints
- ✅ Testes com comandos curl

### Recursos Avançados (ATV3 e ATV4)
- ✅ HATEOAS para navegação hipermídia
- ✅ Sistema de autenticação JWT
- ✅ Controle de acesso baseado em perfis
- ✅ Filtros de segurança personalizados
- ✅ Relacionamentos JPA complexos

## 🎓 Disciplina

**Desenvolvimento Web 3**  
**FATEC Prof. Jessen Vidal**

## 📝 Observações

Cada ATV representa uma evolução no aprendizado, partindo de conceitos básicos até implementações mais complexas com segurança e padrões avançados de REST APIs.

---

*Desenvolvido para fins acadêmicos na disciplina de Desenvolvimento Web 3*