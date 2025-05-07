package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.FormationRessource;
import com.ofpo.GestionnaireFormation.model.Formation;
import com.ofpo.GestionnaireFormation.model.FormationRessourceKey;
import com.ofpo.GestionnaireFormation.model.Ressource;
import com.ofpo.GestionnaireFormation.repository.FormationRessourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationRessourceService {
    private final FormationService fService;
    private final RessourceService  rService;
    private final FormationRessourceRepository repo;

    public FormationRessourceService(FormationService fService,
                                     RessourceService rService,
                                     FormationRessourceRepository repo) {
        this.fService = fService;
        this.rService = rService;
        this.repo     = repo;
    }

    @Transactional
    public void addRessource(Long formationId, Long ressourceId) {
        Formation f = fService.findById(formationId);
        Ressource r = rService.findById(ressourceId);
        FormationRessource fr = new FormationRessource(f, r, LocalDate.now());
        repo.save(fr);
    }

    public List<Ressource> listByFormation(Long formationId) {
        return repo.findAll().stream()
                .filter(fr -> fr.getFormation().getId().equals(formationId))
                .map(FormationRessource::getRessource)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeRessource(Long formationId, Long ressourceId) {
        repo.deleteById(new FormationRessourceKey(formationId, ressourceId));
    }

    @Transactional
    public void updateDate(Long formationId, Long ressourceId, LocalDate date) {
        FormationRessourceKey key = new FormationRessourceKey(formationId, ressourceId);
        FormationRessource fr = repo.findById(key)
                .orElseThrow(() -> new RuntimeException("Lien non trouvé"));
        fr.setDateAjout(date);
        repo.save(fr);
    }

    public FormationRessource findById(Long formationId, Long ressourceId) {
        return repo.findById(new FormationRessourceKey(formationId, ressourceId))
                .orElseThrow(() -> new RuntimeException("Lien non trouvé"));
    }
}
