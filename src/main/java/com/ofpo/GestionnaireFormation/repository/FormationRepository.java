package com.ofpo.GestionnaireFormation.repository;

import com.ofpo.GestionnaireFormation.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationRepository extends JpaRepository<Formation, Long> {
}
