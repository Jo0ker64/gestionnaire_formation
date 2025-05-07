package com.ofpo.GestionnaireFormation.DTO;

import java.time.LocalDate;

public class DocumentDTO {
    private Long id;
    private String libelle;
    private LocalDate dateCreation;
    private LocalDate dateModification;


    // constructeurs, getters, setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public LocalDate getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
    public LocalDate getDateModification() {
        return dateModification;
    }
    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

}
