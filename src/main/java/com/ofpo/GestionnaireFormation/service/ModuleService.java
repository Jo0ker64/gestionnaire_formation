package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Module;
import com.ofpo.GestionnaireFormation.repository.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> findAll() {
        return moduleRepository.findAll();
    }

    public Module findById(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module non trouvé avec l'id : " + id));
    }

    @Transactional
    public Module create(Module module) {
        return moduleRepository.save(module);
    }

    @Transactional
    public Module update(Long id, Module details) {
        Module m = findById(id);
        m.setLibelle(details.getLibelle());
        m.setFormations(details.getFormations());
        m.setSequences(details.getSequences());
        return moduleRepository.save(m);
    }

    public void delete(Long id) {
        moduleRepository.deleteById(id);
    }
}
