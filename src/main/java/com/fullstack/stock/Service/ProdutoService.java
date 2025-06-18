package com.fullstack.stock.Service;

import com.fullstack.stock.DTO.ProdutoRequestDTO;
import com.fullstack.stock.DTO.ProdutoResponseDTO;
import com.fullstack.stock.Entity.Produto;
import com.fullstack.stock.Enum.TipoProdutoEnum;
import com.fullstack.stock.Repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoResponseDTO> getAll() {
        return produtoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProdutoResponseDTO add(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = new Produto();
        produto.setCodigo(produtoRequestDTO.codigo());
        produto.setDescricao(produtoRequestDTO.descricao());
        produto.setTipoProduto(produtoRequestDTO.tipoProduto());
        produto.setValorFornecedor(produtoRequestDTO.valorFornecedor());
        produto.setQuantidadeEstoque(produtoRequestDTO.quantidadeEstoque());

        Produto produtoSalvo = produtoRepository.save(produto);
        return toResponseDTO(produtoSalvo);
    }

    public ProdutoResponseDTO getById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        return toResponseDTO(produto);
    }

    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        produto.setCodigo(produtoRequestDTO.codigo());
        produto.setDescricao(produtoRequestDTO.descricao());
        produto.setTipoProduto(produtoRequestDTO.tipoProduto());
        produto.setValorFornecedor(produtoRequestDTO.valorFornecedor());
        produto.setQuantidadeEstoque(produtoRequestDTO.quantidadeEstoque());

        Produto produtoAtualizado = produtoRepository.save(produto);
        return toResponseDTO(produtoAtualizado);
    }

    public void delete(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    public List<ProdutoResponseDTO> findByTipo(String tipoProduto) {
        try {
            var tipoEnum = TipoProdutoEnum.valueOf(tipoProduto.toUpperCase().trim());
            var produtos = produtoRepository.findByTipoProduto(tipoEnum);
            return produtos.stream().map(this::toResponseDTO).toList();
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Tipo de produto inválido: " + tipoProduto);
        }
    }

    // Método qie converte produto para produtoResponseDTO
    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getCodigo(),
                produto.getDescricao(),
                produto.getTipoProduto(),
                produto.getValorFornecedor(),
                produto.getQuantidadeEstoque()
        );
    }

}
