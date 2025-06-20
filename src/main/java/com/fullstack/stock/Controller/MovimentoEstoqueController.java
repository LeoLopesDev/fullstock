package com.fullstack.stock.Controller;

import com.fullstack.stock.DTO.MovimentoEstoqueRequestDTO;
import com.fullstack.stock.DTO.MovimentoEstoqueResponseDTO;
import com.fullstack.stock.Service.MovimentoEstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movimentos")
public class MovimentoEstoqueController {

    private final MovimentoEstoqueService movimentoEstoqueService;

    public MovimentoEstoqueController(MovimentoEstoqueService movimentoEstoqueService) {
        this.movimentoEstoqueService = movimentoEstoqueService;
    }

    @PostMapping
    public ResponseEntity<MovimentoEstoqueResponseDTO> criarMovimento(@RequestBody MovimentoEstoqueRequestDTO requestDTO) {
        MovimentoEstoqueResponseDTO responseDTO = movimentoEstoqueService.criarMovimento(requestDTO);
        URI location = URI.create("/movimentos/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<MovimentoEstoqueResponseDTO>> listarMovimentos() {
        return ResponseEntity.ok(movimentoEstoqueService.listarMovimentos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMovimento(@PathVariable BigInteger id) {
        movimentoEstoqueService.deletarMovimento(id);
        return ResponseEntity.noContent().build();
    }
}
