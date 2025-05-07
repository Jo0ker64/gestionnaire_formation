package com.ofpo.GestionnaireFormation.repository;

import com.ofpo.GestionnaireFormation.model.FormationRessource;
import com.ofpo.GestionnaireFormation.model.FormationRessourceKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationRessourceRepository
        extends JpaRepository<FormationRessource,FormationRessourceKey> {}
