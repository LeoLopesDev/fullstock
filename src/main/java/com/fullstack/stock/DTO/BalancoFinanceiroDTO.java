package com.fullstack.stock.DTO;

public record BalancoFinanceiroDTO(
        long produtoId,
        double receita,
        double custo,
        double lucro,
        long quantidadeSaida
) {}
