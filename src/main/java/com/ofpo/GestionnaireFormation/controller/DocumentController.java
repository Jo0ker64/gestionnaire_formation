package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Document;
import com.ofpo.GestionnaireFormation.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Document> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Document> create(@RequestBody Document doc) {
        Document saved = service.create(doc);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> update(
            @PathVariable Long id,
            @RequestBody Document in) {
        return ResponseEntity.ok(service.update(id, in));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
