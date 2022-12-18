package modele.elements.cartes.roles;

import lombok.NoArgsConstructor;
import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.io.Serializable;

@NoArgsConstructor
public class CarteScientifique extends CarteRole implements Serializable {

    public CarteScientifique(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.SCIENTIFIQUE);
        super.setDescriptionRole("Vous n'avez besoin que de 4 cartes de la même couleur pour découvrir un remède.");
    }

}
