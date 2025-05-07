package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Role;
import com.ofpo.GestionnaireFormation.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Récupérer tous les roles
     *
     * @return Liste des roles
     */
    @GetMapping("/")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    /**
     * Récupérer un role via son id
     *
     * @param id Id du role
     * @return Role
     */
    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable Long id) {
        Role role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }

    /**
     * Créer un role
     *
     * @param role Role à créer
     * @return Role créé
     */
    @PostMapping("/create")
    public Role create(@RequestBody Role role) {
        return roleService.create(role);
    }

    /**
     * Mettre à jour un role
     *
     * @param id   Id du role à mettre à jour
     * @param role Role avec les nouvelles informations
     * @return Role mis à jour
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody Role role) {
        return ResponseEntity.ok(roleService.update(id, role));
    }

    /**
     * Supprimer un role
     *
     * @param id Id du role à supprimer
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
