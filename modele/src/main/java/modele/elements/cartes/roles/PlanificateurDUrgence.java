package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class PlanificateurDUrgence extends CarteRole {

    public PlanificateurDUrgence(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.PLANIFICATEUR_D_URGENCE);
        super.setDescriptionRole("ceci est une description pour PlanificateurDUrgence.");
    }

    public void effetRole() {

    }

}
