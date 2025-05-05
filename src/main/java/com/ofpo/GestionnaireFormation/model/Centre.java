package com.ofpo.GestionnaireFormation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "centre")
public class Centre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adressePostal;
    private String ville;
    private String codePostal;
    private String telephone;

    // association Formation ↔ Centre (M–N)
    @ManyToMany
    @JoinTable(
            name = "formation_centre",
            joinColumns = @JoinColumn(name = "id_centre"),
            inverseJoinColumns = @JoinColumn(name = "id_formation")
    )
    private Set<Formation> formations = new HashSet<>();

    // Centre → Salle (1–N)
    @OneToMany(mappedBy = "centre", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Salle> salles = new HashSet<>();

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdressePostal() {
        return adressePostal;
    }
    public void setAdressePostal(String adressePostal) {
        this.adressePostal = adressePostal;
    }

    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Formation> getFormations() {
        return formations;
    }
    public void setFormations(Set<Formation> formations) {
        this.formations = formations;
    }

    public Set<Salle> getSalles() {
        return salles;
    }
    public void setSalles(Set<Salle> salles) {
        this.salles = salles;
    }
}
