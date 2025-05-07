package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.FormationRessource;
import com.ofpo.GestionnaireFormation.model.Ressource;
import com.ofpo.GestionnaireFormation.service.FormationRessourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/formations/{formationId}/ressources")
public class FormationRessourceController {

    private final FormationRessourceService service;
    public FormationRessourceController(FormationRessourceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ressource> list(@PathVariable Long formationId) {
        return service.listByFormation(formationId);
    }

    @GetMapping("/{ressourceId}")
    public FormationRessource getOne(@PathVariable Long formationId,
                                     @PathVariable Long ressourceId) {
        return service.findById(formationId, ressourceId);
    }

    @PostMapping("/{ressourceId}")
    public ResponseEntity<Void> create(@PathVariable Long formationId,
                                       @PathVariable Long ressourceId) {
        service.addRessource(formationId, ressourceId);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{ressourceId}")
    public ResponseEntity<Void> updateDate(@PathVariable Long formationId,
                                           @PathVariable Long ressourceId,
                                           @RequestBody LocalDate date) {
        service.updateDate(formationId, ressourceId, date);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{ressourceId}")
    public ResponseEntity<Void> delete(@PathVariable Long formationId,
                                       @PathVariable Long ressourceId) {
        service.removeRessource(formationId, ressourceId);
        return ResponseEntity.noContent().build();
    }
}
