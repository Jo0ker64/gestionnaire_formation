package com.ofpo.GestionnaireFormation.DTO;


import com.ofpo.GestionnaireFormation.model.Inscription;

public class InscriptionDTO {
        private Long utilisateurId;
        private Long formationId;

        public InscriptionDTO() {}

        public InscriptionDTO(Inscription ins) {
            this.utilisateurId = ins.getUtilisateur().getId();
            this.formationId = ins.getFormation().getId();
        }

        public Long getUtilisateurId() { return utilisateurId; }
        public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }
        public Long getFormationId() { return formationId; }
        public void setFormationId(Long formationId) { this.formationId = formationId; }
    }

