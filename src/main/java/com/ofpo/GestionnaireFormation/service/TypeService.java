package com.ofpo.GestionnaireFormation.service;

import com.ofpo.GestionnaireFormation.model.Type;
import com.ofpo.GestionnaireFormation.repository.TypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TypeService {
    private final TypeRepository repo;
    public TypeService(TypeRepository repo) { this.repo = repo; }

    public List<Type> findAll() { return repo.findAll(); }
    public Type findById(Integer id) {
        return repo.findById(id).orElseThrow(() ->
                new RuntimeException("Type non trouvé : "+id));
    }

    @Transactional
    public Type create(Type t) { return repo.save(t); }

    @Transactional
    public Type update(Integer id, Type in) {
        Type t = findById(id);
        t.setLibelle(in.getLibelle());
        return repo.save(t);
    }

    @Transactional
    public void delete(Integer id) { repo.deleteById(id); }
}
