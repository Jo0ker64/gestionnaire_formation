package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "formation_ressource")
public class FormationRessource {

    @EmbeddedId
    private FormationRessourceKey id;

    @ManyToOne
    @MapsId("formationId")
    @JoinColumn(name = "id_formation")
    private Formation formation;

    @ManyToOne
    @MapsId("ressourceId")
    @JoinColumn(name = "id_ressource")
    private Ressource ressource;

    @Column(name = "date_ajout")
    private LocalDate dateAjout;

    public FormationRessource() {}

    public FormationRessource(Formation formation, Ressource ressource, LocalDate dateAjout) {
        this.formation  = formation;
        this.ressource  = ressource;
        this.dateAjout  = dateAjout;
        this.id         = new FormationRessourceKey(formation.getId(), ressource.getId());
    }

    public Formation getFormation() {
        return formation;
    }
    public Ressource getRessource() {
        return ressource;
    }
    public LocalDate getDateAjout() {
        return dateAjout;
    }
    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }
}
