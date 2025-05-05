package com.ofpo.GestionnaireFormation.repository;

import com.ofpo.GestionnaireFormation.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Long> {
    List<Salle> findByCentreId(Long centreId);
}
