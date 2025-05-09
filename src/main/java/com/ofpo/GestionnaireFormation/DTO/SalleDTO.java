package com.ofpo.GestionnaireFormation.DTO;

import com.ofpo.GestionnaireFormation.model.Salle;

public class SalleDTO {
    private Long id;
    private String libelle;
    private Long centreId;

    public SalleDTO() {}

    public SalleDTO(Salle s) {
        this.id = s.getId();
        this.libelle = s.getLibelle();
        this.centreId = s.getCentre() != null ? s.getCentre().getId() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Long getCentreId() { return centreId; }
    public void setCentreId(Long centreId) { this.centreId = centreId; }
}
