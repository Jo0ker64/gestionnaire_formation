package com.ofpo.GestionnaireFormation.DTO;

import com.ofpo.GestionnaireFormation.model.Role;

public class RoleDTO {
    private Long id;
    private String libelle;
    private Boolean statut;

    public RoleDTO() { }

    public RoleDTO(Role role) {
        this.id      = role.getId();
        this.libelle = role.getLibelle();
        this.statut  = role.getStatut();
    }

    public Role toEntity() {
        Role r = new Role();
        r.setId(this.id);
        r.setLibelle(this.libelle);
        r.setStatut(this.statut != null ? this.statut : true);
        return r;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public Boolean getStatut() { return statut; }
    public void setStatut(Boolean statut) { this.statut = statut; }
}
