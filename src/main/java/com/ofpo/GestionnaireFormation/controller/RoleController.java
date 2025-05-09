package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.DTO.RoleDTO;
import com.ofpo.GestionnaireFormation.model.Role;
import com.ofpo.GestionnaireFormation.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public List<RoleDTO> findAll() {
        return roleService.findAll()
                .stream()
                .map(RoleDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO dto) {
        Role toCreate = dto.toEntity();
        // par défaut actif
        toCreate.setStatut(true);
        Role saved = roleService.create(toCreate);
        return ResponseEntity.status(201).body(new RoleDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(
            @PathVariable Long id,
            @RequestBody RoleDTO dto) {
        Role updated = roleService.update(id, dto.toEntity());
        return ResponseEntity.ok(new RoleDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
