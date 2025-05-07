package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ressource")
public class Ressource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private LocalDate dateCreation;
    private LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private Type type;

    @OneToMany(mappedBy = "ressource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormationRessource> formations = new ArrayList<>();

    public Ressource() {}

    // getters / setters

    public Long getId() { return id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    public LocalDate getDateModification() { return dateModification; }
    public void setDateModification(LocalDate dateModification) { this.dateModification = dateModification; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}
