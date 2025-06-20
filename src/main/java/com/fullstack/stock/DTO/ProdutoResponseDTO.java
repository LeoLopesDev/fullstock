package com.fullstack.stock.DTO;

import com.fullstack.stock.Enum.TipoProdutoEnum;

public record ProdutoResponseDTO(
        Long id,
        String descricao,
        TipoProdutoEnum tipoProduto,
        double valorFornecedor,
        int quantidadeEstoque
) {}
