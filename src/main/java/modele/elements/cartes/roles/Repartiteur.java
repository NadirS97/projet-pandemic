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
        super.setDescriptionRole(
                "Déplacez le pion d'un autre joueur comme si c'était le vôtre. Pour une action, déplacez un " +
                        "pion sur une ville où se trouve un autre pion. Vous devez avoir la permission du propriétaire" +
                        " du pion qui sera déplacé.");
    }

    @Override
    public void effet(Optional<IEffetType> effetType) throws Exception {

    }
}
