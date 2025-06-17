package com.fullstack.stock.DTO;


import com.fullstack.stock.Enum.TipoProdutoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProdutoRequestDTO(

        @NotBlank(message = "O código é obrigatório.")
        String codigo,

        @NotBlank(message = "A descrição é obrigatória.")
        String descricao,

        @NotNull(message = "O tipo do produto é obrigatório.")
        TipoProdutoEnum tipoProduto,

        @PositiveOrZero(message = "O valor do fornecedor não pode ser negativo.")
        double valorFornecedor,

        @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa.")
        int quantidadeEstoque

) {}
