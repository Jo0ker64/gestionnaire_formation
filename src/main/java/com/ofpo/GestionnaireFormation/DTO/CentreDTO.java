package com.ofpo.GestionnaireFormation.DTO;

import com.ofpo.GestionnaireFormation.model.Centre;

public class CentreDTO {
    private Long id;
    private String nom;
    private String adressePostal;
    private String ville;
    private String codePostal;
    private String telephone;

    public CentreDTO() {}

    public CentreDTO(Centre centre) {
        this.id = centre.getId();
        this.nom = centre.getNom();
        this.adressePostal = centre.getAdressePostal();
        this.ville = centre.getVille();
        this.codePostal = centre.getCodePostal();
        this.telephone = centre.getTelephone();
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAdressePostal() { return adressePostal; }
    public void setAdressePostal(String adressePostal) { this.adressePostal = adressePostal; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
