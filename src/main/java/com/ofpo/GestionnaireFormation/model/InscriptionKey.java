package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InscriptionKey implements Serializable {
    private Long utilisateurId;
    private Long formationId;

    public InscriptionKey() {}
    public InscriptionKey(Long utilisateurId, Long formationId) {
        this.utilisateurId = utilisateurId;
        this.formationId   = formationId;
    }

    // getters / setters omitted for brevity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InscriptionKey)) return false;
        InscriptionKey that = (InscriptionKey) o;
        return Objects.equals(utilisateurId, that.utilisateurId)
                && Objects.equals(formationId,   that.formationId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(utilisateurId, formationId);
    }
}
