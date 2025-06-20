package com.fullstack.stock.Repository;

import com.fullstack.stock.DTO.BalancoFinanceiroDTO;
import com.fullstack.stock.Entity.MovimentoEstoque;
import com.fullstack.stock.Entity.Produto;
import com.fullstack.stock.Enum.TipoProdutoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {

    // Método que busca as movimentações por tipo de produto
    List<MovimentoEstoque> findByProduto_TipoProduto(TipoProdutoEnum tipoProduto);

    @Query("SELECT new com.fullstack.stock.DTO.BalancoFinanceiroDTO( " +
            "m.produto.id, " +
            "SUM(CASE WHEN m.tipoMovimentacao = com.fullstack.stock.Enum.TipoMovimentacao.SAIDA THEN m.valorVenda * m.quantidadeMovimentada ELSE 0 END), " +
            "SUM(CASE WHEN m.tipoMovimentacao = com.fullstack.stock.Enum.TipoMovimentacao.SAIDA THEN m.produto.valorFornecedor * m.quantidadeMovimentada ELSE 0 END), " +
            "SUM(CASE WHEN m.tipoMovimentacao = com.fullstack.stock.Enum.TipoMovimentacao.SAIDA THEN (m.valorVenda - m.produto.valorFornecedor) * m.quantidadeMovimentada ELSE 0 END), " +
            "SUM(CASE WHEN m.tipoMovimentacao = com.fullstack.stock.Enum.TipoMovimentacao.SAIDA THEN m.quantidadeMovimentada ELSE 0 END) " +
            ") " +
            "FROM MovimentoEstoque m " +
            "GROUP BY m.produto.id")
    List<BalancoFinanceiroDTO> calcularBalancoFinanceiroPorProduto();
}
