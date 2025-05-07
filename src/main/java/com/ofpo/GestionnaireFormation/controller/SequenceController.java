package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Sequence;
import com.ofpo.GestionnaireFormation.service.SequenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sequences")
public class SequenceController {

    private final SequenceService sequenceService;

    public SequenceController(SequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    @GetMapping("/")
    public List<Sequence> findAll() {
        return sequenceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sequence> findById(@PathVariable Long id) {
        return ResponseEntity.ok(sequenceService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Sequence> create(@RequestBody Sequence sequence) {
        Sequence saved = sequenceService.create(sequence);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sequence> update(
            @PathVariable Long id,
            @RequestBody Sequence sequence) {
        Sequence updated = sequenceService.update(id, sequence);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sequenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
