package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class Repartiteur extends CarteRole {

    public Repartiteur(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.REPARTITEUR);
        super.setDescriptionRole("ceci est une description pour Repartiteur.");
    }

    public void effetRole() {

    }

}
