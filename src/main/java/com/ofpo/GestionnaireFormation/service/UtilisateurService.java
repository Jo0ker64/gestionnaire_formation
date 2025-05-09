package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Utilisateur;
import com.ofpo.GestionnaireFormation.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur findByMatricule(String matricule) {
        return utilisateurRepository.findByMatricule(matricule);
    }

    /**
     * Récupère un utilisateur par son ID ou lève une exception s'il n'existe pas.
     */
    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
    }

    public Utilisateur create(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur update(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = findById(id);
        utilisateur.setMatricule(utilisateurDetails.getMatricule());
        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setPrenom(utilisateurDetails.getPrenom());
        utilisateur.setAvatar(utilisateurDetails.getAvatar());
        utilisateur.setAdresseMail(utilisateurDetails.getAdresseMail());
        utilisateur.setAdressePostal(utilisateurDetails.getAdressePostal());
        utilisateur.setCodePostal(utilisateurDetails.getCodePostal());
        utilisateur.setVille(utilisateurDetails.getVille());
        utilisateur.setMotDePasse(utilisateurDetails.getMotDePasse());
        utilisateur.setStatut(utilisateurDetails.getStatut());
        utilisateur.setRoles(utilisateurDetails.getRoles());
        return utilisateurRepository.save(utilisateur);
    }

    public void delete(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur desactiverUtilisateur(Long id) {
        Utilisateur utilisateur = findById(id);
        utilisateur.setStatut(false);
        return utilisateurRepository.save(utilisateur);
    }
}
