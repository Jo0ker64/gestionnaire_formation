package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Module;
import com.ofpo.GestionnaireFormation.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping("/")
    public List<Module> findAll() {
        return moduleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> findById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Module> create(@RequestBody Module module) {
        Module saved = moduleService.create(module);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Module> update(
            @PathVariable Long id,
            @RequestBody Module module) {
        Module updated = moduleService.update(id, module);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        moduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
