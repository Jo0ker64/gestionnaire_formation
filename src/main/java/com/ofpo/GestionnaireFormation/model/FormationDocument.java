package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "formation_document")
public class FormationDocument {

    @EmbeddedId
    private FormationDocumentKey id;

    @ManyToOne
    @MapsId("formationId")
    @JoinColumn(name = "id_formation")
    private Formation formation;

    @ManyToOne
    @MapsId("documentId")
    @JoinColumn(name = "id_document")
    private Document document;

    private LocalDate dateGeneration;
    private LocalDate dateTelechargement;

    public FormationDocument() {}

    public FormationDocument(Formation f, Document d,
                             LocalDate gen, LocalDate telec) {
        this.formation         = f;
        this.document          = d;
        this.dateGeneration    = gen;
        this.dateTelechargement= telec;
        this.id                = new FormationDocumentKey(
                f.getId(), d.getId());
    }

    // getters / setters


    public FormationDocumentKey getId() {
        return id;
    }

    public void setId(FormationDocumentKey id) {
        this.id = id;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public LocalDate getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public LocalDate getDateTelechargement() {
        return dateTelechargement;
    }

    public void setDateTelechargement(LocalDate dateTelechargement) {
        this.dateTelechargement = dateTelechargement;
    }
}