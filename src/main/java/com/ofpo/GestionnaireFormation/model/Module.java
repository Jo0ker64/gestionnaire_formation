package com.ofpo.GestionnaireFormation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "module_")   // ou "module" selon ta BDD
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    /** On cache l’inverse pour éviter la récursion Formation→Module→Formation… */
    @ManyToMany(mappedBy = "modules", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Formation> formations = new ArrayList<>();

    /** On conserve la descente vers Sequence */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "module_sequence",
            joinColumns = @JoinColumn(name = "id_module"),
            inverseJoinColumns = @JoinColumn(name = "id_sequence")
    )
    private List<Sequence> sequences = new ArrayList<>();

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

    public List<Formation> getFormations() {
        return formations;
    }
    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }
    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }
}
