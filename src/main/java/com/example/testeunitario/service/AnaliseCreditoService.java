package com.example.testeunitario.service;

import org.springframework.stereotype.Service;

@Service
public class AnaliseCreditoService {

    // Método responsável por calcular o valor máximo que a parcela pode ter
    public double calcularLimiteParcela(double rendaMensal) {

        // Regra de Validação: A renda não pode ser menor ou igual a zero. Caso o valor seja inválido, o código exibe uma mensagem de erro.
        if (rendaMensal <= 0) {
            throw new IllegalArgumentException("A renda mensal deve ser maior que zero");
        }
        // Regra de Negócio (service): O limite da parcela é no máximo 30% (0.30) do salário do cliente.
        return rendaMensal * 0.30;
    }
}