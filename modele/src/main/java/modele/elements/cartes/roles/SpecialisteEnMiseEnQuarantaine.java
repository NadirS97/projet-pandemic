package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class SpecialisteEnMiseEnQuarantaine extends CarteRole {

    public SpecialisteEnMiseEnQuarantaine(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.SPECIALISTE_EN_MISE_EN_QUARANTAINE);
        super.setDescriptionRole("ceci est une description pour SpecialisteEnMiseEnQuarantaine.");
    }

    public void effetRole() {

    }

}
