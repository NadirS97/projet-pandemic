package modele.elements.cartes.roles;

import modele.elements.PionJoueur;
import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.util.Optional;

public class ExpertAuxOperations extends CarteRole {

    public ExpertAuxOperations(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.EXPERT_AUX_OPERATIONS);
        super.setDescriptionRole(
                "Pour une action vous pouvez construire une station de recherche dans la ville que " +
                        "vous occupez (sans avoir à défausser). Une fois par tour, pour une action, défaussez une carte " +
                        "Ville pour vous déplacer d'une ville avec une station de recherche vers n'importe quelle ville.");
    }


    @Override
    public void execEffet(PionJoueur pionJoueur) throws Exception {

    }
}
