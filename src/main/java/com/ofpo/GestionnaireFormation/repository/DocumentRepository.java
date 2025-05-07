package com.ofpo.GestionnaireFormation.repository;

import com.ofpo.GestionnaireFormation.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
