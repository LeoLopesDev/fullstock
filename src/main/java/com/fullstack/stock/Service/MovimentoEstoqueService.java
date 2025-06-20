package com.fullstack.stock.Service;

import com.fullstack.stock.DTO.MovimentoEstoqueRequestDTO;
import com.fullstack.stock.DTO.MovimentoEstoqueResponseDTO;
import com.fullstack.stock.Entity.MovimentoEstoque;
import com.fullstack.stock.Enum.TipoMovimentacao;
import com.fullstack.stock.Enum.TipoProdutoEnum;
import com.fullstack.stock.Repository.MovimentoEstoqueRepository;
import com.fullstack.stock.Repository.ProdutoRepository;
import com.fullstack.stock.Entity.Produto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentoEstoqueService {

    private final MovimentoEstoqueRepository movimentoEstoqueRepository;
    private final ProdutoRepository produtoRepository;

    public MovimentoEstoqueService(MovimentoEstoqueRepository movimentoEstoqueRepository, ProdutoRepository produtoRepository) {
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public MovimentoEstoqueResponseDTO criarMovimento(MovimentoEstoqueRequestDTO requestDTO) {
        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        int quantidadeMov = requestDTO.quantidadeMovimentada();

        if (requestDTO.tipoMovimentacao() == TipoMovimentacao.SAIDA) {

            if (produto.getQuantidadeEstoque() < quantidadeMov) {
                throw new RuntimeException("Estoque insuficiente para esta saída");
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeMov);
        } else if (requestDTO.tipoMovimentacao() == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidadeMov);
        }

        produtoRepository.save(produto);

        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setProduto(produto);
        movimento.setTipoMovimentacao(requestDTO.tipoMovimentacao());
        movimento.setValorVenda(requestDTO.valorVenda());
        movimento.setDataVenda(requestDTO.dataVenda());
        movimento.setQuantidadeMovimentada(quantidadeMov);

        MovimentoEstoque salvo = movimentoEstoqueRepository.save(movimento);

        return toResponseDTO(salvo);
    }

    public List<MovimentoEstoqueResponseDTO> listarMovimentos() {
        return movimentoEstoqueRepository.findAll().stream()
                .map(movimento -> new MovimentoEstoqueResponseDTO(
                        movimento.getId(),
                        movimento.getProduto().getId(),
                        movimento.getProduto().getDescricao(),
                        movimento.getTipoMovimentacao(),
                        movimento.getValorVenda(),
                        movimento.getDataVenda(),
                        movimento.getQuantidadeMovimentada()
                )).collect(Collectors.toList());
    }

    @Transactional
    public void deletarMovimento(BigInteger id) {
        MovimentoEstoque movimento = movimentoEstoqueRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Movimento não encontrado"));

        Produto produto = movimento.getProduto();

        if (movimento.getTipoMovimentacao() == TipoMovimentacao.ENTRADA) {
            if (produto.getQuantidadeEstoque() < movimento.getQuantidadeMovimentada()) {
                throw new RuntimeException("Estoque insuficiente para esta movimentação.");
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - movimento.getQuantidadeMovimentada());
        } else if (movimento.getTipoMovimentacao() == TipoMovimentacao.SAIDA) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + movimento.getQuantidadeMovimentada());
        }

        produtoRepository.save(produto);
        movimentoEstoqueRepository.delete(movimento);
    }

    public List<MovimentoEstoqueResponseDTO> findByTipo(String tipoProduto) {
        TipoProdutoEnum tipoEnum;

        try {
            tipoEnum = TipoProdutoEnum.valueOf(tipoProduto);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de produto inválido");
        }

        return movimentoEstoqueRepository.findByProduto_TipoProduto(tipoEnum)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    //Implementação do métoodo de conversão en entity para dto
    private MovimentoEstoqueResponseDTO toResponseDTO(MovimentoEstoque movimento) {
        return new MovimentoEstoqueResponseDTO(
                movimento.getId(),
                movimento.getProduto().getId(),
                movimento.getProduto().getDescricao(),
                movimento.getTipoMovimentacao(),
                movimento.getValorVenda(),
                movimento.getDataVenda(),
                movimento.getQuantidadeMovimentada()
        );
    }
}
