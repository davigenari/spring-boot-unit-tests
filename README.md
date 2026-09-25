# Testes Unitários em Spring Boot 🚀

Este projeto é um exemplo prático de implementação de **Testes Unitários** em Java com **Spring Boot** e **JUnit 5**, aplicando as melhores práticas do mercado como o padrão **AAA (Arrange, Act, Assert)**.

## 📌 Funcionalidades Testadas

O projeto simula um serviço de **Análise de Crédito** (`AnaliseCreditoService`):
* **Cálculo de limite de parcela:** Regra de negócio que define que a parcela não pode ultrapassar 30% da renda mensal.
* **Tratamento de exceções:** Validação de dados de entrada que lança `IllegalArgumentException` caso valores inválidos ou negativos sejam passados.

## 🧪 Cobertura de Testes

* Validação do cálculo correto da margem de crédito.
* Teste de lançamento de erro com `assertThrows`.

## 🛠️ Tecnologias Utilizadas

* Java
* Spring Boot
* JUnit
* Maven
