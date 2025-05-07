package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Utilisateur;
import com.ofpo.GestionnaireFormation.model.Formation;
import com.ofpo.GestionnaireFormation.service.InscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscriptions")
public class InscriptionController {

    private final InscriptionService service;
    public InscriptionController(InscriptionService service) {
        this.service = service;
    }

    @PostMapping("/{userId}/{formationId}")
    public ResponseEntity<Void> inscrire(
            @PathVariable Long userId,
            @PathVariable Long formationId) {
        service.inscrire(userId, formationId);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{userId}/{formationId}")
    public ResponseEntity<Void> desinscrire(
            @PathVariable Long userId,
            @PathVariable Long formationId) {
        service.desinscrire(userId, formationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/formation/{formationId}")
    public List<Utilisateur> participants(
            @PathVariable Long formationId) {
        return service.listeUtilisateurs(formationId);
    }

    @GetMapping("/utilisateur/{userId}")
    public List<Formation> mesFormations(
            @PathVariable Long userId) {
        return service.listeFormations(userId);
    }
}