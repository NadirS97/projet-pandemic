package modele.elements.cartes.roles;

import modele.elements.cartes.CarteRole;
import modele.elements.cartes.effets.IEffetType;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.util.Optional;

public class ExpertAuxOperations extends CarteRole {

    public ExpertAuxOperations(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.EXPERT_AUX_OPERATIONS);
        super.setDescriptionRole("ceci est une description pour ExpertAuxOperations.");
    }

    @Override
    public void effet(Optional<IEffetType> effetType) throws Exception {

    }
}
