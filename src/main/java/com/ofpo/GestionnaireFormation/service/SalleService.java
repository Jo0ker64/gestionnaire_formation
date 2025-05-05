package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Salle;
import com.ofpo.GestionnaireFormation.repository.SalleRepository;
import com.ofpo.GestionnaireFormation.repository.CentreRepository;
import com.ofpo.GestionnaireFormation.model.Centre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleService {

    private final SalleRepository salleRepo;
    private final CentreRepository centreRepo;

    public SalleService(SalleRepository salleRepo, CentreRepository centreRepo) {
        this.salleRepo = salleRepo;
        this.centreRepo = centreRepo;
    }

    public List<Salle> findAll() {
        return salleRepo.findAll();
    }

    public List<Salle> findByCentre(Long centreId) {
        return salleRepo.findByCentreId(centreId);
    }

    public Salle findById(Long id) {
        return salleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Salle non trouvée : " + id));
    }

    public Salle create(Long centreId, Salle s) {
        Centre c = centreRepo.findById(centreId)
                .orElseThrow(() -> new RuntimeException("Centre non trouvé : " + centreId));
        s.setCentre(c);
        return salleRepo.save(s);
    }

    public Salle update(Long id, Salle data) {
        Salle s = findById(id);
        s.setLibelle(data.getLibelle());
        return salleRepo.save(s);
    }

    public void delete(Long id) {
        salleRepo.deleteById(id);
    }
}
