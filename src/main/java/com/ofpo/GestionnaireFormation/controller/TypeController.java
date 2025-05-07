package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Type;
import com.ofpo.GestionnaireFormation.service.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/types")
public class TypeController {
    private final TypeService service;
    public TypeController(TypeService service) { this.service = service; }

    @GetMapping("/")
    public List<Type> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Type> one(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public Type create(@RequestBody Type t) { return service.create(t); }

    @PutMapping("/update/{id}")
    public ResponseEntity<Type> update(@PathVariable Integer id, @RequestBody Type in) {
        return ResponseEntity.ok(service.update(id,in));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
