package modele.elements.cartes.roles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteRole;
import modele.elements.enums.CouleurPionsRole;
import modele.elements.enums.NomsRoles;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@Setter
public class CartePlanificateurDurgence extends CarteRole implements Serializable {

    CarteEvenement carteEvenementEntrepose;

    public CartePlanificateurDurgence(CouleurPionsRole couleurPionRole) {
        super(couleurPionRole);
        super.setNomRole(NomsRoles.PLANIFICATEUR_D_URGENCE);
        super.setDescriptionRole(
                "Pour une action, prenez une carte Evenement de la défausse et entreposez-la sur cette carte. " +
                        "Lorsque vous jouez la carte Evenement entreprosée. Retirez la de la partie. " +
                        "Limite de 1 carte Evenement sur cette carte. Elle ne fait pas partie de votre main.");
    }

}
