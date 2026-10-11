package br.com.motiva.sistemamotiva.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.motiva.sistemamotiva.model.RelatorioPrioridade;
import br.com.motiva.sistemamotiva.service.RelatorioPrioridadeService;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioPrioridadeController {

    private final RelatorioPrioridadeService service;

    public RelatorioPrioridadeController(RelatorioPrioridadeService service) {
        this.service = service;
    }

    // POST /api/relatorios - Gera a analise e grava no historico (201 Created)
    @PostMapping
    public ResponseEntity<RelatorioPrioridade> gerarRelatorio() {
        RelatorioPrioridade relatorio = service.gerarEPersistirRelatorio();
        return ResponseEntity.status(HttpStatus.CREATED).body(relatorio);
    }

    // GET /api/relatorios - Lista historico completo (200 OK)
    @GetMapping
    public ResponseEntity<List<RelatorioPrioridade>> listarHistorico() {
        return ResponseEntity.ok(service.listarHistorico());
    }

    // GET /api/relatorios/periodo?inicio=...&fim=... - Filtro por periodo (200 OK)
    @GetMapping("/periodo")
    public ResponseEntity<List<RelatorioPrioridade>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(service.buscarPorPeriodo(inicio, fim));
    }
}