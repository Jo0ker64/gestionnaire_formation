package com.ofpo.GestionnaireFormation.DTO;

import com.ofpo.GestionnaireFormation.model.Utilisateur;

import java.util.stream.Collectors;

public class UtilisateurDTO {

    private Long id;
    private String matricule;
    private String avatar;
    private String nom;
    private String prenom;
    private String adresseMail;
    private String adressePostal;
    private String codePostal;
    private String ville;
    private String motDePasse;
    private Boolean statut;
    private String roles; // affichage simple "Admin, Formateur" par exemple

    public UtilisateurDTO(Utilisateur utilisateur) {
        this.id = utilisateur.getId();
        this.matricule = utilisateur.getMatricule();
        this.avatar = utilisateur.getAvatar();
        this.nom = utilisateur.getNom();
        this.prenom = utilisateur.getPrenom();
        this.adresseMail = utilisateur.getAdresseMail();
        this.adressePostal = utilisateur.getAdressePostal();
        this.codePostal = utilisateur.getCodePostal();
        this.ville = utilisateur.getVille();
        this.motDePasse = utilisateur.getMotDePasse();
        this.statut = utilisateur.getStatut();
        this.roles = utilisateur.getRoles().stream()
                .map(role -> role.getLibelle())         // <-- on utilise getLibelle()
                .collect(Collectors.joining(", "));
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public String getAdressePostal() {
        return adressePostal;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public Boolean getStatut() {
        return statut;
    }

    public String getRoles() {
        return roles;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public void setAdressePostal(String adressePostal) {
        this.adressePostal = adressePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
