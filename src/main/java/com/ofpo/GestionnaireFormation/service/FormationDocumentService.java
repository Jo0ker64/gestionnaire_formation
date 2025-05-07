package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.*;
import com.ofpo.GestionnaireFormation.repository.FormationDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationDocumentService {

    private final FormationDocumentRepository repo;
    private final FormationService formationService;
    private final DocumentService    documentService;

    public FormationDocumentService(FormationDocumentRepository repo,
                                    FormationService formationService,
                                    DocumentService documentService) {
        this.repo             = repo;
        this.formationService = formationService;
        this.documentService  = documentService;
    }

    @Transactional
    public void lierDocument(Long formationId,
                             Long documentId,
                             LocalDate dateGen) {
        Formation f = formationService.findById(formationId);
        Document  d = documentService.findById(documentId);
        repo.save(new FormationDocument(f, d, dateGen, null));
    }

    @Transactional
    public void marquerTelechargement(Long formationId,
                                      Long documentId,
                                      LocalDate dateTelec) {
        FormationDocument doc = repo.findById(
                        new FormationDocumentKey(formationId, documentId))
                .orElseThrow();
        doc.setDateTelechargement(dateTelec);
        repo.save(doc);
    }

    public List<Document> listeDocuments(Long formationId) {
        return repo.findAll().stream()
                .filter(fd -> fd.getFormation().getId()
                        .equals(formationId))
                .map(FormationDocument::getDocument)
                .collect(Collectors.toList());
    }
}
