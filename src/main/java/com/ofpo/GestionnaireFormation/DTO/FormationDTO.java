package com.ofpo.GestionnaireFormation.DTO;

import java.time.LocalDate;
import java.util.List;

public class FormationDTO {
    private Long id;
    private String libelle;
    private String numeroOffre;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Boolean statut;
    private List<RessourceDTO> ressources;
    private List<ModuleDTO> modules;

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

    public String getNumeroOffre() {
        return numeroOffre;
    }

    public void setNumeroOffre(String numeroOffre) {
        this.numeroOffre = numeroOffre;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public List<RessourceDTO> getRessources() {
        return ressources;
    }

    public void setRessources(List<RessourceDTO> ressources) {
        this.ressources = ressources;
    }

    public List<ModuleDTO> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDTO> modules) {
        this.modules = modules;
    }
}
