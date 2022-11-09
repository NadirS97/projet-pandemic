package modele.elements.cartes.roles;

import modele.elements.PionJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

public class CartePlanificateurDUrgence extends CarteRole {

    public CartePlanificateurDUrgence(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.PLANIFICATEUR_D_URGENCE);
        super.setDescriptionRole(
                "Pour une action, prenez une carte Evenement de la défausse et entreposez-la sur cette carte. " +
                        "Lorsque vous jouez la carte Evenement entreprosée. Retirez la de la partie. " +
                        "Limite de 1 carte Evenement sur cette carte. Elle ne fait pas partie de votre main.");
    }


    @Override
    public void execEffet(PionJoueur pionJoueur) throws Exception {
    }
}
