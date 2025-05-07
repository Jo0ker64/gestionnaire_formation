package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FormationRessourceKey implements Serializable {
    private Long formationId;
    private Long ressourceId;

    public FormationRessourceKey() {}

    public FormationRessourceKey(Long formationId, Long ressourceId) {
        this.formationId = formationId;
        this.ressourceId = ressourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormationRessourceKey)) return false;
        FormationRessourceKey that = (FormationRessourceKey) o;
        return Objects.equals(formationId, that.formationId) &&
                Objects.equals(ressourceId, that.ressourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formationId, ressourceId);
    }
}
