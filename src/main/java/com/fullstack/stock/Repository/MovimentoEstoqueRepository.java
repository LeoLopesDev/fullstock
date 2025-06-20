package com.fullstack.stock.Repository;

import com.fullstack.stock.Entity.MovimentoEstoque;
import com.fullstack.stock.Entity.Produto;
import com.fullstack.stock.Enum.TipoProdutoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {

    // Método que busca as movimentações por tipo de produto
    List<MovimentoEstoque> findByProduto_TipoProduto(TipoProdutoEnum tipoProduto);
}
