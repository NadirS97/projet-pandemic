package modele.elements.cartes.roles;

import modele.elements.PionJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.util.Optional;

public class Scientifique extends CarteRole {

    public Scientifique(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.SCIENTIFIQUE);
        super.setDescriptionRole("Vous n'avez besoin que de 4 cartes de la même couleur pour découvrir un remède.");
    }



    @Override
    public void execEffet(PionJoueur pionJoueur) throws Exception {

    }
}
