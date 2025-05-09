//Package et imports

package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Document;
import com.ofpo.GestionnaireFormation.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour gérer les opérations CRUD sur les documents.
 * <p>
 * Cette classe expose des endpoints REST pour créer, lire, mettre à jour et supprimer des documents.
 * </p>
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    /**
     * Service de gestion des documents.
     */
    private final DocumentService service;

    /**
     * Constructeur pour initialiser le service de gestion des documents.
     *
     * @param service le service de gestion des documents
     */
    public DocumentController(DocumentService service) {
        this.service = service;
    }

    /**
     * Endpoint pour récupérer tous les documents.
     *
     * @return la liste de tous les documents
     */
    @GetMapping("/")
    public List<Document> all() {
        return service.findAll();
    }

    /**
     * Endpoint pour récupérer un document par son ID.
     *
     * @param id l'ID du document
     * @return le document correspondant à l'ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Document> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Endpoint pour créer un nouveau document.
     *
     * @param doc le document à créer
     * @return le document créé
     */
    @PostMapping("/create")
    public ResponseEntity<Document> create(@RequestBody Document doc) {
        Document saved = service.create(doc);
        return ResponseEntity.status(201).body(saved);
    }

    /**
     * Endpoint pour mettre à jour un document existant.
     *
     * @param id  l'ID du document à mettre à jour
     * @param in  les nouvelles données du document
     * @return le document mis à jour
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Document> update(
            @PathVariable Long id,
            @RequestBody Document in) {
        return ResponseEntity.ok(service.update(id, in));
    }

    /**
     * Endpoint pour supprimer un document par son ID.
     *
     * @param id l'ID du document à supprimer
     * @return une réponse vide avec le statut 204 No Content
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
