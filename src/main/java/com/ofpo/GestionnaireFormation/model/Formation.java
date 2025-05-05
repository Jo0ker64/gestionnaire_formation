package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "formation")
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @Column(name = "numero_offre")
    private String numeroOffre;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "date_debut_pe")
    private LocalDate dateDebutPe;

    @Column(name = "date_fin_pe")
    private LocalDate dateFinPe;

    private Integer statut;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "formation_module",
            joinColumns = @JoinColumn(name = "id_formation"),
            inverseJoinColumns = @JoinColumn(name = "id_module")
    )
    private List<Module> modules = new ArrayList<>();

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getNumeroOffre() { return numeroOffre; }
    public void setNumeroOffre(String numeroOffre) { this.numeroOffre = numeroOffre; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public LocalDate getDateDebutPe() { return dateDebutPe; }
    public void setDateDebutPe(LocalDate dateDebutPe) { this.dateDebutPe = dateDebutPe; }

    public LocalDate getDateFinPe() { return dateFinPe; }
    public void setDateFinPe(LocalDate dateFinPe) { this.dateFinPe = dateFinPe; }

    public Integer getStatut() { return statut; }
    public void setStatut(Integer statut) { this.statut = statut; }

    public List<Module> getModules() { return modules; }
    public void setModules(List<Module> modules) { this.modules = modules; }
}
