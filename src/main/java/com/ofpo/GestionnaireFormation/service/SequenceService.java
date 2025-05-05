package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Sequence;
import com.ofpo.GestionnaireFormation.repository.SequenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SequenceService {

    private final SequenceRepository sequenceRepository;

    public SequenceService(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    public List<Sequence> findAll() {
        return sequenceRepository.findAll();
    }

    public Sequence findById(Long id) {
        return sequenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Séquence non trouvée avec l'id : " + id));
    }

    @Transactional
    public Sequence create(Sequence sequence) {
        return sequenceRepository.save(sequence);
    }

    @Transactional
    public Sequence update(Long id, Sequence details) {
        Sequence s = findById(id);
        s.setLibelle(details.getLibelle());
        s.setModules(details.getModules());
        return sequenceRepository.save(s);
    }

    public void delete(Long id) {
        sequenceRepository.deleteById(id);
    }
}
