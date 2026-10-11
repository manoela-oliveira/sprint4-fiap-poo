package br.com.motiva.sistemamotiva.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.motiva.sistemamotiva.model.TrechoRodovia;
import br.com.motiva.sistemamotiva.service.TrechoRodoviaService;

@RestController
@RequestMapping("/api/trechos")
public class TrechoRodoviaController {

    private final TrechoRodoviaService service;

    public TrechoRodoviaController(TrechoRodoviaService service) {
        this.service = service;
    }

    // GET /api/trechos - Lista todos
    @GetMapping
    public ResponseEntity<List<TrechoRodovia>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // GET /api/trechos/{id} - Busca por ID
    @GetMapping("/{id}")
    public ResponseEntity<TrechoRodovia> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/trechos/filtro/vegetacao?minimo=30.0 - Teste de Derived Query
    @GetMapping("/filtro/vegetacao")
    public ResponseEntity<List<TrechoRodovia>> filtrarPorVegetacao(@RequestParam Double minimo) {
        return ResponseEntity.ok(service.buscarPorVegetacaoMinima(minimo));
    }

    // POST /api/trechos - Cria novo trecho (Autoestrada ou Estrada Vicinal)
    @PostMapping
    public ResponseEntity<TrechoRodovia> criar(@RequestBody TrechoRodovia trecho) {
        TrechoRodovia novoTrecho = service.salvar(trecho);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTrecho);
    }

    // PUT /api/trechos/{id} - Atualiza trecho existente
    @PutMapping("/{id}")
    public ResponseEntity<TrechoRodovia> atualizar(@PathVariable Long id, @RequestBody TrechoRodovia trecho) {
        return service.atualizar(id, trecho)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/trechos/{id} - Remove trecho
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}