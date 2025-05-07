package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Ressource;
import com.ofpo.GestionnaireFormation.model.Type;
import com.ofpo.GestionnaireFormation.repository.RessourceRepository;
import com.ofpo.GestionnaireFormation.repository.TypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RessourceService {
    private final RessourceRepository repo;
    private final TypeRepository typeRepo;

    public RessourceService(RessourceRepository repo, TypeRepository typeRepo) {
        this.repo     = repo;
        this.typeRepo = typeRepo;
    }

    public List<Ressource> findAll() { return repo.findAll(); }

    public Ressource findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ressource non trouvée : "+id));
    }

    @Transactional
    public Ressource create(Ressource r) {
        Type t = typeRepo.findById(r.getType().getId())
                .orElseThrow(() -> new RuntimeException("Type non trouvé : "+r.getType().getId()));
        r.setType(t);
        return repo.save(r);
    }

    @Transactional
    public Ressource update(Long id, Ressource in) {
        Ressource r = findById(id);
        r.setLibelle(in.getLibelle());
        r.setDateCreation(in.getDateCreation());
        r.setDateModification(in.getDateModification());
        if (in.getType()!=null) {
            Type t = typeRepo.findById(in.getType().getId())
                    .orElseThrow(() -> new RuntimeException("Type non trouvé : "+in.getType().getId()));
            r.setType(t);
        }
        return repo.save(r);
    }

    @Transactional
    public void delete(Long id) { repo.deleteById(id); }
}
