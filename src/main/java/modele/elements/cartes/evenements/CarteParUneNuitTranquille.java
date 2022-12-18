package modele.elements.cartes.evenements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.PionJoueur;
import modele.elements.cartes.CarteEvenement;
import modele.elements.enums.NomsEvenement;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CarteParUneNuitTranquille extends CarteEvenement implements Serializable {

    private final NomsEvenement nomEvenement = NomsEvenement.PAR_UNE_NUIT_TRANQUILE;
    private final String description = "Ne faites pas la prochaine phase Propagation des maladies (ne dévoilez aucune carte Propagation).";

    @Override
    public void execEffet(PionJoueur pionJoueur) {
        pionJoueur.getPlateau().setEffetParUneNuitTranquilleActif(true);
    }
}
