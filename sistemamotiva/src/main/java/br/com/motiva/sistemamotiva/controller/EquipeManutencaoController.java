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

import br.com.motiva.sistemamotiva.model.EquipeManutencao;
import br.com.motiva.sistemamotiva.service.EquipeManutencaoService;

@RestController
@RequestMapping("/api/equipes")
public class EquipeManutencaoController {

    private final EquipeManutencaoService service;

    public EquipeManutencaoController(EquipeManutencaoService service) {
        this.service = service;
    }

    // GET /api/equipes - Lista todas as equipes (200 OK)
    @GetMapping
    public ResponseEntity<List<EquipeManutencao>> listarTodas() {
        List<EquipeManutencao> lista = service.listarTodas();
        return ResponseEntity.ok(lista);
    }

    // GET /api/equipes/{id} - Busca por ID (200 OK ou 404 Not Found)
    @GetMapping("/{id}")
    public ResponseEntity<EquipeManutencao> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/equipes - Cria nova equipe (201 Created)
    @PostMapping
    public ResponseEntity<EquipeManutencao> criar(@RequestBody EquipeManutencao equipe) {
        EquipeManutencao novaEquipe = service.salvar(equipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEquipe);
    }

    // PUT /api/equipes/{id} - Atualiza equipe existente (200 OK ou 404 Not Found)
    @PutMapping("/{id}")
    public ResponseEntity<EquipeManutencao> atualizar(@PathVariable Long id, @RequestBody EquipeManutencao equipe) {
        return service.atualizar(id, equipe)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/equipes/{id} - Remove equipe (204 No Content ou 404 Not Found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean removido = service.deletar(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}