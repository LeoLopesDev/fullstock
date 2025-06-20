package com.fullstack.stock.Service;

import com.fullstack.stock.DTO.MovimentoEstoqueRequestDTO;
import com.fullstack.stock.DTO.MovimentoEstoqueResponseDTO;
import com.fullstack.stock.Entity.MovimentoEstoque;
import com.fullstack.stock.Enum.TipoMovimentacao;
import com.fullstack.stock.Repository.MovimentoEstoqueRepository;
import com.fullstack.stock.Repository.ProdutoRepository;
import com.fullstack.stock.Entity.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return new MovimentoEstoqueResponseDTO(
                salvo.getId(),
                salvo.getProduto().getId(),
                salvo.getTipoMovimentacao(),
                salvo.getValorVenda(),
                salvo.getDataVenda(),
                salvo.getQuantidadeMovimentada()
        );
    }

    public List<MovimentoEstoqueResponseDTO> listarMovimentos() {
        return movimentoEstoqueRepository.findAll().stream()
                .map(movimento -> new MovimentoEstoqueResponseDTO(
                        movimento.getId(),
                        movimento.getProduto().getId(),
                        movimento.getTipoMovimentacao(),
                        movimento.getValorVenda(),
                        movimento.getDataVenda(),
                        movimento.getQuantidadeMovimentada()
                )).collect(Collectors.toList());
    }

    @Transactional
    public void deletarMovimento(Long id) {
        MovimentoEstoque movimento = movimentoEstoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimento não encontrado"));

        Produto produto = movimento.getProduto();

        if (movimento.getTipoMovimentacao() == TipoMovimentacao.ENTRADA) {
            if (produto.getQuantidadeEstoque() < movimento.getQuantidadeMovimentada()) {
                throw new RuntimeException("Não é possível remover esta entrada pois resultaria em estoque negativo.");
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - movimento.getQuantidadeMovimentada());
        } else if (movimento.getTipoMovimentacao() == TipoMovimentacao.SAIDA) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + movimento.getQuantidadeMovimentada());
        }

        produtoRepository.save(produto);
        movimentoEstoqueRepository.delete(movimento);
    }



}
