package com.fullstack.stock.Entity;

import com.fullstack.stock.Enum.TipoMovimentacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimento_estoque")
public class MovimentoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "tipo_movimentacao")
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    @Column(name = "valor_venda")
    private double valorVenda;

    @Column(name = "data_venda")
    private LocalDateTime dataVenda;

    @Column(name = "quantidade_movimentada")
    private int quantidadeMovimentada;

    //Construtor mantido como boa prática visando futuras evoluções
    public MovimentoEstoque(long id, Produto produto, TipoMovimentacao tipoMovimentacao, double valorVenda, LocalDateTime dataVenda, int quantidadeMovimentada) {
        this.id = id;
        this.produto = produto;
        this.tipoMovimentacao = tipoMovimentacao;
        this.valorVenda = valorVenda;
        this.dataVenda = dataVenda;
        this.quantidadeMovimentada = quantidadeMovimentada;
    }

    public MovimentoEstoque() {}

    public long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getQuantidadeMovimentada() {
        return quantidadeMovimentada;
    }

    public void setQuantidadeMovimentada(int quantidadeMovimentada) {
        this.quantidadeMovimentada = quantidadeMovimentada;
    }
}
