package com.ofpo.GestionnaireFormation.repository;

import com.ofpo.GestionnaireFormation.model.FormationDocument;
import com.ofpo.GestionnaireFormation.model.FormationDocumentKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationDocumentRepository
        extends JpaRepository<FormationDocument, FormationDocumentKey> { }
