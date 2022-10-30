package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class ExpertAuxOperations extends CarteRole {

    public ExpertAuxOperations(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.EXPERT_AUX_OPERATIONS);
        super.setDescriptionRole("ceci est une description pour ExpertAuxOperations.");
    }

    public void effetRole() {

    }

}
