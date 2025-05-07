package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.model.Document;
import com.ofpo.GestionnaireFormation.service.FormationDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/formations/{formationId}/documents")
public class FormationDocumentController {

    private final FormationDocumentService service;

    public FormationDocumentController(
            FormationDocumentService service) {
        this.service = service;
    }

    @PostMapping("/{documentId}")
    public ResponseEntity<Void> lier(
            @PathVariable Long formationId,
            @PathVariable Long documentId,
            @RequestParam("dateGen") String dateGen) {

        service.lierDocument(
                formationId, documentId,
                LocalDate.parse(dateGen));
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{documentId}/telecharger")
    public ResponseEntity<Void> telecharger(
            @PathVariable Long formationId,
            @PathVariable Long documentId,
            @RequestParam("date") String dateTelec) {

        service.marquerTelechargement(
                formationId, documentId,
                LocalDate.parse(dateTelec));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Document> liste(
            @PathVariable Long formationId) {
        return service.listeDocuments(formationId);
    }
}