package com.ofpo.GestionnaireFormation.repository;

import com.ofpo.GestionnaireFormation.model.Inscription;
import com.ofpo.GestionnaireFormation.model.InscriptionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository
        extends JpaRepository<Inscription, InscriptionKey> { }
