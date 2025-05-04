package com.ofpo.GestionnaireFormation.controller;

import com.ofpo.GestionnaireFormation.DTO.UtilisateurDTO;
import com.ofpo.GestionnaireFormation.model.Utilisateur;
import com.ofpo.GestionnaireFormation.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /**
     * Récupérer tous les utilisateurs
     *
     * @return Liste des utilisateurs
     */
    @GetMapping("/")
    public List<UtilisateurDTO> findAll() {
        return utilisateurService.findAll().stream()
                .map(UtilisateurDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer un utilisateur via son matricule
     *
     * @param matricule Matricule de l'utilisateur
     * @return UtilisateurDTO
     */
    @GetMapping("/{matricule}")
    public ResponseEntity<UtilisateurDTO> findByMatricule(@PathVariable String matricule) {
        Utilisateur utilisateur = utilisateurService.findByMatricule(matricule);
        if (utilisateur != null) {
            return ResponseEntity.ok(new UtilisateurDTO(utilisateur));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Créer un utilisateur
     *
     * @param utilisateur Utilisateur à créer
     * @return Utilisateur créé
     */
    @PostMapping("/create")
    public Utilisateur add(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.create(utilisateur);
    }

    /**
     * Mettre à jour un utilisateur
     *
     * @param matricule         matricule de l'utilisateur à mettre à jour
     * @param utilisateur Détails de l'utilisateur à mettre à jour
     * @return Utilisateur mis à jour
     */
    @PutMapping("/update/{matricule}")
    public ResponseEntity<Utilisateur> update(@PathVariable Long matricule, @RequestBody Utilisateur utilisateur) {
        return ResponseEntity.ok(utilisateurService.update(matricule, utilisateur));
    }

    /**
     * Supprimer un utilisateur
     *
     * @param matricule ID de l'utilisateur à supprimer
     * @return Réponse vide
     */
    @DeleteMapping("/delete/{matricule}")
    public ResponseEntity<Void> delete(@PathVariable Long matricule) {
        utilisateurService.delete(matricule);
        return ResponseEntity.noContent().build();
    }

    /**
     * Désactiver un utilisateur (statut à false)
     *
     * @param matricule matricule de l'utilisateur à désactiver
     * @return Utilisateur désactivé
     */
    @PutMapping("/disable/{matricule}")
    public ResponseEntity<Utilisateur> updateStatut(@PathVariable Long matricule) {
        return ResponseEntity.ok(utilisateurService.disableUtilisateur(matricule));
    }
}
