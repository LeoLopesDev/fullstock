package com.fullstack.stock.Repository;

import com.fullstack.stock.Entity.Produto;
import com.fullstack.stock.Enum.TipoProdutoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Método que efetua a busca por tipo de produto
    List<Produto> findByTipoProduto(TipoProdutoEnum tipoProduto);
}
