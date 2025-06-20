package com.fullstack.stock.Controller;

import com.fullstack.stock.DTO.ProdutoRequestDTO;
import com.fullstack.stock.DTO.ProdutoResponseDTO;
import com.fullstack.stock.Service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> add(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO response = produtoService.add(produtoRequestDTO);
        return ResponseEntity
                .created(URI.create("/produtos/" + response.id()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAll() {
        List<ProdutoResponseDTO> response = produtoService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getById(@PathVariable Long id) {
        ProdutoResponseDTO response = produtoService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
        produtoService.update(id, produtoRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable BigInteger id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipoProduto}")
    public ResponseEntity<List<ProdutoResponseDTO>> findByTipo(@PathVariable String tipoProduto) {
        List<ProdutoResponseDTO> produtos = produtoService.findByTipo(tipoProduto);
        return ResponseEntity.ok(produtos);
    }

}
