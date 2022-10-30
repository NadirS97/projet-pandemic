package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.cartes.effets.IEffetType;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.util.Optional;

public class Medecin extends CarteRole {

    public Medecin(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.MEDECIN);
        super.setDescriptionRole("ceci est une description pour Medecin.");
    }

    @Override
    public void effet(Optional<IEffetType> effetType) throws Exception {

    }

}
