package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.cartes.effets.IEffetType;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.util.Optional;

public class PlanificateurDUrgence extends CarteRole {

    public PlanificateurDUrgence(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.PLANIFICATEUR_D_URGENCE);
        super.setDescriptionRole("ceci est une description pour PlanificateurDUrgence.");
    }

    @Override
    public void effet(Optional<IEffetType> effetType) throws Exception {

    }

}
