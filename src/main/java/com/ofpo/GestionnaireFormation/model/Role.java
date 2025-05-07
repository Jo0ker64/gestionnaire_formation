package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private Boolean statut;

    // **Le constructeur vide est indispensable pour Jackson**
    public Role() {}

    // Si tu veux, tu peux aussi avoir un constructeur utile :
    public Role(String libelle, Boolean statut) {
        this.libelle  = libelle;
        this.statut   = statut;
    }

    // … puis tes getters & setters …

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public Boolean getStatut() { return statut; }
    public void setStatut(Boolean statut) { this.statut = statut; }
}
