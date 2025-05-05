package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Centre;
import com.ofpo.GestionnaireFormation.model.Formation;
import com.ofpo.GestionnaireFormation.repository.CentreRepository;
import com.ofpo.GestionnaireFormation.repository.FormationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CentreService {

    private final CentreRepository centreRepo;
    private final FormationRepository formationRepo;

    public CentreService(CentreRepository centreRepo, FormationRepository formationRepo) {
        this.centreRepo = centreRepo;
        this.formationRepo = formationRepo;
    }

    public List<Centre> findAll() {
        return centreRepo.findAll();
    }

    public Centre findById(Long id) {
        return centreRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Centre non trouvé : " + id));
    }

    public Centre create(Centre c) {
        return centreRepo.save(c);
    }

    public Centre update(Long id, Centre data) {
        Centre c = findById(id);
        c.setNom(data.getNom());
        c.setAdressePostal(data.getAdressePostal());
        c.setVille(data.getVille());
        c.setCodePostal(data.getCodePostal());
        c.setTelephone(data.getTelephone());
        return centreRepo.save(c);
    }

    public void delete(Long id) {
        centreRepo.deleteById(id);
    }

    /** Associe une formation existante à un centre */
    public Centre addFormation(Long centreId, Long formationId) {
        Centre c = findById(centreId);
        Formation f = formationRepo.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée : " + formationId));
        c.getFormations().add(f);
        return centreRepo.save(c);
    }
}
