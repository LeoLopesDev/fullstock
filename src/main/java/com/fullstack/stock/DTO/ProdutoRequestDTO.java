package com.fullstack.stock.DTO;


import com.fullstack.stock.Enum.TipoProdutoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProdutoRequestDTO(
        String id,
        String descricao,
        TipoProdutoEnum tipoProduto,
        double valorFornecedor,
        int quantidadeEstoque
) {}
