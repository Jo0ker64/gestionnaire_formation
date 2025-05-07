package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Ressource;
import com.ofpo.GestionnaireFormation.service.RessourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ressources")
public class RessourceController {
    private final RessourceService service;
    public RessourceController(RessourceService service) { this.service = service; }

    @GetMapping("/")
    public List<Ressource> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Ressource> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public Ressource create(@RequestBody Ressource r) {
        return service.create(r);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ressource> update(@PathVariable Long id, @RequestBody Ressource in) {
        return ResponseEntity.ok(service.update(id,in));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
