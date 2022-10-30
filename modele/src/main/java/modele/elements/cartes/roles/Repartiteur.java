package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.cartes.effets.IEffetType;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.util.Optional;

public class Repartiteur extends CarteRole {

    public Repartiteur(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.REPARTITEUR);
        super.setDescriptionRole("ceci est une description pour Repartiteur.");
    }

    @Override
    public void effet(Optional<IEffetType> effetType) throws Exception {

    }
}
