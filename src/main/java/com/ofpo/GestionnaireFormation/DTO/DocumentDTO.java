// Package et imports

package com.ofpo.GestionnaireFormation.DTO;

import java.time.LocalDate;

/**
 * DocumentDTO est un objet de transfert de données (DTO) qui représente un document.
 * Il contient des informations sur le document, y compris son identifiant, son libellé et sa date de création.
 */
public class DocumentDTO {
    private Long id;
    private String libelle;
    private LocalDate dateCreation;

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

}
