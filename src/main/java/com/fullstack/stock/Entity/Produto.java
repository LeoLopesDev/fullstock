package com.fullstack.stock.Entity;

import com.fullstack.stock.Enum.TipoProdutoEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_produto")
    private TipoProdutoEnum tipoProduto;

    @Column(name = "valor_fornecedor")
    private double valorFornecedor;

    @Column(name = "quantidade_estoque")
    private int quantidadeEstoque;

    //Construtor mantido como boa prática para evoluções futuras
    public Produto(long id, String codigo, String descricao, TipoProdutoEnum tipoProduto, double valorFornecedor, int quantidadeEstoque) {
        this.id = id;
        this.descricao = descricao;
        this.tipoProduto = tipoProduto;
        this.valorFornecedor = valorFornecedor;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Produto() {
    }

    public long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoProdutoEnum getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProdutoEnum tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public double getValorFornecedor() {
        return valorFornecedor;
    }

    public void setValorFornecedor(double valorFornecedor) {
        this.valorFornecedor = valorFornecedor;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}
