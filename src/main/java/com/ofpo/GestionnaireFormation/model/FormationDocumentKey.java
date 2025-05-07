package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FormationDocumentKey implements Serializable {
    private Long formationId;
    private Long documentId;

    public FormationDocumentKey() {}

    public FormationDocumentKey(Long formationId, Long documentId) {
        this.formationId = formationId;
        this.documentId  = documentId;
    }

    public Long getFormationId() {
        return formationId;
    }

    public void setFormationId(Long formationId) {
        this.formationId = formationId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormationDocumentKey)) return false;
        FormationDocumentKey that = (FormationDocumentKey) o;
        return Objects.equals(formationId, that.formationId) &&
                Objects.equals(documentId, that.documentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formationId, documentId);
    }
}
