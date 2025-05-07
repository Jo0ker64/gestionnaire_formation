package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Document;
import com.ofpo.GestionnaireFormation.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository repo;

    public DocumentService(DocumentRepository repo) {
        this.repo = repo;
    }

    public List<Document> findAll() {
        return repo.findAll();
    }

    public Document findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document non trouvé : " + id));
    }

    @Transactional
    public Document create(Document doc) {
        return repo.save(doc);
    }

    @Transactional
    public Document update(Long id, Document in) {
        Document d = findById(id);
        d.setLibelle(in.getLibelle());
        d.setDateCreation(in.getDateCreation());
        return repo.save(d);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
