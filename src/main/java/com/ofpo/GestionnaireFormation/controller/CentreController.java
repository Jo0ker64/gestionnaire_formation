package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Centre;
import com.ofpo.GestionnaireFormation.service.CentreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/centres")
public class CentreController {

    private final CentreService service;

    public CentreController(CentreService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Centre> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Centre> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Centre> create(@RequestBody Centre c) {
        return ResponseEntity.ok(service.create(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Centre> update(
            @PathVariable Long id,
            @RequestBody Centre c
    ) {
        return ResponseEntity.ok(service.update(id, c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** POST /centres/{cid}/formations/{fid} pour associer */
    @PostMapping("/{centreId}/formations/{formationId}")
    public ResponseEntity<Centre> addFormation(
            @PathVariable Long centreId,
            @PathVariable Long formationId
    ) {
        return ResponseEntity.ok(service.addFormation(centreId, formationId));
    }
}
