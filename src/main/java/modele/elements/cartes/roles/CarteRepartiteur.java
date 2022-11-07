package modele.elements.cartes.roles;

import modele.elements.PionJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class CarteRepartiteur extends CarteRole {

    public CarteRepartiteur(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.REPARTITEUR);
        super.setDescriptionRole(
                "Déplacez le pion d'un autre joueur comme si c'était le vôtre. Pour une action, déplacez un " +
                        "pion sur une ville où se trouve un autre pion. Vous devez avoir la permission du propriétaire" +
                        " du pion qui sera déplacé.");
    }



    @Override
    public void execEffet(PionJoueur pionJoueur) throws Exception {

    }
}
