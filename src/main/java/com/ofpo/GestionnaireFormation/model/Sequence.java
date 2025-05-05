package com.ofpo.GestionnaireFormation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sequence")   // ou "sequence_" si besoin
public class Sequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    /** On cache l’inverse pour éviter la récursion Module→Sequence→Module… */
    @ManyToMany(mappedBy = "sequences", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Module> modules = new ArrayList<>();

    // — Getters & Setters —

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

    public List<Module> getModules() {
        return modules;
    }
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
