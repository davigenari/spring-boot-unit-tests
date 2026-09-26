# Testes Unitários com Spring Boot

## Visão geral

Este projeto demonstra como implementar e verificar uma regra simples de análise de crédito usando Java, Spring Boot, JUnit 5 e Maven. O serviço calcula um limite máximo teórico para a parcela de um crédito com base na renda mensal informada.

O foco do projeto é didático: mostrar como testar um resultado esperado e como verificar o comportamento do sistema diante de uma entrada inválida. Não há, nesta versão, uma API REST, interface de usuário, integração com banco de dados ou decisão completa de concessão de crédito.

## Objetivos

- Implementar uma regra de negócio pequena e independente.
- Validar o cálculo com um teste unitário.
- Verificar o tratamento de entrada inválida por meio de uma exceção.
- Demonstrar o padrao **Arrange, Act, Assert (AAA)**.
- Executar os testes automatizados pelo Maven Wrapper.

## Regra de negocio

Para uma renda mensal válida, o limite máximo da parcela é calculado como 30% da renda:

```text
limite da parcela = renda mensal * 0.30
```

A renda precisa ser maior que zero. Quando o valor informado é zero ou negativo, `AnaliseCreditoService` lança `IllegalArgumentException` com a mensagem `A renda mensal deve ser maior que zero`.

Exemplo: para uma renda de R$ 5.000,00, o cálculo retorna R$ 1.500,00. Esse valor é apenas o resultado da regra implementada; o sistema não avalia juros, prazo, comprometimento com outras dívidas ou aprovação de crédito.

## Estrutura do projeto

```text
src/
|-- main/
|   |-- java/com/example/testeunitario/
|   |   |-- TesteunitarioApplication.java
|   |   `-- service/AnaliseCreditoService.java
|   `-- resources/application.properties
`-- test/
	`-- java/com/example/testeunitario/
		|-- TesteunitarioApplicationTests.java
		`-- service/AnaliseCreditoServiceTest.java
```

- `TesteunitarioApplication`: ponto de entrada da aplicação Spring Boot.
- `AnaliseCreditoService`: contém a validação da renda e o cálculo do limite.
- `AnaliseCreditoServiceTest`: contém os testes unitários da regra de negócio.
- `TesteunitarioApplicationTests`: verifica se o contexto da aplicação Spring consegue iniciar.
- `pom.xml`: declara as dependências, a versão do Java e a configuração Maven.

## Tecnologias e requisitos

- Java 21.
- Spring Boot 4.1.1.
- JUnit 5, disponibilizado pelas dependencias de teste do Spring Boot.
- Maven Wrapper incluído no repositório (`mvnw` e `mvnw.cmd`).

Não é necessário instalar o Maven globalmente. É necessário ter um JDK 21 instalado e disponível no `PATH`.

## Executar os testes

No Windows, abra um terminal na pasta raiz do projeto e execute:

```powershell
.\mvnw.cmd test
```

O comando baixa, se necessário, a versão de Maven configurada no wrapper, compila o projeto e executa os testes. Para executar uma compilação limpa e os testes:

```powershell
.\mvnw.cmd clean test
```

No Linux ou macOS, use `./mvnw test` ou `./mvnw clean test`.

Ao final, o Maven informa a quantidade de testes executados, falhas e erros. Os relatórios detalhados ficam em `target/surefire-reports/`.

## Passo a passo dos testes

Os testes da regra estão em `AnaliseCreditoServiceTest`. Cada método segue o padrão AAA.

### 1. Cálculo com renda válida

O teste `deveCalcularLimiteParcelaCorretamente` verifica o cenário em que a renda é positiva:

1. **Arrange (preparação):** cria uma instância de `AnaliseCreditoService` e define a renda como `5000.0`.
2. **Act (execução):** chama `calcularLimiteParcela(5000.0)` e guarda o resultado.
3. **Assert (verificação):** compara o resultado com `1500.0`, usando uma tolerância de `0.001` para a comparação de valores `double`.

O teste passa quando o método calcula corretamente 30% da renda. A tolerância evita que pequenas diferenças de representação decimal em ponto flutuante causem uma falha indevida.

### 2. Renda negativa

O teste `deveLancarExcecaoQuandoValoresNegativos` verifica a proteção contra uma entrada negativa:

1. **Arrange:** cria uma instância de `AnaliseCreditoService`.
2. **Act/Assert:** executa `calcularLimiteParcela(-1000.0)` dentro de `assertThrows`.
3. **Verificação:** confirma que a execução lança uma exceção do tipo `IllegalArgumentException`.

Se nenhuma exceção for lançada, ou se for lançada uma exceção de outro tipo, o teste falha. O teste atual confirma o tipo da exceção; ele não verifica a mensagem.

### 3. Inicialização do contexto Spring

Além dos dois testes unitários, `TesteunitarioApplicationTests` contém `contextLoads`, anotado com `@SpringBootTest`. Esse teste verifica se o contexto da aplicação Spring inicia sem erro. Ele não substitui os testes unitários da regra de cálculo.

## Cenários cobertos e próximos testes

Atualmente, os testes cobrem:

- renda positiva de `5000.0`, com resultado esperado de `1500.0`;
- renda negativa de `-1000.0`, com expectativa de `IllegalArgumentException`;
- inicialização do contexto Spring.

Para ampliar a cobertura, podem ser adicionados testes para renda igual a zero, outras rendas positivas, a mensagem da exceção e limites de arredondamento. O serviço rejeita zero pela regra `rendaMensal <= 0`, mas esse caso ainda não possui um teste dedicado.

## Palavras usadas no Termo de Teste

As palavras usadas nas 10 rodadas estão agrupadas pelo formato e pelo número de tentativas:

### Palavra única (rodadas 1–4, 6 tentativas)

1. JUNIT
2. MOCK
3. TEST
4. ASSERT

### Dueto (rodadas 5–8, 7 tentativas)

5. MOCKITO / ASSERTJ
6. VERIFY / RETURN
7. SERVICE / JUPITER
8. COVERAGE / ISOLATED

### Quarteto (rodadas 9–10, 9 tentativas)

9. BEFORE / RETURN / VERIFY / ASSERT
10. ASSERTIONS / REPOSITORY / CONTROLLER / PARAMETERS

### O que cada palavra significa no projeto

A tabela compara os termos do jogo com os conceitos de desenvolvimento e testes. Quando o projeto não usa um recurso, isso é indicado para distinguir o vocabulário da atividade das funcionalidades realmente implementadas.

#### Palavra única

| Palavra | Significado em desenvolvimento e testes | Uso neste sistema |
| --- | --- | --- |
| JUNIT | Framework Java para escrever e executar testes automatizados. | Usado com JUnit 5: a anotação `@Test` marca os testes da classe `AnaliseCreditoServiceTest` e o teste de contexto. |
| MOCK | Objeto simulado que substitui uma dependência durante o teste. | Não é usado. O serviço não depende de outros objetos e é criado diretamente com `new AnaliseCreditoService()`. |
| TEST | Caso automatizado que verifica um comportamento esperado. | Usado nos dois testes da regra de crédito e no teste `contextLoads`, que verifica a inicialização do Spring. |
| ASSERT | Verificação que compara o resultado real com o esperado. | Usado por `assertEquals` para conferir o cálculo e por `assertThrows` para conferir a exceção de renda negativa. |

#### Dueto

| Palavra | Significado em desenvolvimento e testes | Uso neste sistema |
| --- | --- | --- |
| MOCKITO | Biblioteca Java para criar mocks e verificar interações com eles. | Não há mocks nem chamadas Mockito nos testes atuais; a regra é testada diretamente, sem dependências simuladas. |
| ASSERTJ | Biblioteca de asserções com uma API fluente para expressar verificações. | Não é usada diretamente. Os testes importam as asserções do JUnit: `assertEquals` e `assertThrows`. |
| VERIFY | Verificar que uma chamada ou interação ocorreu, normalmente em um mock. | Não é usado como verificação de interação. Os testes verificam valores e exceções por meio das asserções do JUnit. |
| RETURN | Palavra-chave Java que devolve um valor de um método. | Usada em `calcularLimiteParcela` para retornar `rendaMensal * 0.30` quando a renda é válida. |
| SERVICE | Camada que concentra regras de negócio da aplicação. | Usado em `AnaliseCreditoService`, anotado com `@Service`, que valida a renda e calcula o limite da parcela. |
| JUPITER | API e modelo de programação do JUnit 5 para testes em Java. | Usado por meio de `org.junit.jupiter.api.Test`, que fornece a anotação presente nos métodos de teste. |
| COVERAGE | Medida de quanto do código foi exercitado pelos testes. | Há testes para um cálculo válido e uma renda negativa, além do carregamento do contexto. O projeto não configura relatório ou percentual de cobertura. |
| ISOLATED | Teste que avalia uma unidade sem depender de serviços externos ou de outras partes do sistema. | Os testes da regra instanciam o serviço diretamente e não carregam o contexto Spring. Separadamente, `contextLoads` testa a inicialização do contexto. |

#### Quarteto

| Palavra | Significado em desenvolvimento e testes | Uso neste sistema |
| --- | --- | --- |
| BEFORE | Configuração executada antes de cada teste, geralmente com `@BeforeEach`. | `@BeforeEach` não é usado. Cada teste cria sua própria instância do serviço dentro do próprio método. |
| ASSERTIONS | Verificações que confirmam resultados ou comportamentos esperados. | Feitas com `assertEquals` e `assertThrows`, importados de `org.junit.jupiter.api.Assertions`. |
| REPOSITORY | Componente de acesso a dados, frequentemente usado para persistência em banco. | Não há repositório nem banco de dados neste projeto. |
| CONTROLLER | Componente que recebe requisições HTTP e encaminha o processamento para a aplicação. | Não há controller nem endpoint REST implementado. |
| PARAMETERS | Valores de entrada recebidos por um método. | `calcularLimiteParcela` recebe `rendaMensal` como parâmetro `double`; os testes passam `5000.0` e `-1000.0`. |

Os termos repetidos em mais de uma rodada, como `ASSERT`, `VERIFY` e `RETURN`, aparecem uma única vez neste glossário para evitar definições duplicadas.

## Executar a aplicacao

Para iniciar a aplicacao localmente no Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Esse comando inicia a aplicação Spring Boot. A versão atual não declara controladores HTTP; portanto, não há uma rota REST de análise de crédito para acessar no navegador. A funcionalidade demonstrada é exercitada diretamente pelos testes automatizados.

## Roteiro sugerido para apresentação acadêmica

1. **Problema:** explicar a regra simplificada de limite de parcela, correspondente a 30% da renda mensal.
2. **Implementação:** apresentar `AnaliseCreditoService`, destacando a validação da entrada e o cálculo.
3. **Estratégia de teste:** introduzir o padrão AAA e explicar por que os testes verificam tanto o caminho válido quanto uma entrada inválida.
4. **Demonstração:** executar `mvnw.cmd test` e mostrar o resultado dos testes no terminal.
5. **Análise do resultado:** mostrar que o caso de R$ 5.000,00 resulta em R$ 1.500,00 e que renda negativa gera a exceção esperada.
6. **Limitações e evolução:** esclarecer que se trata de uma regra didática, não de um sistema bancário completo, e sugerir casos adicionais ou uma API como trabalhos futuros.

## Conclusao

O projeto apresenta um exemplo enxuto de teste automatizado em uma aplicação Spring Boot. A separação da regra em um serviço permite testar o cálculo sem iniciar o contexto Spring; já o teste de contexto verifica separadamente a inicialização da aplicação. Assim, os testes documentam o comportamento esperado e ajudam a identificar regressões quando a regra for alterada.
