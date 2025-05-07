package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String libelle;

    public Type() {}

    public Type(String libelle) {
        this.libelle = libelle;
    }

    public Integer getId() {
        return id;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
