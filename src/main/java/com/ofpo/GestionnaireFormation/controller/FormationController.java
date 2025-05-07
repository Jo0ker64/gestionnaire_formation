package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Formation;
import com.ofpo.GestionnaireFormation.service.FormationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationController {

    private final FormationService formationService;

    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    @GetMapping("/")
    public List<Formation> findAll() {
        return formationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> findById(@PathVariable Long id) {
        return ResponseEntity.ok(formationService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Formation> create(@RequestBody Formation formation) {
        Formation saved = formationService.create(formation);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Formation> update(
            @PathVariable Long id,
            @RequestBody Formation formation) {
        Formation updated = formationService.update(id, formation);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}