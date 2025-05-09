// Package et imports

package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Centre;
import com.ofpo.GestionnaireFormation.service.CentreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour gérer les opérations CRUD sur les centres de formation.
 * <p>
 * Cette classe expose des endpoints REST pour créer, lire, mettre à jour et supprimer des centres de formation.
 * </p>
 */
@RestController
@RequestMapping("/centres")
public class CentreController {

    /**
     * Service de gestion des centres de formation.
     */
    private final CentreService service;

    /**
     * Constructeur pour initialiser le service de gestion des centres.
     *
     * @param service le service de gestion des centres
     */
    public CentreController(CentreService service) {
        this.service = service;
    }

    /**
     * Endpoint pour récupérer tous les centres de formation.
     *
     * @return la liste de tous les centres
     */
    @GetMapping("/")
    public List<Centre> all() {
        return service.findAll();
    }

    /**
     * Endpoint pour récupérer un centre de formation par son ID.
     *
     * @param id l'ID du centre
     * @return le centre correspondant à l'ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Centre> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Endpoint pour créer un nouveau centre de formation.
     *
     * @param c le centre à créer
     * @return le centre créé
     */
    @PostMapping("/create")
    public ResponseEntity<Centre> create(@RequestBody Centre c) {
        return ResponseEntity.ok(service.create(c));
    }

    /**
     * Endpoint pour mettre à jour un centre de formation existant.
     *
     * @param id l'ID du centre à mettre à jour
     * @param c  le centre mis à jour
     * @return le centre mis à jour
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Centre> update(
            @PathVariable Long id,
            @RequestBody Centre c
    ) {
        return ResponseEntity.ok(service.update(id, c));
    }

    /**
     * Endpoint pour supprimer un centre de formation par son ID.
     *
     * @param id l'ID du centre à supprimer
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint pour associer une formation à un centre de formation.
     *
     * @param centreId    l'ID du centre
     * @param formationId l'ID de la formation
     * @return le centre mis à jour avec la formation associée
     */
    /** POST /centres/{cid}/formations/{fid} pour associer */
    @PostMapping("/{centreId}/formations/{formationId}")
    public ResponseEntity<Centre> addFormation(
            @PathVariable Long centreId,
            @PathVariable Long formationId
    ) {
        return ResponseEntity.ok(service.addFormation(centreId, formationId));
    }
}
