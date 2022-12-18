package modele.elements.cartes.roles;

import lombok.NoArgsConstructor;
import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.io.Serializable;

@NoArgsConstructor
public class CarteMedecin extends CarteRole implements Serializable {

    public CarteMedecin(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.MEDECIN);
        super.setDescriptionRole(
                "Retirez tous les cubes d'une couleur lorsque vous traitez une maladie. Dans la " +
                        "ville où vous êtes retirez automatiquement tous les cubes de la couleur d'une maladie guérie " +
                        "(et empêchez d'autres cubes d'une maladie guérie d'y être placés).");
    }


}
