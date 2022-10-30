package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.cartes.effets.IEffetType;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.util.Optional;

public class Chercheuse extends CarteRole {

    public Chercheuse(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.CHERCHEUSE);
        super.setDescriptionRole("ceci est une description pour chercheuse.");
    }

    @Override
    public void effet(Optional<IEffetType> effetType) throws Exception {

    }

}
