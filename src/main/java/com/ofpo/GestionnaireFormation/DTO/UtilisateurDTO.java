package com.ofpo.GestionnaireFormation.DTO;

import com.ofpo.GestionnaireFormation.model.Utilisateur;
import java.util.List;
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
    private List<String> roles;  // <-- liste de libellés de rôle

    public UtilisateurDTO() {}

    public UtilisateurDTO(Utilisateur u) {
        this.id             = u.getId();
        this.matricule      = u.getMatricule();
        this.avatar         = u.getAvatar();
        this.nom            = u.getNom();
        this.prenom         = u.getPrenom();
        this.adresseMail    = u.getAdresseMail();
        this.adressePostal  = u.getAdressePostal();
        this.codePostal     = u.getCodePostal();
        this.ville          = u.getVille();
        this.motDePasse     = u.getMotDePasse();
        this.statut         = u.getStatut();
        this.roles          = u.getRoles().stream()
                .map(r -> r.getLibelle())
                .collect(Collectors.toList());
    }

    // Convertit ce DTO en entité Utilisateur (sans la résolution des rôles)
    public Utilisateur toEntity() {
        Utilisateur u = new Utilisateur();
        u.setId(this.id);
        u.setMatricule(this.matricule);
        u.setAvatar(this.avatar);
        u.setNom(this.nom);
        u.setPrenom(this.prenom);
        u.setAdresseMail(this.adresseMail);
        u.setAdressePostal(this.adressePostal);
        u.setCodePostal(this.codePostal);
        u.setVille(this.ville);
        u.setMotDePasse(this.motDePasse);
        u.setStatut(this.statut);
        return u;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String m) { this.matricule = m; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String a) { this.avatar = a; }
    public String getNom() { return nom; }
    public void setNom(String n) { this.nom = n; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String p) { this.prenom = p; }
    public String getAdresseMail() { return adresseMail; }
    public void setAdresseMail(String a) { this.adresseMail = a; }
    public String getAdressePostal() { return adressePostal; }
    public void setAdressePostal(String a) { this.adressePostal = a; }
    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String c) { this.codePostal = c; }
    public String getVille() { return ville; }
    public void setVille(String v) { this.ville = v; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String m) { this.motDePasse = m; }
    // statut : true = actif, false = inactif
    // (pour le DTO, on ne gère pas le statut "bloqué")
    public Boolean getStatut() { return statut; }
    public void setStatut(Boolean s) { this.statut = s; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}
