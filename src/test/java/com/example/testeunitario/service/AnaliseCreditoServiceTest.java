package com.example.testeunitario.service;

// Importação das asserções do JUnit 5 (métodos que confirmam se o resultado foi o esperado)
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Importação da anotação @Test para identificar os métodos de teste
import org.junit.jupiter.api.Test;

public class AnaliseCreditoServiceTest {

    //Verifica se o cálculo dos 30% é feito corretamente quando o cliente fornece uma renda válida. (Todos os dados são válidos).
    @Test
    void deveCalcularLimiteParcelaCorretamente() {
        // Arrange: Instancia a classe service e define as entradas do teste.
        AnaliseCreditoService service = new AnaliseCreditoService();
        double rendaMensal = 5000.0;

        // Act: Executa o método que será testado.
        double limiteObtido = service.calcularLimiteParcela(rendaMensal);

        // Assert (Verificação): Confirma se o resultado obtido bate com o resultado esperado.
        assertEquals(1500.0, limiteObtido, 0.001);
    }

    //Teste de exceção (dados inválidos). Verifica se o sistema bloqueia rendas negativas lançando a exceção esperada.
    @Test
    void deveLancarExcecaoQuandoValoresNegativos() {
        // Arrange (Preparação): Instancia a classe service para o teste.
        AnaliseCreditoService service = new AnaliseCreditoService();

        //Act/Assert: O método "assertThrows" garante que, ao tentar calcular para um valor negativo, o método dispara uma exceção do tipo IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> {
            service.calcularLimiteParcela(-1000.0);
        });
    }
}