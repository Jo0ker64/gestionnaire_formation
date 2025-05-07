package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Role;            // ⇨ CORRECTION : import du modèle Role
import com.ofpo.GestionnaireFormation.model.Utilisateur;
import com.ofpo.GestionnaireFormation.repository.RoleRepository;      // ⇨ CORRECTION : import du repository Role
import com.ofpo.GestionnaireFormation.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;      // ⇨ CORRECTION : annotation transactionnelle

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;          // ⇨ CORRECTION : injection de RoleRepository

    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                              RoleRepository roleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;            // ⇨ CORRECTION : assignation du RoleRepository
    }

    // Récupérer tous les utilisateurs
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    // Récupérer un utilisateur via son matricule
    public Utilisateur findByMatricule(String matricule) {
        return utilisateurRepository.findByMatricule(matricule);
    }

    // Créer un utilisateur
    @Transactional                                        // ⇨ CORRECTION : assurer une transaction
    public Utilisateur create(Utilisateur utilisateur) {
        // ⇨ CORRECTION : remplacer chaque Role "transient" par l'entité persistée
        List<Role> persistedRoles = utilisateur.getRoles().stream()
                .map(r -> roleRepository.findById(r.getId())
                        .orElseThrow(() -> new RuntimeException("Role non trouvé avec id : " + r.getId())))
                .collect(Collectors.toList());
        utilisateur.setRoles(persistedRoles);

        return utilisateurRepository.save(utilisateur);
    }

    // Mettre à jour un utilisateur
    @Transactional                                        // ⇨ CORRECTION : assurer une transaction
    public Utilisateur update(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));

        // Mise à jour des champs simples
        utilisateur.setMatricule(utilisateurDetails.getMatricule());
        utilisateur.setAvatar(utilisateurDetails.getAvatar());
        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setPrenom(utilisateurDetails.getPrenom());
        utilisateur.setAdresseMail(utilisateurDetails.getAdresseMail());
        utilisateur.setAdressePostal(utilisateurDetails.getAdressePostal());
        utilisateur.setCodePostal(utilisateurDetails.getCodePostal());
        utilisateur.setVille(utilisateurDetails.getVille());
        utilisateur.setMotDePasse(utilisateurDetails.getMotDePasse());
        utilisateur.setStatut(utilisateurDetails.getStatut());

        // ⇨ CORRECTION : idem pour la mise à jour des rôles
        List<Role> updatedRoles = utilisateurDetails.getRoles().stream()
                .map(r -> roleRepository.findById(r.getId())
                        .orElseThrow(() -> new RuntimeException("Role non trouvé avec id : " + r.getId())))
                .collect(Collectors.toList());
        utilisateur.setRoles(updatedRoles);

        return utilisateurRepository.save(utilisateur);
    }

    // Supprimer un utilisateur
    public void delete(Long id) {
        utilisateurRepository.deleteById(id);
    }

    // Désactiver un utilisateur (statut à false)
    @Transactional                                        // ⇨ CORRECTION : comporter en transaction
    public Utilisateur disableUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        utilisateur.setStatut(false);
        return utilisateurRepository.save(utilisateur);
    }

    @Transactional
    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
    }

}
