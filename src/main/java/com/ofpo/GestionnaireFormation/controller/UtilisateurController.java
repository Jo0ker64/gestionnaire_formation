package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Utilisateur;
import com.ofpo.GestionnaireFormation.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/")
    public List<Utilisateur> findAll() {
        return utilisateurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> findById(@PathVariable Long id) {
        Utilisateur u = utilisateurService.findById(id);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/create")
    public ResponseEntity<Utilisateur> create(@RequestBody Utilisateur utilisateur) {
        Utilisateur saved = utilisateurService.create(utilisateur);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Utilisateur> update(
            @PathVariable Long id,
            @RequestBody Utilisateur utilisateur) {
        Utilisateur updated = utilisateurService.update(id, utilisateur);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        utilisateurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Utilisateur> disable(@PathVariable Long id) {
        Utilisateur u = utilisateurService.desactiverUtilisateur(id);
        return ResponseEntity.ok(u);
    }
}
