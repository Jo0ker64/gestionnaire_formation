package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Formation;
import com.ofpo.GestionnaireFormation.model.Inscription;
import com.ofpo.GestionnaireFormation.model.InscriptionKey;
import com.ofpo.GestionnaireFormation.model.Utilisateur;
import com.ofpo.GestionnaireFormation.repository.InscriptionRepository;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscriptionService {

    private final InscriptionRepository repo;
    private final UtilisateurService utilService;
    private final FormationService formService;

    public InscriptionService(InscriptionRepository repo,
                              UtilisateurService utilService,
                              FormationService formService) {
        this.repo        = repo;
        this.utilService = utilService;
        this.formService = formService;
    }

    @Transactional
    public void inscrire(Long userId, Long formationId) {
        Utilisateur u = utilService.findById(userId);
        Formation f   = formService.findById(formationId);
        // Construire la clé et l’entité correctement selon votre modèle
        Inscription ins = new Inscription(new InscriptionKey(userId, formationId), u, f);
        repo.save(ins);
    }

    @Transactional
    public void desinscrire(Long userId, Long formationId) {
        repo.deleteById(new InscriptionKey(userId, formationId));
    }

    public List<Utilisateur> listeUtilisateurs(Long formationId) {
        return repo.findAll().stream()
                .filter(i -> i.getFormation().getId().equals(formationId))
                .map(Inscription::getUtilisateur)
                .collect(Collectors.toList());
    }

    public List<Formation> listeFormations(Long userId) {
        return repo.findAll().stream()
                .filter(i -> i.getUtilisateur().getId().equals(userId))
                .map(Inscription::getFormation)
                .collect(Collectors.toList());
    }
}
