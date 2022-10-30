package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class Scientifique extends CarteRole {

    public Scientifique(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.SCIENTIFIQUE);
        super.setDescriptionRole("ceci est une description pour Scientifique.");
    }

    public void effetRole() {

    }

}
