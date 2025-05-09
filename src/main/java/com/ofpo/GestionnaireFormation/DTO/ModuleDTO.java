package com.ofpo.GestionnaireFormation.DTO;

public class ModuleDTO {
    private Long id;
    private String libelle;

    public ModuleDTO() {}

    public ModuleDTO(Long id, String libelle) {
        this.id      = id;
        this.libelle = libelle;
    }

    // getters, setters
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
}
