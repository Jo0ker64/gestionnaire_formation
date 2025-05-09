// Package et imports

package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Formation;
import com.ofpo.GestionnaireFormation.service.FormationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour gérer les opérations CRUD sur les formations.
 * <p>
 * Cette classe expose des endpoints REST pour créer, lire, mettre à jour et supprimer des formations.
 * </p>
 */
@RestController
@RequestMapping("/formations")

/**
 * Controller pour gérer les opérations CRUD sur les formations.
 * <p>
 * Cette classe expose des endpoints REST pour créer, lire, mettre à jour et supprimer des formations.
 * </p>
 */
public class FormationController {

    /**
     * Service de gestion des formations.
     */
    private final FormationService formationService;

    /**
     * Constructeur pour initialiser le service de gestion des formations.
     *
     * @param formationService le service de gestion des formations
     */
    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    /**
     * Endpoint pour récupérer toutes les formations.
     *
     * @return la liste de toutes les formations
     */
    @GetMapping("/")
    public List<Formation> findAll() {
        return formationService.findAll();
    }

    /**
     * Endpoint pour récupérer une formation par son ID.
     *
     * @param id l'ID de la formation
     * @return la formation correspondant à l'ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Formation> findById(@PathVariable Long id) {
        return ResponseEntity.ok(formationService.findById(id));
    }

    /**
     * Endpoint pour créer une nouvelle formation.
     *
     * @param formation la formation à créer
     * @return la formation créée
     */
    @PostMapping("/create")
    public ResponseEntity<Formation> create(@RequestBody Formation formation) {
        Formation saved = formationService.create(formation);
        return ResponseEntity.ok(saved);
    }

    /**
     * Endpoint pour mettre à jour une formation existante.
     *
     * @param id        l'ID de la formation à mettre à jour
     * @param formation les nouvelles données de la formation
     * @return la formation mise à jour
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Formation> update(
            @PathVariable Long id,
            @RequestBody Formation formation) {
        Formation updated = formationService.update(id, formation);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint pour supprimer une formation par son ID.
     *
     * @param id l'ID de la formation à supprimer
     * @return une réponse vide avec le statut 204 No Content
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}