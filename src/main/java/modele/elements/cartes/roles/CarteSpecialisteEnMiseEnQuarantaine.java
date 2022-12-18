package modele.elements.cartes.roles;

import lombok.NoArgsConstructor;
import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.io.Serializable;

@NoArgsConstructor
public class CarteSpecialisteEnMiseEnQuarantaine extends CarteRole implements Serializable {

    public CarteSpecialisteEnMiseEnQuarantaine(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.SPECIALISTE_EN_MISE_EN_QUARANTAINE);
        super.setDescriptionRole(
                "Empêchez les éclosions et le placement de cubes dans la ville où vous êtes ainsi " +
                        "que dans toutes les villes qui y sont reliées.");
    }

}
