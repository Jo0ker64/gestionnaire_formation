package com.ofpo.GestionnaireFormation.DTO;

public class TypeDTO {
    private Long id;
    private String libelle;

    public TypeDTO() {}
    public TypeDTO(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "TypeDTO{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }

    // getters and setters
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
