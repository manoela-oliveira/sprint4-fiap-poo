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
import org.springframework.web.bind.annotation.RestController;

import br.com.motiva.sistemamotiva.model.IntervencaoOperacional;
import br.com.motiva.sistemamotiva.service.IntervencaoOperacionalService;

@RestController
@RequestMapping("/api/intervencoes")
public class IntervencaoOperacionalController {

    private final IntervencaoOperacionalService service;

    public IntervencaoOperacionalController(IntervencaoOperacionalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<IntervencaoOperacional>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntervencaoOperacional> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IntervencaoOperacional> criar(@RequestBody IntervencaoOperacional intervencao) {
        IntervencaoOperacional nova = service.salvar(intervencao);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntervencaoOperacional> atualizar(@PathVariable Long id, @RequestBody IntervencaoOperacional intervencao) {
        return service.atualizar(id, intervencao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}