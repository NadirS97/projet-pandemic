package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class Chercheuse extends CarteRole {

    public Chercheuse(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.CHERCHEUSE);
        super.setDescriptionRole("ceci est une description pour chercheuse.");
    }

    public void effetRole() {

    }

}
