package com.fullstack.stock.DTO;

import com.fullstack.stock.Enum.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentoEstoqueResponseDTO(
        Long id,
        Long produtoId,
        String produtoDescricao,
        TipoMovimentacao tipoMovimentacao,
        double valorVenda,
        LocalDateTime dataVenda,
        int quantidadeMovimentada
) {}
