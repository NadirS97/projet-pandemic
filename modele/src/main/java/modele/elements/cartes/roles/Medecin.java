package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class Medecin extends CarteRole {

    public Medecin(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.MEDECIN);
        super.setDescriptionRole("ceci est une description pour Medecin.");
    }

    public void effetRole() {

    }

}
