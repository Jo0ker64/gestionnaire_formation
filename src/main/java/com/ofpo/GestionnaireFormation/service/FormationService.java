package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Formation;
import com.ofpo.GestionnaireFormation.repository.FormationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormationService {

    private final FormationRepository formationRepository;

    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public List<Formation> findAll() {
        return formationRepository.findAll();
    }

    public Formation findById(Long id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée avec l'id : " + id));
    }

    @Transactional
    public Formation create(Formation formation) {
        return formationRepository.save(formation);
    }

    @Transactional
    public Formation update(Long id, Formation formationDetails) {
        Formation f = findById(id);
        f.setTitre(formationDetails.getTitre());
        f.setDescription(formationDetails.getDescription());
        f.setDuree(formationDetails.getDuree());
        // si tu veux remplacer les modules en même temps :
        f.setModules(formationDetails.getModules());
        return formationRepository.save(f);
    }

    public void delete(Long id) {
        formationRepository.deleteById(id);
    }
}
