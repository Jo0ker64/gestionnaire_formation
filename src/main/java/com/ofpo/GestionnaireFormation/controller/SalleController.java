package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Salle;
import com.ofpo.GestionnaireFormation.service.SalleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salles")
public class SalleController {

    private final SalleService service;

    public SalleController(SalleService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Salle> all() {
        return service.findAll();
    }

    @GetMapping("/centre/{centreId}")
    public List<Salle> byCentre(@PathVariable Long centreId) {
        return service.findByCentre(centreId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salle> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create/centre/{centreId}")
    public ResponseEntity<Salle> create(
            @PathVariable Long centreId,
            @RequestBody Salle s
    ) {
        return ResponseEntity.ok(service.create(centreId, s));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Salle> update(
            @PathVariable Long id,
            @RequestBody Salle s
    ) {
        return ResponseEntity.ok(service.update(id, s));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
