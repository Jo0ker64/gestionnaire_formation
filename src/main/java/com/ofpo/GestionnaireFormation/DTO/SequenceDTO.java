package com.ofpo.GestionnaireFormation.DTO;

import com.ofpo.GestionnaireFormation.model.Sequence;

public class SequenceDTO {
    private Long id;
    private String libelle;

    public SequenceDTO() {}

    public SequenceDTO(Sequence seq) {
        this.id = seq.getId();
        this.libelle = seq.getLibelle();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
