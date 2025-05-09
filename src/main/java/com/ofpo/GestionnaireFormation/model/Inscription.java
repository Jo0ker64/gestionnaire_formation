package com.ofpo.GestionnaireFormation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur_formation_fonction")
public class Inscription {

    @EmbeddedId
    private InscriptionKey id;

    @ManyToOne
    @MapsId("utilisateurId")
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @MapsId("formationId")
    @JoinColumn(name = "id_formation")
    private Formation formation;

    // on peut ajouter ici un rôle / fonction si nécessaire
    // @Column(name="fonction")
    // private String fonction;

    public Inscription() {}
    public Inscription(Utilisateur u, Formation f) {
        this.utilisateur = u;
        this.formation    = f;
        this.id           = new InscriptionKey(u.getId(), f.getId());
    }

    public Inscription(InscriptionKey inscriptionKey, Utilisateur u, Formation f) {
        this.id           = inscriptionKey;
        this.utilisateur  = u;
        this.formation    = f;
    }

    // getters
    public Utilisateur getUtilisateur() { return utilisateur; }
    public Formation   getFormation()   { return formation; }
}
